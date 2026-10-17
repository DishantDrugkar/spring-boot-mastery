package com.example.springboot_series.service;

import com.example.springboot_series.entity.Employee;
import com.example.springboot_series.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    private EmployeeRepository employeeRepository;


    @Override
    @Transactional
    public String createEmployee(Employee employee) {
        employeeRepository.save(employee);
        return "Employee added Successfully !!";
    }

    @Override
    public List<Employee> getEmployee() {
        return employeeRepository.findAll();
    }

    @Override
    public Optional<Employee> getEmployeeById(UUID id) {
        return employeeRepository.findById(id);
    }

    @Override
    public boolean updateEmployee(UUID id, Employee employee) {
        if(employeeRepository.existsById(id)){
            Employee existingEmployee = employeeRepository.findById(id).orElse(null);
            if(existingEmployee != null){
                existingEmployee.setName(employee.getName());
                existingEmployee.setDomain(employee.getDomain());
                existingEmployee.setLocation(employee.getLocation());
                existingEmployee.setSalary(employee.getSalary());
                employeeRepository.save(existingEmployee);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteEmployee(UUID id) {
        if(employeeRepository.existsById(id)){
            employeeRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
