package dao;

import model.Staff;
import util.DBConnection;

import java.sql.*;

public class EditStaffDAO {

    public Staff getStaffById(String staffId) throws SQLException {
        String sql
                = "SELECT STAFF_ID, STAFF_EMAIL, STAFF_NAME, STAFF_PHONE, USER_ROLE "
                + "FROM STAFF WHERE STAFF_ID = ?";

        try (Connection conn = DBConnection.createConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, staffId);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }

                Staff s = new Staff();
                s.setStaffId(rs.getString("STAFF_ID"));
                s.setStaffEmail(rs.getString("STAFF_EMAIL"));
                s.setStaffName(rs.getString("STAFF_NAME"));
                s.setStaffPhone(rs.getString("STAFF_PHONE"));
                s.setUserRole(rs.getString("USER_ROLE"));
                return s;
            }
        }
    }

    // Email must be unique (but allow same staff keep same email)
    public boolean emailExistsForOtherStaff(String email, String staffId) throws SQLException {
        String sql
                = "SELECT 1 FROM STAFF WHERE STAFF_EMAIL = ? AND STAFF_ID <> ?";

        try (Connection conn = DBConnection.createConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, staffId);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    // Update without password
    public void updateStaffNoPassword(Staff staff) throws SQLException {
        String sql
                = "UPDATE STAFF "
                + "SET STAFF_EMAIL = ?, STAFF_NAME = ?, STAFF_PHONE = ?, USER_ROLE = ? "
                + "WHERE STAFF_ID = ?";

        try (Connection conn = DBConnection.createConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, staff.getStaffEmail());
            ps.setString(2, staff.getStaffName());
            ps.setString(3, staff.getStaffPhone());
            ps.setString(4, staff.getUserRole());
            ps.setString(5, staff.getStaffId());

            ps.executeUpdate();
        }
    }

    // Update with password
    public void updateStaffWithPassword(Staff staff) throws SQLException {
        String sql
                = "UPDATE STAFF "
                + "SET STAFF_EMAIL = ?, STAFF_PASS = ?, STAFF_NAME = ?, STAFF_PHONE = ?, USER_ROLE = ? "
                + "WHERE STAFF_ID = ?";

        try (Connection conn = DBConnection.createConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, staff.getStaffEmail());
            ps.setString(2, staff.getStaffPass());
            ps.setString(3, staff.getStaffName());
            ps.setString(4, staff.getStaffPhone());
            ps.setString(5, staff.getUserRole());
            ps.setString(6, staff.getStaffId());

            ps.executeUpdate();
        }
    }
}
