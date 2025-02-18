package com.employeeManagement.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.employeeManagement.model.Employee;

public interface EmployeeRepo extends JpaRepository<Employee, Long> {

}
