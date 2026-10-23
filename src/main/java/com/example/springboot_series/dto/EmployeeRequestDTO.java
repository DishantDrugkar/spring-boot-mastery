package com.example.springboot_series.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeRequestDTO {
    @NotBlank(message = "Employee name cannnot be empty")
    private String name;
    private String domain;
    private String location;
    private double salary;

    private String phoneNumber;
    private String address;
}
