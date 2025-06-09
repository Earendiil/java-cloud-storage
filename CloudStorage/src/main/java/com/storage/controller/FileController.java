package com.storage.controller;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.storage.dto.ExpiryDateUpdateRequest;
import com.storage.dto.StoredFileDTO;
import com.storage.entity.StoredFile;
import com.storage.security.services.CustomUserDetails;
import com.storage.service.StoredFileService;
import com.storage.util.FileEncryptionUtil;


@RestController
@RequestMapping("/api")
public class FileController {
	
	private final StoredFileService fileService;
	private final FileEncryptionUtil fileEncryptionUtil;
	
	public FileController(StoredFileService fileService, FileEncryptionUtil fileEncryptionUtil) {
		super();
		this.fileService = fileService;
		this.fileEncryptionUtil = fileEncryptionUtil;
	}

	@PostMapping("upload/{userId}")
	public ResponseEntity<String> uploadFile(
						@PathVariable Long userId,
						@RequestParam("file") MultipartFile file) throws Exception {
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
	
	@GetMapping("download/{fileId}")
	public ResponseEntity<Resource> downloadFile(@PathVariable UUID fileId) {
	    StoredFile file = fileService.getFileById(fileId);

	    byte[] decrypted;
	    try {
	        decrypted = fileEncryptionUtil.decrypt(file.getData());
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	    }

	    return ResponseEntity.ok()
	        .contentType(MediaType.parseMediaType(file.getContentType()))
	        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
	        .body(new ByteArrayResource(decrypted));
	}

	@PutMapping("/files/{fileId}/expiry")
	private ResponseEntity<String> updateExpiryDate(@PathVariable UUID fileId,
											   @RequestBody ExpiryDateUpdateRequest request){
		fileService.updateExpiryDate(fileId, request);
		return new ResponseEntity<String>("Expiry date updated!", HttpStatus.OK);
	}
	
	@GetMapping("/files/total-size")
	public ResponseEntity<Long> getTotalFileSize(Authentication authentication) {
	    CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
	    Long userId = userDetails.getUserId();
	    Long totalSize = fileService.getTotalFileSizeByUserId(userId);
	    return ResponseEntity.ok(totalSize);
	}
	
}
