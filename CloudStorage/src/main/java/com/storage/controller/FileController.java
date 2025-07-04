package com.storage.controller;

import org.springframework.core.io.ByteArrayResource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	private static final Logger logger = LoggerFactory.getLogger(FileController.class);
	
	private final StoredFileService fileService;
	private final FileEncryptionUtil fileEncryptionUtil;
	
	public FileController(StoredFileService fileService, FileEncryptionUtil fileEncryptionUtil) {
		this.fileService = fileService;
		this.fileEncryptionUtil = fileEncryptionUtil;
	}

	@PostMapping("upload/{userId}")
	public ResponseEntity<String> uploadFile(@PathVariable Long userId,
											 @RequestParam("file") MultipartFile file) throws Exception {
		logger.info("Received file upload request for userId={}, fileName={}", userId, file.getOriginalFilename());
		fileService.addFile(file, userId);
		logger.info("File uploaded successfully for userId={}, fileName={}", userId, file.getOriginalFilename());
	    return ResponseEntity.ok("File uploaded successfully");
	}
	
	@GetMapping("user/files/{userId}")
	private ResponseEntity<List<StoredFileDTO>> getUserFiles(@PathVariable Long userId){
		logger.info("Fetching files for userId={}", userId);
		List<StoredFileDTO> files = fileService.getAllFiles(userId);
		logger.debug("Returned {} files for userId={}", files.size(), userId);
		return new ResponseEntity<>(files, HttpStatus.OK); 
	}
	
	@DeleteMapping("user/files/{userId}/{fileId}")
	private ResponseEntity<String> deleteFile(@PathVariable Long userId,
											  @PathVariable UUID fileId){
		logger.info("Delete request: userId={}, fileId={}", userId, fileId);
		fileService.removeFile(userId, fileId);
		logger.info("File deleted: fileId={}", fileId);
		return new ResponseEntity<>("File deleted!", HttpStatus.OK);	
	}
	
	@GetMapping("download/{fileId}")
	public ResponseEntity<Resource> downloadFile(@PathVariable UUID fileId) {
	    logger.info("Download request for fileId={}", fileId);
	    StoredFile file = fileService.getFileById(fileId);

	    byte[] decrypted;
	    try {
	        decrypted = fileEncryptionUtil.decrypt(file.getData());
	    } catch (Exception e) {
	        logger.error("File decryption failed for fileId={}: {}", fileId, e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	    }

	    logger.info("File decrypted and ready for download: fileName={}", file.getFileName());
	    return ResponseEntity.ok()
	        .contentType(MediaType.parseMediaType(file.getContentType()))
	        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
	        .body(new ByteArrayResource(decrypted));
	}

	@PutMapping("/files/{fileId}/expiry")
	private ResponseEntity<String> updateExpiryDate(@PathVariable UUID fileId,
													@RequestBody ExpiryDateUpdateRequest request){
		logger.info("Updating expiry date for fileId={} to {}", fileId, request.getExpiryDate());
		fileService.updateExpiryDate(fileId, request);
		return new ResponseEntity<>("Expiry date updated!", HttpStatus.OK);
	}
	
	@GetMapping("/files/total-size")
	public ResponseEntity<Long> getTotalFileSize(Authentication authentication) {
	    CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
	    Long userId = userDetails.getUserId();
	    logger.info("Getting total file size for userId={}", userId);
	    Long totalSize = fileService.getTotalFileSizeByUserId(userId);
	    logger.debug("Total file size for userId={} is {} bytes", userId, totalSize);
	    return ResponseEntity.ok(totalSize);
	}
}
