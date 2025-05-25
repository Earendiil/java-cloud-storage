package com.storage.dto;

import java.time.Instant;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoredFileDTO {

	private UUID id;
	private String originalFileName;     
    private String contentType;          
    private Long size;                   
    private Instant uploadDate;
    
    
}
