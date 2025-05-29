package com.storage.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.storage.dto.StoredFileDTO;
import com.storage.entity.StoredFile;
import com.storage.repository.StoredFileRepository;
import com.storage.service.StoredFileService;

@RestController
@RequestMapping("/api/")
public class FileController {
	
	private final StoredFileService fileService;
	
	
	public FileController(StoredFileService fileService) {
		super();
		this.fileService = fileService;
	}

	@PostMapping("upload/{userId}")
	public ResponseEntity<String> uploadFile(
						@PathVariable Long userId,
						@RequestParam("file") MultipartFile file) throws IOException {
		fileService.addFile(file, userId);
	    return ResponseEntity.ok("File uploaded successfully");
	}
	
	
	
	@GetMapping("user/files/{userId}")
	private ResponseEntity<List<StoredFileDTO>> getUserFiles(@PathVariable Long userId){
		List<StoredFileDTO> files = fileService.getAllFiles(userId);
		return new ResponseEntity<List<StoredFileDTO>>(files, HttpStatus.OK); 
	}
	
	@DeleteMapping("user/files/{userId}/{fileId}")
	private ResponseEntity<String> deleteFile(@PathVariable Long userId,
											  @PathVariable UUID fileId){
		fileService.removeFile(userId, fileId);
										
		return new ResponseEntity<String>("File deleted!", HttpStatus.OK);
		
		
	}
	
	
}
