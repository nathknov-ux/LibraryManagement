package controller;

import model.Staff;
import model.dao.StaffDAO;

public class StaffController {
    private StaffDAO staffDAO = new StaffDAO();
    
    public boolean addStaff(String fullName, String email, String username, String password, String role) {
       if (fullName == null || fullName.trim().isEmpty()) {
            return false;
        }

        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        if (username == null || username.trim().isEmpty()) {
            return false;
        }

        if (password == null || password.trim().isEmpty()) {
            System.out.println("Resource type is required.");
            return false;
        }
        if (role == null || role.trim().isEmpty()) {
            System.out.println("Title is required.");
            return false;
        }

        Staff s = new Staff();
        s.setFullName(fullName.trim());
        s.setEmail(email);               
        s.setUsername(username);
        s.setPassword(password);           
        s.setRole(role);    

        boolean success = staffDAO.Create(s);

        if (success) {
            System.out.println("Resource added successfully.");
        } else {
            System.out.println("Failed to add resource.");
        }

        return success;
    }
}
