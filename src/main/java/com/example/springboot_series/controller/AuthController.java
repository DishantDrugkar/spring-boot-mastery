package com.example.springboot_series.controller;

import com.example.springboot_series.entity.AppUser;
import com.example.springboot_series.entity.RefreshToken;
import com.example.springboot_series.repository.UserRepository;
import com.example.springboot_series.service.JwtService;
import com.example.springboot_series.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.get("username"),
                        request.get("password")
                )
        );

        String username = authentication.getName();
        AppUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String accessToken = jwtService.generateAccessToken(username);
        String refreshToken = jwtService.generateRefreshToken(username);
        refreshTokenService.createRefreshToken(user, refreshToken);
        return Map.of(
                "accessToken", accessToken,
                "refreshToken", refreshToken,
                "tokenType", "Bearer"
        );
    }

    @PostMapping("/refresh")
    public Map<String, String> refresh(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");

        if (refreshToken == null || refreshToken.isBlank()) {
            throw new IllegalArgumentException("Refresh token is required");
        }

        // Validate JWT signature, username, expiry and token type
        String username = jwtService.extractUsername(refreshToken);

        if (!jwtService.isRefreshTokenValid(refreshToken, username)) {
            throw new IllegalArgumentException("Invalid refresh token");
        }

        // Verify token against database
        RefreshToken storedToken = refreshTokenService.verifyRefreshToken(refreshToken);

        // Ensure the database token belongs to the same username
        if (!storedToken.getUser().getUsername().equals(username)) {
            throw new IllegalArgumentException("Invalid refresh token");
        }

        String newAccessToken = jwtService.generateAccessToken(username);

        return Map.of(
                "accessToken", newAccessToken,
                "tokenType", "Bearer"
        );
    }

    @PostMapping("/logout")
    public Map<String, String> logout(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");

        if (refreshToken == null || refreshToken.isBlank()) {
            throw new IllegalArgumentException("Refresh token is required");
        }
        String username = jwtService.extractUsername(refreshToken);
        if (!jwtService.isRefreshTokenValid(refreshToken, username)) {
            throw new IllegalArgumentException("Invalid refresh token");
        }

        refreshTokenService.verifyRefreshToken(refreshToken);

        refreshTokenService.revokeRefreshToken(refreshToken);

        return Map.of("message", "Logged out successfully");
    }
}