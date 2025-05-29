package com.storage.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface StoredFileService {

	void addFile(MultipartFile file, Long userId) throws IOException;

}
