package com.storage.entity;

import java.time.Instant;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
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
	    private UUID fileId;

	    private String fileName;           // Original file name
	    private String storedFileName;     // Unique name used on disk/S3
	    private String contentType;
	    private Long size;
	    private Instant uploadDate;

	    @Lob
	    @Basic(fetch = FetchType.LAZY)     // Optimize performance for large files
	    private byte[] data;

	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "user_id")
	    private User owner;
	
	
}
