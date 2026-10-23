package com.example.springboot_series.controller;

import com.example.springboot_series.dto.EmployeeRequestDTO;
import com.example.springboot_series.dto.EmployeeResponseDTO;
import com.example.springboot_series.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
     public String createEmployee(@Valid @RequestBody EmployeeRequestDTO requestDTO){
        employeeService.createEmployee(requestDTO);
        return "Employee is added";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/get")
    public List<EmployeeResponseDTO> getEmployee() {
        return employeeService.getEmployee();
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/get/{id}")
    ResponseEntity<EmployeeResponseDTO> getEmployeeById(@PathVariable UUID id){
        Optional<EmployeeResponseDTO> employeeById = employeeService.getEmployeeById(id);
        if(employeeById.isPresent()){
            return new ResponseEntity<>(employeeById.get(), HttpStatus.OK);
        }
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateEmployee(@PathVariable UUID id, @Valid @RequestBody EmployeeRequestDTO requestDTO) {

        boolean updated = employeeService.updateEmployee(id, requestDTO);
        if (updated) {
            return ResponseEntity.ok("Employee details updated successfully!");
        }
        return ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable UUID id) {
        boolean deleted = employeeService.deleteEmployee(id);
        if (deleted) {
            return ResponseEntity.ok("Employee deleted successfully!");
        }
        return ResponseEntity.notFound().build();
    }

}
