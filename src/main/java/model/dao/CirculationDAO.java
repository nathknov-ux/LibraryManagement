/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.dao;

/**
 *
 * @author Lee
 */
import model.Circulation;
import util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CirculationDAO {
    
    public boolean borrow(Circulation c) {
        String sql = "INSERT INTO circulation " +
            "(barcode, member_id, due_date, processed_by) VALUES (?,?,?,?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql,
                     Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, c.getBarcode());
            ps.setInt(2, c.getMemberId());
            ps.setTimestamp(3, Timestamp.valueOf(c.getDueDate()));
            ps.setInt(4, c.getProcessedBy());
            if (ps.executeUpdate() > 0) {
                ResultSet keys = ps.getGeneratedKeys();
                if (keys.next()) c.setCirculationId(keys.getInt(1));
                return true;
                // Trigger trg_circulation_after_insert fires automatically
                // and sets resource_copy.status = 'borrowed'
            }
        } catch (SQLException e) { System.out.println(e.getMessage()); }
        return false;
    }

    public boolean returnCopy(int circulationId) {
        String sql = "UPDATE circulation SET returned = NOW(), status = 'returned' " +
                     "WHERE circulation_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, circulationId);
            return ps.executeUpdate() > 0;
            // Trigger trg_circulation_after_return fires automatically
            // and sets resource_copy.status = 'available'
        } catch (SQLException e) { System.out.println(e.getMessage()); }
        return false;
    }

    public List<Circulation> getActiveBorrows() throws SQLException {
        List<Circulation> list = new ArrayList<>();
        String sql = "SELECT * FROM circulation WHERE returned IS NULL ORDER BY due_date";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
        }
        return list;
    }
    
    public List<Circulation> getOverdue() throws SQLException {
        List<Circulation> list = new ArrayList<>();
        String sql = "SELECT * FROM circulation " +
                     "WHERE returned IS NULL AND due_date < NOW()";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
        }
        return list;
    }
    
        private Circulation mapRow(ResultSet rs) throws SQLException {
        Circulation c = new Circulation();
        c.setCirculationId(rs.getInt("circulation_id"));
        c.setBarcode(rs.getString("barcode"));
        c.setMemberId(rs.getInt("member_id"));
        Timestamp ba = rs.getTimestamp("borrowed_at");
        if (ba != null) c.setBorrowedAt(ba.toLocalDateTime());
        Timestamp dd = rs.getTimestamp("due_date");
        if (dd != null) c.setDueDate(dd.toLocalDateTime());
        Timestamp ret = rs.getTimestamp("returned");
        if (ret != null) c.setReturned(ret.toLocalDateTime());
        String st = rs.getString("status");
        if (st != null) c.setStatus(Circulation.CirculationStatus.valueOf(st.toUpperCase()));
        c.setFineAmount(rs.getBigDecimal("fine_amount"));
        String reason = rs.getString("reason");
        if (reason != null) c.setReason(Circulation.FineReason.valueOf(reason.toUpperCase()));
        c.setPaid(rs.getBoolean("paid"));
        c.setProcessedBy(rs.getInt("processed_by"));
        return c;
    }
}



