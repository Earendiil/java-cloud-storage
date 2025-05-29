package com.storage.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.storage.dto.StoredFileDTO;

public interface StoredFileService {

	void addFile(MultipartFile file, Long userId) throws IOException;

	List<StoredFileDTO> getAllFiles(Long userId);

}
