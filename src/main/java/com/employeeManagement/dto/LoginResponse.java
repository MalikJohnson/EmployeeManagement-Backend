package com.employeeManagement.dto;

public class LoginResponse {
    private String token;
    private String username;
    private String role;
    private Long userId; 

    
    public LoginResponse(String token, String username, String role, Long userId) {
        this.token = token;
        this.username = username;
        this.role = role;
        this.userId = userId;
    }

    
    public Long getUserId() {
        return userId;
    }

    
    public String getToken() {
        return token;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }
}