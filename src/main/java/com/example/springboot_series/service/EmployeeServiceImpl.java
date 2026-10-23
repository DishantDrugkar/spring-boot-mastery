package com.example.springboot_series.service;

import com.example.springboot_series.dto.EmployeeRequestDTO;
import com.example.springboot_series.dto.EmployeeResponseDTO;
import com.example.springboot_series.entity.Employee;
import com.example.springboot_series.entity.EmployeeProfile;
import com.example.springboot_series.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    private EmployeeRepository employeeRepository;


    @Override
    @Transactional
    public String createEmployee(EmployeeRequestDTO requestDTO) {

        // Step 1: Create Employee object
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

        return "Employee added Successfully !!";
    }

    @Override
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
    public Optional<EmployeeResponseDTO> getEmployeeById(UUID id) {

        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            EmployeeResponseDTO responseDTO = convertToDTO(employee);
            return Optional.of(responseDTO);
        }
        return Optional.empty();
    }

    @Override
    public boolean updateEmployee(UUID id, EmployeeRequestDTO requestDTO) {
        if(employeeRepository.existsById(id)){
            Employee existingEmployee = employeeRepository.findById(id).orElse(null);
            if(existingEmployee != null){
                existingEmployee.setName(requestDTO.getName());
                existingEmployee.setDomain(requestDTO.getDomain());
                existingEmployee.setLocation(requestDTO.getLocation());
                existingEmployee.setSalary(requestDTO.getSalary());
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
