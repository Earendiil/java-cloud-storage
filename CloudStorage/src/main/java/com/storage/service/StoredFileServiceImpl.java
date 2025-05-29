package com.storage.service;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.storage.dto.StoredFileDTO;
import com.storage.entity.StoredFile;
import com.storage.entity.User;
import com.storage.exceptions.ResourceNotFoundException;
import com.storage.repository.StoredFileRepository;
import com.storage.repository.UserRepository;

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
	
		StoredFile storedFile = new StoredFile();
		storedFile.setFileName(file.getOriginalFilename());
        storedFile.setStoredFileName(generateUniqueFilename(file.getOriginalFilename()));
        storedFile.setContentType(file.getContentType());
        storedFile.setSize(file.getSize());
        storedFile.setUploadDate(Instant.now());
        storedFile.setData(file.getBytes());
        storedFile.setOwner(user);
        
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
	public List<StoredFileDTO> getAllFiles(Long userId) {
	    User user = userRepository.findById(userId)
	        .orElseThrow(() -> new ResourceNotFoundException("User not found"));

	    List<StoredFile> files = fileRepository.findAllByOwner(user);

	    return files.stream()
	        .map(file -> new StoredFileDTO(
	            file.getId(),
	            file.getFileName(),
	            file.getContentType(),
	            file.getSize(),
	            file.getUploadDate(),
	            file.getData()
	        ))
	        .collect(Collectors.toList());
	}

	@Override
	public void removeFile(Long userId, UUID fileId) {
		// can swap to retrieving the user from authantication w/o passing the userId
	
	 StoredFile file = fileRepository.findById(fileId)
			 .orElseThrow(() -> new ResourceNotFoundException("File not found"));
	 if (!file.getOwner().getUserId().equals(userId)) 
		    throw new AccessDeniedException("You are not allowed to delete this file.");
		
	 fileRepository.delete(file);
		
	}


}
