package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Staff;
import model.Customer;

public class UserDAO {

    private Connection conn;

    public UserDAO(Connection conn) {
        this.conn = conn;
    }

    // ================= STAFF =================
    public Staff getStaffByEmailAndPassword(String email, String password) throws SQLException {

        String sql
                = "SELECT STAFF_ID, STAFF_EMAIL, STAFF_NAME, STAFF_PHONE, USER_ROLE "
                + "FROM STAFF "
                + "WHERE STAFF_EMAIL = ? AND STAFF_PASS = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Staff staff = new Staff();
                    staff.setStaffId(rs.getString("STAFF_ID"));
                    staff.setStaffEmail(rs.getString("STAFF_EMAIL"));
                    staff.setStaffName(rs.getString("STAFF_NAME"));
                    staff.setStaffPhone(rs.getString("STAFF_PHONE"));
                    staff.setUserRole(rs.getString("USER_ROLE")); // ✅ THIS FIXES EVERYTHING
                    return staff;
                }
            }
        }
        return null;
    }

    // ================= CUSTOMER =================
    public Customer getCustomerByEmailAndPassword(String email, String password) throws SQLException {

        String sql
                = "SELECT CUST_ID, CUST_EMAIL "
                + "FROM CUSTOMER "
                + "WHERE CUST_EMAIL = ? AND CUST_PASS = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Customer cust = new Customer();
                    cust.setCustId(rs.getString("CUST_ID"));
                    cust.setCustEmail(rs.getString("CUST_EMAIL"));
                    return cust;
                }
            }
        }
        return null;
    }
}
