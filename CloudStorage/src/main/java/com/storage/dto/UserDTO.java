package com.storage.dto;

import java.util.List;

import com.storage.entity.StoredFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

	
	private Long userId;
	private String email;
	private String username;
	private List <StoredFile> files;
	
	
}
