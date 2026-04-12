package model;

import java.time.LocalDateTime;

public class Staff {
    private int staffID;
    private String fullName;
    private String email;
    private String username;
    private String password;
    private String role;
    private LocalDateTime lastLogin;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Staff() {}

    public Staff(int staffID, String fullName, String email, String username, String password, String role, LocalDateTime lastLogin, LocalDateTime createdAt, LocalDateTime updatedat) {
        this.staffID = staffID;
        this.fullName = fullName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
        this.lastLogin = lastLogin;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    public int getStaffID()               { return staffID; }
    public void setStaffID(int staffID)        { this.staffID = staffID; }
    
    public String getFullName()               { return fullName; }
    public void setFullName(String fullName)        { this.fullName = fullName; }
    
    public String getEmail()               { return email; }
    public void setEmail(String email)        { this.email = email; }
    
    public String getUsername()               { return username; }
    public void setUsername(String username)        { this.username = username; }
    
    public String getPassword()               { return password; }
    public void setPassword(String password)        { this.password = password; }
    
    public String getRole()               { return role; }
    public void setRole(String role)        { this.role = role; }
    
    public LocalDateTime getLastLogin()              { return lastLogin; }
    public void setLastLogin(LocalDateTime lastLogin) { this.lastLogin = lastLogin; }
    
    public LocalDateTime getCreatedAt()              { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt()              { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public void setDateRegistered(LocalDateTime toLocalDateTime) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
