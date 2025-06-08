package com.storage.service;

import com.storage.dto.ChangePasswordRequest;
import com.storage.dto.SignupRequest;
import com.storage.dto.UserDTO;

import jakarta.validation.Valid;

public interface UserService {

	void saveUser(@Valid SignupRequest signupRequest);

	UserDTO findUser(Long userId);

	void editPassword(@Valid Long userId, ChangePasswordRequest changePasswordRequest);

	void removeUser(Long userId);

	


}
