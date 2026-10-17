package com.example.springboot_series.service;

import com.example.springboot_series.entity.Employee;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EmployeeService {
    String createEmployee(Employee employee);
    List<Employee> getEmployee();
    Optional<Employee> getEmployeeById(UUID id);
    boolean updateEmployee(UUID id, Employee employee);
    boolean deleteEmployee(UUID id);
}
