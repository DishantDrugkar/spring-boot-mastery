package com.example.springboot_series.SpringAI;

import com.example.springboot_series.service.EmployeeService;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class EmployeeTools {

    private final EmployeeService employeeService;

    public EmployeeTools(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Tool(description = "Get complete employee details using employee ID")
    public String getEmployeeById(String id) {

        UUID employeeId = UUID.fromString(id);

        var employee = employeeService.getEmployeeById(employeeId);

        if (employee.isEmpty()) {
            return "Employee not found with ID: " + id;
        }

        var emp = employee.get();

        return """
            Employee Details:
            Employee ID: %s
            Name: %s
            Domain: %s
            Location: %s
            Salary: %s
            """.formatted(
                emp.getId(),
                emp.getName(),
                emp.getDomain(),
                emp.getLocation(),
                emp.getSalary()
        );
    }

    @Tool(description = "Fetch all actual employee records from the company database. Use this tool whenever the user asks to list, show, or count employees. Return only real employee data retrieved from the database.")
    public String getAllEmployees() {

        System.out.println("========== GET ALL EMPLOYEES TOOL CALLED ==========");

        var employees = employeeService.getEmployee();

        if (employees == null || employees.isEmpty()) {
            System.out.println("No employees found!");

            return "No employees found in the company database.";
        }

        StringBuilder result = new StringBuilder();

        result.append("Total Employees: ")
                .append(employees.size())
                .append("\n\n");

        for (var employee : employees) {

            result.append("Employee ID: ")
                    .append(employee.getId())
                    .append("\n");

            result.append("Name: ")
                    .append(employee.getName())
                    .append("\n");

            result.append("Domain: ")
                    .append(employee.getDomain())
                    .append("\n");

            result.append("Location: ")
                    .append(employee.getLocation())
                    .append("\n");

            result.append("Salary: ")
                    .append(employee.getSalary())
                    .append("\n");

            result.append("-------------------------\n");
        }

        System.out.println(result);

        System.out.println("========== TOOL EXECUTION COMPLETED ==========");

        return result.toString();
    }
}