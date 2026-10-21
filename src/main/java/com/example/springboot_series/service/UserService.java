package com.example.springboot_series.service;

import com.example.springboot_series.entity.AppUser;
import com.example.springboot_series.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public AppUser createUser(String username, String password, String role) {
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException(
                    "Username already exists"
            );
        }

        AppUser user = new AppUser();

        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password)); // password will encode
        user.setRole(role);

        return userRepository.save(user);
    }

    @Transactional
    public void resetPassword(String username, String newPassword) {
        AppUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    public boolean userServiceExists(String username) {
        return userRepository.existsByUsername(username);
    }


    public boolean verifyPassword(String username, String rawPassword) {
        AppUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return passwordEncoder.matches(rawPassword, user.getPassword());
    }
}