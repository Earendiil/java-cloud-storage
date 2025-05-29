package com.storage.service;

import org.modelmapper.ModelMapper;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.storage.dto.ChangePasswordRequest;
import com.storage.dto.SignupRequest;
import com.storage.dto.UserDTO;
import com.storage.entity.User;
import com.storage.exceptions.PasswordMismatchException;
import com.storage.exceptions.ResourceNotFoundException;
import com.storage.repository.UserRepository;
import jakarta.validation.Valid;

@Service
public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepository;
	private final ModelMapper modelMapper;
	private final PasswordEncoder passwordEncoder;
	
	public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		super();
		this.userRepository = userRepository;
		this.modelMapper = new ModelMapper();
		this.passwordEncoder = passwordEncoder;
	}


	@Override
	public void saveUser(@Valid SignupRequest signupRequest) {
	    if (userRepository.existsByEmail(signupRequest.getEmail())) {
	        throw new IllegalArgumentException("Email already exists");
	    }

	    if (!signupRequest.getPassword().equals(signupRequest.getConfirmPassword())) {
	    	throw new PasswordMismatchException("Passwords do not match");

	    }

	    User user = modelMapper.map(signupRequest, User.class);
	    user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
	    userRepository.save(user);
	}



	@Override
	public UserDTO findUser(Long userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new ConfigDataResourceNotFoundException(null));
		return modelMapper.map(user, UserDTO.class);
	}


	@Override
	public UserDTO editPassword(@Valid Long userId, ChangePasswordRequest changePasswordRequest) {
		User user =	userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found"));
		
		user.setPassword(changePasswordRequest.getNewPassword());
		return null;
	}

}
