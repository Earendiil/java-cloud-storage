package com.storage.entity;

import java.time.Instant;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "files")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoredFile {

	@Id
	@GeneratedValue
	private  UUID id;
	
	private String fileName;     
    private String storedFileName;       // Renamed unique filename (on disk or S3)
    private String contentType;          // image/png, application/pdf etc.
    private Long size;                   // in bytes
    private String storagePath;          // relative or full path (optional)
    private Instant uploadDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User owner;
      
	
	
	
}
