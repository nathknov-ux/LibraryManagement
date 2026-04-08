package util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BarcodeUtil {
    public static int getLastBarcodeNumber(Connection conn) throws SQLException {
        String sql = "SELECT MAX(CAST(SUBSTRING(barcode, 4) AS UNSIGNED)) FROM resource_copy";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1); // returns 0 if NULL (empty table)
            }
        }
        return 0;
    }

    public static String generateBarcode(int lastNumber, int increment) {
        return String.format("BR-%04d", lastNumber + increment);
    }
}
