package com.storage.service;

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.storage.config.StoragePlan;
import com.storage.dto.ExpiryDateUpdateRequest;
import com.storage.dto.StoredFileDTO;
import com.storage.entity.StoredFile;
import com.storage.entity.User;
import com.storage.exceptions.ResourceNotFoundException;
import com.storage.exceptions.StorageLimitExceededException;
import com.storage.repository.StoredFileInfo;
import com.storage.repository.StoredFileRepository;
import com.storage.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class StoredFileServiceImpl implements StoredFileService{
	
	private final StoredFileRepository fileRepository;
	private final UserRepository userRepository;

	public StoredFileServiceImpl(StoredFileRepository fileRepository, UserRepository userRepository) {
		super();
		this.fileRepository = fileRepository;
		this.userRepository = userRepository;
	}
	


	@Override
	public void addFile(MultipartFile file, Long userId) throws IOException {
		User user =userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
		 Long totalUsed = fileRepository.getTotalFileSizeByUserId(user.getUserId());
		 Long fileSize = file.getSize();
		 Long maxAllowed = StoragePlan.BASIC.getMaxBytes(); // or user.getStoragePlan().getMaxBytes()

	    if (totalUsed + fileSize > maxAllowed) {
	        throw new StorageLimitExceededException("You have exceeded your 50MB storage limit.");
	    }
			
		StoredFile storedFile = new StoredFile();
		storedFile.setFileName(file.getOriginalFilename());
        storedFile.setStoredFileName(generateUniqueFilename(file.getOriginalFilename()));
        storedFile.setContentType(file.getContentType());
        storedFile.setSize(file.getSize());
        storedFile.setUploadDate(Instant.now());
        storedFile.setData(file.getBytes());
        storedFile.setOwner(user);
        storedFile.setExpiryDate(Instant.now().plus(30,ChronoUnit.DAYS));
        
        fileRepository.save(storedFile);
		
	}
	

	// method to create a unique name for the file
	private String generateUniqueFilename(String originalFilename) {
		String extension = "";
        int i = originalFilename.lastIndexOf(".");
        if (i > 0) {
            extension = originalFilename.substring(i);
        }
        return java.util.UUID.randomUUID().toString() + extension;
	}

	@Override
	@Transactional
	public List<StoredFileDTO> getAllFiles(Long userId) {
	    User user = userRepository.findById(userId)
	        .orElseThrow(() -> new ResourceNotFoundException("User not found"));

	    List<StoredFileInfo> files = fileRepository.findAllFileInfoByOwner(user);

	    return files.stream()
	        .map(file -> new StoredFileDTO(
	            file.getFileId(),
	            file.getFileName(),
	            file.getContentType(),
	            file.getSize(),
	            file.getUploadDate(), 
	            file.getExpiryDate()
	        ))
	        .collect(Collectors.toList());
	}

	@Override
	public void removeFile(Long userId, UUID fileId) {
		// can swap to retrieving the user from authentication w/o passing the userId
	
	 StoredFile file = fileRepository.findById(fileId)
			 .orElseThrow(() -> new ResourceNotFoundException("File not found"));
	 if (!file.getOwner().getUserId().equals(userId)) 
		    throw new AccessDeniedException("You are not allowed to delete this file.");
		
	 fileRepository.delete(file);
		
	}

	@Override
	public StoredFile getFileById(UUID fileId) {
		return fileRepository.findById(fileId).orElseThrow(()-> new ResourceNotFoundException("file not found"));
		
	}

	@Override
	@Transactional
	public void updateExpiryDate(UUID fileId, ExpiryDateUpdateRequest request) {
		StoredFile file = fileRepository.findById(fileId).orElseThrow(()-> new ResourceNotFoundException("File not found!"));
		Instant maxExpiry = file.getUploadDate().plus(30, ChronoUnit.DAYS);
		Instant expiryRequest = request.getExpiryDate();
		if (expiryRequest.isAfter(maxExpiry)) {
			throw new IllegalArgumentException("Expiry cannot be more than 30 days");
		}
		file.setExpiryDate(expiryRequest);
		fileRepository.save(file);
	}


	@Override
	public Long getTotalFileSizeByUserId(Long userId) {
		return fileRepository.getTotalFileSizeByUserId(userId);
		
	}


	



}
