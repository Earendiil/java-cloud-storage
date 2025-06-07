package com.storage.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.storage.dto.ChangePasswordRequest;
import com.storage.dto.SignupRequest;
import com.storage.dto.UserDTO;
import com.storage.security.services.CustomUserDetails;
import com.storage.service.UserService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api")
public class UserController {
	
	private final UserService userService;
	

	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}

	
	@PostMapping("create")
	private ResponseEntity<String> createUser (@Valid @RequestBody SignupRequest signupRequest){
		userService.saveUser(signupRequest);
		return new ResponseEntity<String>("User created!", HttpStatus.CREATED);
	}
	
	@GetMapping("user/{userId}")
	private ResponseEntity<UserDTO> getUser (@PathVariable Long userId){
		UserDTO user = userService.findUser(userId);		
		return new ResponseEntity<UserDTO>(user, HttpStatus.OK);
	}
	
	
	@PutMapping("user/{userId}")
	private ResponseEntity<String> changePassword (@Valid @PathVariable Long userId, @RequestBody ChangePasswordRequest changePasswordRequest){
		userService.editPassword(userId, changePasswordRequest);
		return new ResponseEntity<String>("Password updated!", HttpStatus.OK);
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<String> deleteCurrentUser(Authentication authentication) {
	    CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
	    Long userId = userDetails.getUserId();

	    userService.removeUser(userId);
	    return ResponseEntity.ok("User deleted!");
	}

	
}
