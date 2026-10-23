package com.example.springboot_series.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "Employee_db")
@Getter
@Setter
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    @Size(min = 2, max = 50)
    private String name;
    private String domain;
    private String location;

    @Positive
    private double salary;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_id",unique = true)
    private EmployeeProfile profile;
}
