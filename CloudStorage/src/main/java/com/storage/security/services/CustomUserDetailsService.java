package com.storage.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.storage.entity.User;
import com.storage.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String input) throws UsernameNotFoundException {
        User user = input.contains("@")
            ? userRepository.findByEmail(input).orElseThrow(() -> new UsernameNotFoundException("Email not found"))
            : userRepository.findByUsername(input).orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        return new CustomUserDetails(user.getUserId(), user.getUsername(), user.getPassword());
    }
}
