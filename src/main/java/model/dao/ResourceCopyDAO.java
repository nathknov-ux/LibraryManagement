package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import model.Resource;
import model.ResourceCopy;
import util.DBConnection;

public class ResourceCopyDAO {
    private ResourceCopy mapRow(ResultSet rs) throws SQLException {
        ResourceCopy rc = new ResourceCopy();
        rc.setBarcode(rs.getString("barcode"));
        rc.setResourceId(rs.getInt("resource_id"));
        rc.setResourceStatus(ResourceCopy.ResourceStatus.fromString(rs.getString("status")));
        Timestamp ca = rs.getTimestamp("acquired_at");
        if (ca != null) rc.setAcquiredAt(ca.toLocalDateTime());
        Timestamp ua = rs.getTimestamp("updated_at");
        if (ua != null) rc.setUpdatedAt(ua.toLocalDateTime());
        return rc;
        }
    
    public boolean Create(ResourceCopy rc) {
        String qry = "INSERT INTO resourceCopy (barcode, resourceID, resourceStatus) VALUES (?,?,?)";

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(qry, PreparedStatement.RETURN_GENERATED_KEYS);

            stmt.setString(1, r.getTitle());
            stmt.setString(2, r.getResourceType().name().toLowerCase());
            stmt.setString(3, r.getAuthor());
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
