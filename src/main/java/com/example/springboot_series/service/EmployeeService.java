package com.example.springboot_series.service;

import com.example.springboot_series.dto.EmployeeRequestDTO;
import com.example.springboot_series.dto.EmployeeResponseDTO;
import com.example.springboot_series.entity.Employee;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EmployeeService {
    String createEmployee(EmployeeRequestDTO requestDTO);
    List<EmployeeResponseDTO> getEmployee();
    Optional<EmployeeResponseDTO> getEmployeeById(UUID id);
    boolean updateEmployee(UUID id, EmployeeRequestDTO requestDTO);
    boolean deleteEmployee(UUID id);
}
