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

    private UUID fileId;
    private String fileName;         
    private String contentType;
    private Long size;
    private Instant uploadDate;
    private Instant expiryDate;

   
//    private byte[] data;   
    
}
