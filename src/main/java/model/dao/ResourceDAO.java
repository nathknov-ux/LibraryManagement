package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import model.Resource;
import util.BarcodeUtil;
import util.DBConnection;


public class ResourceDAO {
    
    private void insertCopies(Connection conn, Resource resource, int count) throws SQLException {
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
    
    public boolean Create(Resource r) {
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
}
