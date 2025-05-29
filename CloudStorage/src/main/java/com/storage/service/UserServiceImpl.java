package com.storage.service;

import org.modelmapper.ModelMapper;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.storage.dto.SignupRequest;
import com.storage.dto.UserDTO;
import com.storage.entity.User;
import com.storage.repository.UserRepository;
import jakarta.validation.Valid;

@Service
public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepository;
	private final ModelMapper modelMapper;
	
	public UserServiceImpl(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
		this.modelMapper = new ModelMapper();
	}


	@Override
	public void saveUser(@Valid SignupRequest signupRequest) {
		if( userRepository.existsByEmail(signupRequest.getEmail())) {
			throw new IllegalArgumentException("Email already exists");
		}
		User user = modelMapper.map(signupRequest, User.class);
		user.setPassword(signupRequest.getPassword());
		userRepository.save(user);
	}


	@Override
	public UserDTO findUser(Long userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new ConfigDataResourceNotFoundException(null));
		return modelMapper.map(user, UserDTO.class);
	}

}
