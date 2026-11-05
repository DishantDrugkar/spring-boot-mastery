package com.example.springboot_series.service;

import com.example.springboot_series.dto.EmployeeRequestDTO;
import com.example.springboot_series.dto.EmployeeResponseDTO;
import com.example.springboot_series.entity.Employee;
import com.example.springboot_series.entity.EmployeeProfile;
import com.example.springboot_series.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    private EmployeeRepository employeeRepository;

    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Override
    @Transactional
    @CacheEvict(value = "allEmployees", allEntries = true)
    public String createEmployee(EmployeeRequestDTO requestDTO) {

        // Step 1: Create Employee object
        logger.info("Creating new employeee");
        Employee employee = new Employee();

        // Step 2: Copy employee details from DTO
        employee.setName(requestDTO.getName());
        employee.setDomain(requestDTO.getDomain());
        employee.setLocation(requestDTO.getLocation());
        employee.setSalary(requestDTO.getSalary());

        // Step 3: Create EmployeeProfile object
        EmployeeProfile profile = new EmployeeProfile();

        profile.setPhoneNumber(requestDTO.getPhoneNumber());
        profile.setAddress(requestDTO.getAddress());

        // Step 4: Associate profile with employee
        employee.setProfile(profile);

        // Step 5: Save employee
        employeeRepository.save(employee);
        logger.info("Employee created successfully with ID: {}", employee.getId());
        return "Employee added Successfully !!";
    }

    @Override
    @Cacheable(value = "allEmployees")
    public List<EmployeeResponseDTO> getEmployee() {
        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeResponseDTO> responseList = new ArrayList<>();
        for (Employee employee : employees) {
            EmployeeResponseDTO responseDTO = convertToDTO(employee);
            responseList.add(responseDTO);
        }
        return responseList;
    }

    @Override
    @Cacheable(value = "employees", key = "#id")
    public Optional<EmployeeResponseDTO> getEmployeeById(UUID id) {
        logger.info("Fetching employee with ID: {}", id);
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            EmployeeResponseDTO responseDTO = convertToDTO(employee);
            logger.info("Employee found with ID: {}", id);
            return Optional.of(responseDTO);
        }
        logger.warn("Employee not found with ID: {}", id);
        return Optional.empty();
    }

    @Override
    @Transactional
    @Caching(
            put = {
                    @CachePut(value = "employees", key = "#id")
            },
            evict = {
                    @CacheEvict(value = "allEmployees", allEntries = true)
            }
    )
    public EmployeeResponseDTO updateEmployee(UUID id, EmployeeRequestDTO requestDTO) {
        logger.info("Updating employee with ID: {}", id);

        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        existingEmployee.setName(requestDTO.getName());
        existingEmployee.setDomain(requestDTO.getDomain());
        existingEmployee.setLocation(requestDTO.getLocation());
        existingEmployee.setSalary(requestDTO.getSalary());

        Employee updatedEmployee = employeeRepository.save(existingEmployee);
        logger.info("Employee updated successfully with ID: {}", id);
        return convertToDTO(updatedEmployee);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "employees", key = "#id"),
            @CacheEvict(value = "allEmployees", allEntries = true)
    })
    public boolean deleteEmployee(UUID id) {
        logger.warn("Deleting employee with ID: {}", id);
        if(employeeRepository.existsById(id)){
            employeeRepository.deleteById(id);
            logger.info("Employee deleted successfully with ID: {}", id);
            return true;
        }
        logger.warn("Employee not found for deletion with ID: {}", id);
        return false;
    }

    private EmployeeResponseDTO convertToDTO(Employee employee) {

        EmployeeResponseDTO responseDTO = new EmployeeResponseDTO();

        responseDTO.setId(employee.getId());
        responseDTO.setName(employee.getName());
        responseDTO.setDomain(employee.getDomain());
        responseDTO.setLocation(employee.getLocation());
        responseDTO.setSalary(employee.getSalary());

        if (employee.getProfile() != null) {
            responseDTO.setProfileId(employee.getProfile().getId());
            responseDTO.setPhoneNumber(employee.getProfile().getPhoneNumber());
            responseDTO.setAddress(employee.getProfile().getAddress());
        }

        return responseDTO;
    }
}
