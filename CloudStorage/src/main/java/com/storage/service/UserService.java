package com.storage.service;

import com.storage.dto.SignupRequest;
import com.storage.dto.UserDTO;

import jakarta.validation.Valid;

public interface UserService {

	void saveUser(@Valid SignupRequest signupRequest);

	UserDTO findUser(Long userId);

}
