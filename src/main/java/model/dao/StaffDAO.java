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
import model.Staff;
import util.DBConnection;

/**
 *
 * @author BENNY
 */
public class StaffDAO {
    
    private Staff mapRow(ResultSet rs) throws SQLException {
        Staff m = new Staff();
        m.setStaffID(rs.getInt("member_id"));
        m.setFullName(rs.getString("full_name"));
        m.setEmail(rs.getString("email"));
        Timestamp dr = rs.getTimestamp("date_registered");
        if (dr != null) m.setDateRegistered(dr.toLocalDateTime());
        return m;
    }

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
    
    public List<Staff> getAll() throws SQLException {
        List<Staff> list = new ArrayList<>();
        String sql = "SELECT * FROM staff ORDER BY full_name";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
        }
        return list;
    }
    
    public Staff getById(int id) throws SQLException {
        String sql = "SELECT * FROM members WHERE member_id = ?";
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
        String sql = "SELECT * FROM members WHERE full_name LIKE ? OR email LIKE ?";
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

    
}
