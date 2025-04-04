package com.employeeManagement.controller;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.stream.Collectors;

import com.employeeManagement.dto.EmployeeResponseDTO;
import com.employeeManagement.model.Employee;
import com.employeeManagement.service.EmployeeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
//@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeService employeeService;
    
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<EmployeeResponseDTO>> getAllEmployeesForCurrentUser() {
        try {
            List<Employee> employees = employeeService.findAllEmployeesForCurrentUser();
            List<EmployeeResponseDTO> responseDTOs = employees.stream()
                .map(emp -> employeeService.convertToDTO(emp))
                .collect(Collectors.toList());
            return new ResponseEntity<>(responseDTOs, HttpStatus.OK);
        } catch (AccessDeniedException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
    
    @GetMapping("/find/{id}")
    public ResponseEntity<EmployeeResponseDTO> getEmployeeById(@PathVariable("id") Long id) {
        try {
            Employee employee = employeeService.findEmployeeById(id);
            EmployeeResponseDTO responseDTO = employeeService.convertToDTO(employee);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } catch (AccessDeniedException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping("/add")
    public ResponseEntity<EmployeeResponseDTO> addEmployee(@RequestBody Employee employee) {
        try {
            Employee newEmployee = employeeService.addEmployee(employee);
            EmployeeResponseDTO responseDTO = employeeService.convertToDTO(newEmployee);
            return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
    @PutMapping("/update")
    public ResponseEntity<EmployeeResponseDTO> updateEmployee(@RequestBody Employee employee) {
        try {
            Employee updatedEmployee = employeeService.updateEmployee(employee);
            EmployeeResponseDTO responseDTO = employeeService.convertToDTO(updatedEmployee);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } catch (AccessDeniedException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable("id") Long id) {
        try {
            employeeService.deleteEmployee(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (AccessDeniedException e) {
            return new ResponseEntity<>("You don't have permission to delete this employee", HttpStatus.FORBIDDEN);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>("Employee not found with ID: " + id, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while deleting the employee", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
   
    @GetMapping("/admin/all")
    public ResponseEntity<List<EmployeeResponseDTO>> getAllEmployees() {
        try {
            List<Employee> employees = employeeService.findAllEmployees();
            List<EmployeeResponseDTO> responseDTOs = employees.stream()
                .map(emp -> employeeService.convertToDTO(emp))
                .collect(Collectors.toList());
            return new ResponseEntity<>(responseDTOs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}