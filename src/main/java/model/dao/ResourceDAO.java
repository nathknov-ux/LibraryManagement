package model.dao;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import model.Resource;
import util.BarcodeUtil;
import util.DBConnection;


public class ResourceDAO {
    
    private Resource mapRow(ResultSet rs) throws SQLException {
        Resource r = new Resource();
        r.setResourceId(rs.getInt("resource_id"));
        r.setTitle(rs.getString("title"));
        r.setResourceType(Resource.ResourceType.fromString(rs.getString("resource_type")));
        r.setAuthor(rs.getString("author"));
        int year = rs.getInt("year_published");
        r.setYearPublished(rs.wasNull() ? null : year);
        r.setIsbnIssn(rs.getString("isbn_issn"));
        r.setDegreeLevel(rs.getString("degree_level"));
        r.setStatus(Resource.ResourceStatus.fromString(rs.getString("status")));
        r.setTotalCopies(rs.getInt("total_copies"));
        r.setAvailableCopies(rs.getInt("available_copies"));
        
        Integer addedby = (Integer) rs.getObject("added_by");
        r.setAddedBy(addedby);
        Timestamp ca = rs.getTimestamp("created_at");
        if (ca != null) r.setCreatedAt(ca.toLocalDateTime());
        Timestamp ua = rs.getTimestamp("updated_at");
        if (ua != null) r.setUpdatedAt(ua.toLocalDateTime());
        return r;
    }

    
    public static void insertCopies(Connection conn, Resource resource, int count) throws SQLException {
        int lastNumber = BarcodeUtil.getLastBarcodeNumber(conn);
 
        String sql = "INSERT INTO resource_copy (barcode, resource_id, status) VALUES (?, ?, 'available')";
 
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (int i = 1; i <= count; i++) {
                String barcode = BarcodeUtil.generateBarcode(lastNumber, i);
                ps.setString(1, barcode);
                ps.setInt(2, resource.getResourceId());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }
    
    public boolean create(Resource r) {
        String qry = "INSERT INTO resource (title, resource_type, author, year_published, isbn_issn, degree_level, status, added_by) VALUES (?,?,?,?,?,?,?,?)";

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(qry, PreparedStatement.RETURN_GENERATED_KEYS);

            stmt.setString(1, r.getTitle());
            stmt.setString(2, r.getResourceType().name().toLowerCase());
            stmt.setString(3, r.getAuthor());
            stmt.setInt(4, r.getYearPublished());
            stmt.setString(5, r.getIsbnIssn());
            stmt.setString(6, r.getDegreeLevel());
            stmt.setString(7, r.getStatus().name().toLowerCase());
            stmt.setInt(8, r.getAddedBy());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                var rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    int generatedId = rs.getInt(1);
                    r.setResourceId(generatedId);
                }
                insertCopies(conn, r, r.getTotalCopies()); // ← use totalCopies here
                return true;
            }

            return false; // ← also fix this, was returning true even if 0 rows affected

        } catch (SQLException e) {
            System.out.println("Insert Failed: " + e.getMessage());
            return false;
        }
    }
    
    public List<Resource> getAll() throws SQLException {
        List<Resource> list = new ArrayList<>();
        String sql = "SELECT * FROM resource ORDER BY resource_id DESC";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) list.add(mapRow(rs));
        }
        return list;
    }
    
    public Resource getById(int id) throws SQLException {
        String sql = "SELECT * FROM resource WHERE resource_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        }
        return null;
    }
    
    public List<Resource> getByType(String type) throws SQLException {
        List<Resource> list = new ArrayList<>();
        String sql = "SELECT * FROM resource WHERE resource_type = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, type);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        }
        return list;
    }
    
    public List<Resource> search(String keyword) throws SQLException {
        List<Resource> list = new ArrayList<>();
        String sql = "SELECT * FROM resource WHERE title LIKE ? OR author LIKE ?";
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
    
    public boolean update(Resource r) {
        String sql = "UPDATE resource SET title=?, resource_type=?, author=?, " +
            "year_published=?, isbn_issn=?, degree_level=?, status=? " +
            "WHERE resource_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, r.getTitle());
            ps.setString(2, r.getResourceType().name().toLowerCase());
            ps.setString(3, r.getAuthor());
            ps.setInt(4, r.getYearPublished());
            ps.setString(5, r.getIsbnIssn());
            ps.setString(6, r.getDegreeLevel());
            ps.setString(7, r.getStatus().name().toLowerCase());
            ps.setInt(8, r.getResourceId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Update failed: " + e.getMessage());
            return false;
        }
    }
    
    public boolean delete(int resourceId) {
        // Will fail if resource_copy rows still exist (FK RESTRICT)
        String sql = "DELETE FROM resource WHERE resource_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, resourceId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Delete failed: " + e.getMessage());
            return false;
        }
    }
}
