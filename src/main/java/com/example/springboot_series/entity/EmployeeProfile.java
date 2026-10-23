package com.example.springboot_series.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "employee_profile")
@Data
public class EmployeeProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Pattern(regexp = "^[0-9]{10}$")
    private String phoneNumber;
    private String address;
}
