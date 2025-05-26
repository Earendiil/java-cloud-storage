package com.storage.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.storage.dto.UserDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/")
public class UserController {

	@PostMapping("create")
	private ResponseEntity<String> createUser (@Valid @RequestBody UserDTO userDTO){
		
		
		return new ResponseEntity<String>("User created", HttpStatus.OK);
	}
	
	@GetMapping("user/{userId}")
	private ResponseEntity<UserDTO> getUser (@RequestParam Long userId){
		UserDTO userDTO = new UserDTO();
		
		
		return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
	}
	
	
	@PutMapping("user/{userId}")
	private ResponseEntity<String> updateUser (@Valid @RequestParam Long userId, @RequestBody UserDTO userDTO){
		
		return new ResponseEntity<String>("User updated", HttpStatus.OK);
	}
	
	@DeleteMapping("delete/{userId}")
	private ResponseEntity<String> deleteUser (@RequestParam Long userId){
		
		return new ResponseEntity<String>("User deleted", HttpStatus.OK);
	}
	
	
}
