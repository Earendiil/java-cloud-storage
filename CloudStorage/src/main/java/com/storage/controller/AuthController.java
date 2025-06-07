package com.storage.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.storage.dto.LoginRequest;
import com.storage.dto.LoginResponse;
import com.storage.entity.User;
import com.storage.repository.UserRepository;
import com.storage.security.JwtUtils;
import com.storage.security.services.CustomUserDetailsService;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtUtils jwtUtils;
    private final CustomUserDetailsService userDetailsService;
    private final UserRepository userRepository;
    

    public AuthController(AuthenticationManager authManager, JwtUtils jwtUtils,
			CustomUserDetailsService userDetailsService,UserRepository userRepository) {
		super();
		this.authManager = authManager;
		this.jwtUtils = jwtUtils;
		this.userDetailsService = userDetailsService;
		this.userRepository = userRepository;
	}



    @PostMapping("login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        Authentication auth = authManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getInput(), request.getPassword())
        );

        User user = userRepository.findByUsernameOrEmail(request.getInput(), request.getInput())
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String token = jwtUtils.generateToken(user.getUsername());

        return ResponseEntity.ok(
            new LoginResponse(token, user.getUserId(), user.getUsername(), user.getEmail())
        );
    }

    
    

    /*   @PostMapping("/logout") will be handled by Frontend */
    

		
		
		
	
	
}
