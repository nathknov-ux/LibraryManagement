/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import model.Staff;
import util.DBConnection;

/**
 *
 * @author BENNY
 */
public class StaffDAO {
    public boolean Create(Staff s) {
        String qry = "INSERT INTO resource (full_name, email, username, password, role) VALUES (?,?,?,?,?)";
       
       try {
           Connection conn = DBConnection.getConnection();
           PreparedStatement stmt = conn.prepareStatement(qry, PreparedStatement.RETURN_GENERATED_KEYS);
           
           stmt.setString(1, s.getFullName());
           stmt.setString(2, s.getEmail());
           stmt.setString(3, s.getUsername());
           stmt.setString(4, s.getPassword());
           stmt.setString(5, s.getRole());
           
           int rowsAffected = stmt.executeUpdate();
           return rowsAffected > 0;
           
       } catch (SQLException e) {
           System.out.println("Insert Failed: " + e.getMessage());
           return false;
       }
    }    
}
