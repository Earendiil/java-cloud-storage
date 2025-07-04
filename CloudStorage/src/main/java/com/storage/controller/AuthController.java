package com.storage.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
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

@RestController
@RequestMapping("/api/auth/")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthenticationManager authManager;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;

    public AuthController(AuthenticationManager authManager, JwtUtils jwtUtils,
                          UserRepository userRepository) {
        super();
        this.authManager = authManager;
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
    }

    @PostMapping("login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        String input = request.getInput();

        try {
            Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(input, request.getPassword())
            );

            User user = userRepository.findByUsernameOrEmail(input, input)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            String token = jwtUtils.generateToken(user.getUsername());

            logger.info("User logged in successfully: username='{}', userId={}", user.getUsername(), user.getUserId());

            return ResponseEntity.ok(
                new LoginResponse(token, user.getUserId(), user.getUsername(), user.getEmail())
            );

        } catch (BadCredentialsException ex) {
            logger.warn("Failed login attempt for input '{}': bad credentials", input);
            throw ex;  // or return 401 response here
        } catch (UsernameNotFoundException ex) {
            logger.warn("Failed login attempt: user not found for input '{}'", input);
            throw ex; // or handle similarly
        } catch (Exception ex) {
            logger.error("Unexpected error during login for input '{}': {}", input, ex.getMessage());
            throw ex; // or return 500 response
        }
    }

    

    /*   @PostMapping("/logout") will be handled by Frontend */
    
		
	
	
}
