package com.storage.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpiryDateUpdateRequest {
	
	 	private Instant expiryDate;


	}