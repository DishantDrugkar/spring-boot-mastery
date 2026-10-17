package com.example.springboot_series.entity;

import jakarta.persistence.*;
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
    private String name;
    private String domain;
    private String location;
    private double salary;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_id",unique = true)
    private EmployeeProfile profile;
}
