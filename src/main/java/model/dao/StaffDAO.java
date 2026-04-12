/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import model.Member;
import model.Staff;
import util.DBConnection;

/**
 *
 * @author BENNY
 */
public class StaffDAO {
    
    private Staff mapRow(ResultSet rs) throws SQLException {
        Staff s = new Staff();
        s.setStaffID(rs.getInt("staff_id"));
        s.setFullName(rs.getString("full_name"));
        s.setEmail(rs.getString("email"));
        s.setUsername(rs.getString("username"));   
        s.setPassword(rs.getString("password"));   
        s.setRole(rs.getString("role"));
        Timestamp ll = rs.getTimestamp("last_login"); 
        if (ll != null) s.setLastLogin(ll.toLocalDateTime());
        Timestamp ca = rs.getTimestamp("created_at");
        if (ca != null) s.setCreatedAt(ca.toLocalDateTime());
        Timestamp ua = rs.getTimestamp("updated_at");
        if (ua != null) s.setUpdatedAt(ua.toLocalDateTime());
        return s;
    }

    public boolean create(Staff s) {
        String qry = "INSERT INTO staff (full_name, email, username, password, role) VALUES (?,?,?,?,?)";
       
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
    
    public List<Staff> getAll() throws SQLException {
        List<Staff> list = new ArrayList<>();
        String sql = "SELECT * FROM staff ORDER BY staff_id DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
        }
        return list;
    }
    
    public Staff getById(int id) throws SQLException {
        String sql = "SELECT * FROM staff WHERE staff_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        }
        return null;
    }

    public List<Staff> search(String keyword) throws SQLException {
        List<Staff> list = new ArrayList<>();
        String sql = "SELECT * FROM staff WHERE full_name LIKE ? OR email LIKE ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        }
        return list;
    }
    
    public boolean update(Staff m) {
        String sql = "UPDATE staff SET full_name = ?, email = ?, username = ? " +
            "WHERE staff_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, m.getFullName());
            ps.setString(2, m.getEmail());
            ps.setString(3, m.getUsername());  // ← was missing
            ps.setInt(4, m.getStaffID());      // ← was missing
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Update failed: " + e.getMessage());
            return false;
        }
    }
    
    public boolean updatePassword(Staff m) {
        String sql = "UPDATE staff SET password = ? " +
            "WHERE staff_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, m.getPassword());
            ps.setInt(2, m.getStaffID());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Update failed: " + e.getMessage());
            return false;
        }
    }
    
    public boolean delete(int id) {
        
        String sql = "DELETE FROM staff WHERE staff_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Delete failed: " + e.getMessage());
            return false;
        }
    }
    
    public Staff login(String username, String password) throws SQLException {
        String sql = "SELECT * FROM staff WHERE username = ? AND password = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Staff s = mapRow(rs);                    
                    return s;
                }
            }
        }
        return null;
    }
}
