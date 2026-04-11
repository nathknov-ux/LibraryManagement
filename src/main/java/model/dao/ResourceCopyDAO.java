package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import model.Resource;
import model.ResourceCopy;
import util.DBConnection;

public class ResourceCopyDAO {
    
    private ResourceCopy mapRow(ResultSet rs) throws SQLException {
        ResourceCopy rc = new ResourceCopy();
        rc.setBarcode(rs.getString("barcode"));
        rc.setResourceId(rs.getInt("resource_id"));
        rc.setStatus(ResourceCopy.ResourceStatus.fromString(rs.getString("status")));
        Timestamp ca = rs.getTimestamp("acquired_at");
        if (ca != null) rc.setAcquiredAt(ca.toLocalDateTime());
        Timestamp ua = rs.getTimestamp("updated_at");
        if (ua != null) rc.setUpdatedAt(ua.toLocalDateTime());
        return rc;
        }
    
    public boolean create(ResourceCopy rc, Resource r, int totalCopies) {
        String qry = "INSERT INTO resourceCopy (barcode, resourceID, resourceStatus) VALUES (?,?,?)";

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(qry, PreparedStatement.RETURN_GENERATED_KEYS);

            stmt.setString(1, rc.getBarcode());
            stmt.setString(7, rc.getStatus().name().toLowerCase());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                var rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    int generatedId = rs.getInt(1);
                    rc.setResourceId(r.getResourceId());
                }
                ResourceDAO.insertCopies(conn, r, totalCopies); // ← use totalCopies here
                return true;
            }

            return false; // ← also fix this, was returning true even if 0 rows affected

        } catch (SQLException e) {
            System.out.println("Insert Failed: " + e.getMessage());
            return false;
        }
    }
    
     public List<ResourceCopy> getAll() throws SQLException {
        List<ResourceCopy> list = new ArrayList<>();
        String sql = "SELECT * FROM resource_copy ORDER BY resource_id ASC";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) list.add(mapRow(rs));
        }
        return list;
    }
     
     public ResourceCopy getById(int id) throws SQLException {
        String sql = "SELECT * FROM resource_copy WHERE barcode = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        }
        return null;
    }
     
     public List<ResourceCopy> search(String keyword) throws SQLException {
        List<ResourceCopy> list = new ArrayList<>();
        String sql = "SELECT * FROM resource WHERE resource_id LIKE ? OR status LIKE ?";
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
     
     public boolean update(ResourceCopy rc) {
        String sql = "UPDATE resource_copy SET status=? " +
            "WHERE barcode=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, rc.getStatus().name().toLowerCase());
            ps.setString(2, rc.getBarcode());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Update failed: " + e.getMessage());
            return false;
        }
    }
    
    public boolean delete(String barcode) {
        // Will fail if resource_copy rows still exist (FK RESTRICT)
        String sql = "DELETE FROM resource_copy WHERE barcode = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, barcode);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Delete failed: " + e.getMessage());
            return false;
        }
    }
}
