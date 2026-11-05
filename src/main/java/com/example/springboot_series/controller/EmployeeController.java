package com.example.springboot_series.controller;

import com.example.springboot_series.dto.EmployeeRequestDTO;
import com.example.springboot_series.dto.EmployeeResponseDTO;
import com.example.springboot_series.service.EmployeeService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
     public String createEmployee(@Valid @RequestBody EmployeeRequestDTO requestDTO){
        employeeService.createEmployee(requestDTO);
        logger.info("POST request received to create employee");
        return "Employee is added";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/get")
    public List<EmployeeResponseDTO> getEmployee() {
        logger.info("GET request received to fetch all employees");
        return employeeService.getEmployee();
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/get/{id}")
    ResponseEntity<EmployeeResponseDTO> getEmployeeById(@PathVariable UUID id){
        Optional<EmployeeResponseDTO> employeeById = employeeService.getEmployeeById(id);
        if(employeeById.isPresent()){
            logger.info("GET request received for employee ID: {}", id);
            return new ResponseEntity<>(employeeById.get(), HttpStatus.OK);
        }
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<EmployeeResponseDTO> updateEmployee(
            @PathVariable UUID id,
            @RequestBody EmployeeRequestDTO requestDTO) {

        EmployeeResponseDTO response =
                employeeService.updateEmployee(id, requestDTO);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable UUID id) {
        boolean deleted = employeeService.deleteEmployee(id);
        if (deleted) {
            logger.info("DELETE request received for employee ID: {}", id);
            return ResponseEntity.ok("Employee deleted successfully!");
        }
        return ResponseEntity.notFound().build();
    }

}
