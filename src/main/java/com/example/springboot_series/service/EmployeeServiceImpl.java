package com.example.springboot_series.service;

import com.example.springboot_series.entity.Employee;
import com.example.springboot_series.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    private EmployeeRepository employeeRepository;


    @Override
    public String createEmployee(Employee employee) {
        employeeRepository.save(employee);
        return "Employee added Successfully !!";
    }

    @Override
    public List<Employee> getEmployee() {
        return employeeRepository.findAll();
    }

    @Override
    public boolean updateEmployee(long id, Employee employee) {
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
    public boolean deleteEmployee(long id) {
        if(employeeRepository.existsById(id)){
            employeeRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
