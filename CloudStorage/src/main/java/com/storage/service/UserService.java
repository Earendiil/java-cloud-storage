package com.storage.service;

import com.storage.dto.UserDTO;

import jakarta.validation.Valid;

public interface UserService {

	void saveUser(@Valid UserDTO userDTO);

	UserDTO findUser(Long userId);

}
