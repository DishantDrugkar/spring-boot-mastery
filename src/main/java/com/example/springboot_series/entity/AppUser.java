package com.example.springboot_series.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "app_users")
@Data
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;

    public AppUser() {

    }

    public AppUser(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;

    }
}

