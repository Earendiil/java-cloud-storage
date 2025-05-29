package com.storage.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {

	@Column(unique = true, nullable = false)
	@NotBlank (message = "email is required")
	@Email(message = "invalid email format")
	private String email;
	
	
	@Column(nullable = false) // enforces non-null in the DB schema too
	@NotBlank(message = "password is required")
	@Size(min = 8, max = 72)
	@Pattern(regexp = ".*[a-zA-Z].*", message = "Must contain at least one alphabetical letter")
	private String password;
	
	@NotBlank
	@Size(min = 8, max = 72)
	private String confirmPassword;
	
}
