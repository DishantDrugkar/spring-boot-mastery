package com.example.springboot_series.controller;

import com.example.springboot_series.service.JwtService;
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

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> request) {

        System.out.println("Login endpoint reached");
        System.out.println("Username received: " + request.get("username"));

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.get("username"),
                        request.get("password")
                )
        );

        System.out.println("Authentication successful: " + authentication.isAuthenticated());

        String token = jwtService.generateToken(authentication.getName());

        return Map.of("token", token);
    }
}