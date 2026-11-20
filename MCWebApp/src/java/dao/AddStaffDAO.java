package dao;

import model.Staff;
import util.DBConnection;

import java.sql.*;

public class AddStaffDAO {

    // ===== Check email exists =====
    public boolean emailExists(String email) throws SQLException {
        String sql = "SELECT 1 FROM STAFF WHERE STAFF_EMAIL = ?";
        try (Connection c = DBConnection.createConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    // ===== Insert staff =====
    public void addStaff(Staff staff) throws SQLException {

        try (Connection conn = DBConnection.createConnection()) {
            conn.setAutoCommit(false);

            try {
                String staffId = generateNextStaffId(conn);

                String sql =
                    "INSERT INTO STAFF (STAFF_ID, STAFF_EMAIL, STAFF_PASS, STAFF_NAME, STAFF_PHONE, USER_ROLE) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, staffId);
                    ps.setString(2, staff.getStaffEmail());
                    ps.setString(3, staff.getStaffPass()); // plain for now (OK for assignment)
                    ps.setString(4, staff.getStaffName());
                    ps.setString(5, staff.getStaffPhone());
                    ps.setString(6, staff.getUserRole());
                    ps.executeUpdate();
                }

                conn.commit();

            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    // ===== Generate STAFF_ID =====
    private String generateNextStaffId(Connection conn) throws SQLException {

        String sql = "SELECT MAX(STAFF_ID) FROM STAFF WHERE STAFF_ID LIKE 'S%'";
        int next = 1;

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                String maxId = rs.getString(1); // e.g. S012
                if (maxId != null) {
                    next = Integer.parseInt(maxId.substring(1)) + 1;
                }
            }
        }
        return "S" + String.format("%03d", next);
    }
}
