package com.example.springboot_series.service;

import com.example.springboot_series.entity.Employee;

import java.util.List;

public interface EmployeeService {
    String createEmployee(Employee employee);
    List<Employee> getEmployee();
    boolean updateEmployee(long id, Employee employee);
    boolean deleteEmployee(long id);
}
