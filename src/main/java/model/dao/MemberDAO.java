package model.dao;
 
import model.Member;
import util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.ResourceCopy;
 
public class MemberDAO {
    
    private Member mapRow(ResultSet rs) throws SQLException {
        Member m = new Member();
        m.setMemberId(rs.getInt("member_id"));
        m.setFullName(rs.getString("full_name"));
        m.setEmail(rs.getString("email"));
        Timestamp dr = rs.getTimestamp("date_registered");
        if (dr != null) m.setDateRegistered(dr.toLocalDateTime());
        return m;
    }
 
    public boolean create(Member m) {
        String sql = "INSERT INTO members (full_name, email) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql,
                     Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, m.getFullName());
            ps.setString(2, m.getEmail());
            if (ps.executeUpdate() > 0) {
                ResultSet keys = ps.getGeneratedKeys();
                if (keys.next()) m.setMemberId(keys.getInt(1));
                return true;
            }
        } catch (SQLException e) { System.out.println(e.getMessage()); }
        return false;
    }
 
    public List<Member> getAll() throws SQLException {
        List<Member> list = new ArrayList<>();
        String sql = "SELECT * FROM members ORDER BY member_id DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
        }
        return list;
    }
 
    public Member getById(int id) throws SQLException {
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
 
    public List<Member> search(String keyword) throws SQLException {
        List<Member> list = new ArrayList<>();
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
    
    public boolean update(Member m) {
        String sql = "UPDATE members SET full_name = ?, email = ? " +
            "WHERE member_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, m.getFullName());
            ps.setString(2, m.getEmail());
            ps.setInt(3, m.getMemberId());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Update failed: " + e.getMessage());
            return false;
        }
    }
    
    public boolean delete(int memberId) {
        // Will fail if resource_copy rows still exist (FK RESTRICT)
        String sql = "DELETE FROM members WHERE member_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, memberId);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Delete failed: " + e.getMessage());
            return false;
        } 
    }
}
