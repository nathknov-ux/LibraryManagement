package model.dao;

import util.DBConnection;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class DashboardDAO {

    /**
     * Returns a map of resource_type -> count of currently borrowed copies.
     */
    public Map<String, Integer> getBorrowedCountByType() {
        Map<String, Integer> map = new HashMap<>();
        String sql = "SELECT r.resource_type, COUNT(*) AS cnt " +
                     "FROM circulation c " +
                     "JOIN resource_copy rc ON c.barcode = rc.barcode " +
                     "JOIN resource r ON rc.resource_id = r.resource_id " +
                     "WHERE c.returned IS NULL " +
                     "GROUP BY r.resource_type";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                map.put(rs.getString("resource_type"), rs.getInt("cnt"));
            }
        } catch (SQLException e) {
            System.out.println("getBorrowedCountByType: " + e.getMessage());
        }
        return map;
    }

    /**
     * Returns a map of resource_type -> total available_copies.
     */
    public Map<String, Integer> getAvailableCountByType() {
        Map<String, Integer> map = new HashMap<>();
        String sql = "SELECT resource_type, SUM(available_copies) AS cnt " +
                     "FROM resource GROUP BY resource_type";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                map.put(rs.getString("resource_type"), rs.getInt("cnt"));
            }
        } catch (SQLException e) {
            System.out.println("getAvailableCountByType: " + e.getMessage());
        }
        return map;
    }

    /**
     * Total number of currently borrowed items (all types).
     */
    public int getTotalBorrowed() {
        String sql = "SELECT COUNT(*) FROM circulation WHERE returned IS NULL";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            System.out.println("getTotalBorrowed: " + e.getMessage());
        }
        return 0;
    }

    /**
     * Total number of available copies (all types).
     */
    public int getTotalAvailable() {
        String sql = "SELECT SUM(available_copies) FROM resource";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            System.out.println("getTotalAvailable: " + e.getMessage());
        }
        return 0;
    }
}
