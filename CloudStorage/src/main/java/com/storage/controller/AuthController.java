package com.storage.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.storage.dto.LoginRequest;
import com.storage.dto.LoginResponse;
import com.storage.dto.SignupRequest;
import com.storage.security.CustomUserDetailsService;
import com.storage.security.JwtUtils;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtUtils jwtUtils;
    private final CustomUserDetailsService userDetailsService;
    
    

    public AuthController(AuthenticationManager authManager, JwtUtils jwtUtils,
			CustomUserDetailsService userDetailsService) {
		super();
		this.authManager = authManager;
		this.jwtUtils = jwtUtils;
		this.userDetailsService = userDetailsService;
	}



	@PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Authentication auth = authManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getInput(), request.getPassword())
        );

        String token = jwtUtils.generateToken(request.getInput());
        return ResponseEntity.ok(new LoginResponse(token));
    }
	

		
		
		
	
	
}
