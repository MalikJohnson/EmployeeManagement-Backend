package com.employeeManagement.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.employeeManagement.dto.EmployeeResponseDTO;
import com.employeeManagement.exception.UserNotFoundException;
import com.employeeManagement.model.Employee;
import com.employeeManagement.model.User;
import com.employeeManagement.repo.EmployeeRepo;

import jakarta.transaction.Transactional;

@Service
public class EmployeeService {
    private final EmployeeRepo employeeRepo;
    
    @Autowired
    public EmployeeService(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }
    
    public Employee addEmployee(Employee employee) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        
        employee.setEmployeeCode(UUID.randomUUID().toString());
        employee.setUser(currentUser);
        return employeeRepo.save(employee);
    }
    
    public List<Employee> findAllEmployeesForCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        return employeeRepo.findByUser(currentUser);
    }
    
    public List<Employee> findAllEmployees() {
        return employeeRepo.findAll();
    }
    
    public Employee updateEmployee(Employee employee) {
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        
        employee.setUser(currentUser);
        Employee existingEmployee = employeeRepo.findById(employee.getId())
            .orElseThrow(() -> new UserNotFoundException("Employee not found"));
            
        if (!existingEmployee.getUser().getId().equals(currentUser.getId())) {
            throw new SecurityException("You can only update your own employees");
        }
        
        return employeeRepo.save(employee);
    }
    
    @Transactional
    public void deleteEmployee(Long id) {
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        
        Employee employee = employeeRepo.findById(id)
            .orElseThrow(() -> new UserNotFoundException("Employee not found"));
            
        if (!employee.getUser().getId().equals(currentUser.getId())) {
            throw new SecurityException("You can only delete your own employees");
        }
        
        employeeRepo.deleteEmployeeById(id);
    }
    
    public Employee findEmployeeById(Long id) {
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        
        Employee employee = employeeRepo.findEmployeeById(id)
            .orElseThrow(() -> new UserNotFoundException("Employee with id " + id + " does not exist"));
            
        if (!employee.getUser().getId().equals(currentUser.getId())) {
            throw new SecurityException("You can only access your own employees");
        }
        
        return employee;
    }
    
    public List<Employee> findEmployeesByUser(User user) {
        return employeeRepo.findByUser(user);
    }
    
    public EmployeeResponseDTO convertToDTO(Employee employee) {
        return new EmployeeResponseDTO(
            employee.getId(),
            employee.getName(),
            employee.getEmail(),
            employee.getJobTitle(),
            employee.getPhone(),
            employee.getImageUrl(),
            employee.getEmployeeCode()
        );
    }
}