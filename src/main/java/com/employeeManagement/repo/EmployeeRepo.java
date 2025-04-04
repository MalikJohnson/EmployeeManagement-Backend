package com.employeeManagement.repo;

import com.employeeManagement.model.Employee;
import com.employeeManagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepo extends JpaRepository<Employee, Long> {
    List<Employee> findByUser(User user);
    
    Optional<Employee> findEmployeeById(Long id);
    
    @Modifying
    @Query("DELETE FROM Employee e WHERE e.id = :id")
    void deleteEmployeeById(@Param("id") Long id);
    
    @Query("SELECT e FROM Employee e WHERE e.user.id = :userId AND e.id = :employeeId")
    Optional<Employee> findByIdAndUserId(@Param("employeeId") Long employeeId, @Param("userId") Long userId);
}