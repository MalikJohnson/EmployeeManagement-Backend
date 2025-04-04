package com.employeeManagement.service;

import com.employeeManagement.model.User;
import com.employeeManagement.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    public User findByUsername(String username) {
        return userRepo.findByUsername(username).orElse(null);
    }

 
}