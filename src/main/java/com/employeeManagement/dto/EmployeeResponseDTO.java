package com.employeeManagement.dto;

public class EmployeeResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String jobTitle;
    private String phone;
    private String imageUrl;
    private String employeeCode;
    
    // Constructor
    public EmployeeResponseDTO(Long id, String name, String email, String jobTitle, 
                             String phone, String imageUrl, String employeeCode) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.jobTitle = jobTitle;
        this.phone = phone;
        this.imageUrl = imageUrl;
        this.employeeCode = employeeCode;
    }
    
    
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getJobTitle() { return jobTitle; }
    public String getPhone() { return phone; }
    public String getImageUrl() { return imageUrl; }
    public String getEmployeeCode() { return employeeCode; }
}