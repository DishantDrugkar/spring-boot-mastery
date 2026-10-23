# Spring Boot Employee Management System

A REST API project built using Spring Boot, Java, Spring Security, JWT, JPA, and PostgreSQL.

## 📂 Files and Their Responsibilities

### 1. Entity Layer — Database Structure

#### Employee.java
- Represents an employee in the database.
- Stores name, domain, location, salary, and profile relationship.

#### EmployeeProfile.java
- Stores additional employee details.
- Contains phone number and address.

### 2. DTO Layer — Request and Response

#### EmployeeRequestDTO.java
- Receives employee data from the client.
- Contains validation rules such as `@NotBlank`.

#### EmployeeResponseDTO.java
- Defines the employee data returned to the client.
- Prevents directly exposing entity objects in API responses.

### 3. Repository Layer — Database Operations

#### EmployeeRepository.java
- Handles employee database operations.
- Uses `JpaRepository` methods such as `save()`, `findById()`, `findAll()`, and `delete()`.

#### EmployeeProfileRepository.java
- Handles employee profile database operations, if required by the project.

### 4. Service Layer — Business Logic

#### EmployeeService.java
- Defines methods for employee-related operations.

#### EmployeeServiceImpl.java
- Implements business logic for creating, retrieving, updating, and deleting employees.
- Contains `convertToDTO()` to convert an Employee entity into an EmployeeResponseDTO.

### 5. Controller Layer — REST APIs

#### EmployeeController.java
- Exposes REST endpoints for Postman and other API clients.
- Calls the service layer to perform operations.
- Uses `@PreAuthorize` for role-based authorization.

#### Important Endpoints

| HTTP Method | Endpoint | Purpose |
|---|---|---|
| POST | `/api/employee/create` | Create an employee |
| GET | `/api/employee/get` | Retrieve all employees |
| GET | `/api/employee/get/{id}` | Retrieve an employee by ID |
| PUT | `/api/employee/update/{id}` | Update an employee |
| DELETE | `/api/employee/delete/{id}` | Delete an employee |

### 6. Exception Layer — Error Handling

#### GlobalExceptionHandler.java
- Handles validation errors globally.
- Returns structured JSON responses with HTTP `400 Bad Request`.

#### EmployeeNotFoundException.java
- Custom exception representing an employee-not-found situation.
- Full exception handling is currently skipped.

Example validation error:

```json
{
  "status": 400,
  "error": "Validation Failed",
  "message": "Employee name cannot be empty"
}

## 7. SPRING SECURITY LAYER — Authentication and Authorization

### SpringSecurity.java
- Configures endpoint permissions, stateless sessions, exception handlers, and the JWT filter.

### JwtAuthenticationFilter.java
- Reads the Bearer token from the Authorization header.
- Validates the JWT and sets the authenticated user in Spring Security's context.

### RestAuthenticationEntryPoint.java
- Returns a custom HTTP 401 Unauthorized response when authentication is required but missing or invalid.

### RestAccessDeniedHandler.java
- Returns a custom HTTP 403 Forbidden response when an authenticated user lacks the required permission.

### CustomUserDetailsService.java
- Loads user details from the database, including username, password, and roles.

### JwtService.java
- Generates and validates JWTs and extracts the username from a token.

### RefreshTokenService.java
- Handles refresh-token creation, validation, expiry, and related operations.

### RefreshToken.java
- Represents refresh-token data stored in the database.

### RefreshTokenRepository.java
- Handles database operations for refresh tokens.

> **Note:** Refresh-token filenames may differ slightly depending on the exact names used in the project.

---

## 8. COMPLETE REQUEST FLOW

### Typical Employee Request

```text
Postman
   |
   v
EmployeeController
   |
   v
EmployeeService
   |
   v
EmployeeServiceImpl
   |
   v
EmployeeRepository
   |
   v
Database
```

The response returns through the service and controller. The service converts the entity into a response DTO when needed.

### Validation Error Flow

```text
Client Request
      |
      v
@Valid and DTO Validation
      |
      v
MethodArgumentNotValidException
      |
      v
GlobalExceptionHandler
      |
      v
HTTP 400 Bad Request
```

### Security Flow

- The JWT filter checks the Bearer token.
- Authentication identifies the user.
- Authorization rules determine whether the user can access the endpoint.

```text
Client Request
      |
      v
JWT Authentication Filter
      |
      v
JWT Validation
      |
      v
Authentication
      |
      v
Authorization
      |
      v
Controller
      |
      v
Service Layer
      |
      v
Repository
      |
      v
Database
```

---

## 9. FIVE MOST IMPORTANT LAYERS TO REMEMBER

| Layer | Responsibility |
|---|---|
| Entity | Represents the database structure |
| DTO | Handles request and response data |
| Repository | Performs database operations |
| Service | Contains business logic |
| Controller | Handles REST API endpoints |

These five layers form the basic structure of the Employee Management System.

---

## 10. NEXT PLANNED TOPIC

**Pagination and Sorting**

- Pagination using `Pageable`
- Sorting using `Sort`
- Returning paginated API responses
- Testing pagination and sorting with Postman