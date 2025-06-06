package com.storage.service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.storage.dto.ExpiryDateUpdateRequest;
import com.storage.dto.StoredFileDTO;
import com.storage.entity.StoredFile;

public interface StoredFileService {

	void addFile(MultipartFile file, Long userId) throws IOException;

	List<StoredFileDTO> getAllFiles(Long userId);

	void removeFile(Long userId, UUID fileId);

	StoredFile getFileById(UUID fileId);

	void updateExpiryDate(UUID fileId, ExpiryDateUpdateRequest request);

}
