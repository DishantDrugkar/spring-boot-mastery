package com.example.springboot_series.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class EmployeeResponseDTO {
    private UUID id;
    private String name;
    private String domain;
    private String location;
    private double salary;

    private UUID profileId;
    private String phoneNumber;
    private String address;
}
