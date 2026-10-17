package com.example.springboot_series.controller;

import com.example.springboot_series.entity.Employee;
import com.example.springboot_series.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/create")
     public String createEmployee(@RequestBody Employee employee){
        employeeService.createEmployee(employee);
        return "Employee is added";
    }

    @GetMapping("/get")
    List<Employee> getEmployee(){
        return employeeService.getEmployee();
    }

    @GetMapping("/get/{id}")
    ResponseEntity<Employee> getEmployeeById(@PathVariable UUID id){
        Optional<Employee> employeeById = employeeService.getEmployeeById(id);
        if(employeeById.isPresent()){
            return new ResponseEntity<>(employeeById.get(), HttpStatus.OK);
        }
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update/{id}")
    String updateEmployee(@PathVariable UUID id, @RequestBody Employee employee){
        employeeService.updateEmployee(id,employee);
        return "Employee details updated !!";
    }

    @DeleteMapping("/delete/{id}")
    String deleteEmployee(@PathVariable UUID id){
        employeeService.deleteEmployee(id);
        return "Employee Deleted !!";
    }

}
