package com.example.springboot_series.controller;

import com.example.springboot_series.entity.Employee;
import com.example.springboot_series.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PutMapping("/update/{id}")
    String updateEmployee(@PathVariable long id, @RequestBody Employee employee){
        employeeService.updateEmployee(id,employee);
        return "Employee details updated !!";
    }

    @DeleteMapping("/delete/{id}")
    String deleteEmployee(@PathVariable long id){
        employeeService.deleteEmployee(id);
        return "Employee Deleted !!";
    }

}
