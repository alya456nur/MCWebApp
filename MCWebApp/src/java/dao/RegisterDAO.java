package dao;

import util.DBConnection;
import java.sql.*;

public class RegisterDAO {

    // Register customer
    public boolean registerCustomer(String custName, String email, String password, String custAddress) {
        try (Connection c = DBConnection.createConnection()) {

            if (c == null) {
                System.err.println("DB connection is null!");
                return false;
            }

            // Check if email exists
            String checkEmail = "SELECT CUST_ID FROM CUSTOMER WHERE CUST_EMAIL = ?";
            try (PreparedStatement ps = c.prepareStatement(checkEmail)) {
                ps.setString(1, email);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    System.out.println("Email already exists: " + email);
                    return false; // Email already exists
                }
            }

            // Generate next CUST_ID like C001
            String custId = generateNextId(c, "CUSTOMER", "CUST_ID", "C", 3);

            // Insert new customer with default phone '-' and role 'Customer'
            String sql = "INSERT INTO CUSTOMER "
                       + "(CUST_ID, CUST_NAME, CUST_EMAIL, CUST_PASS, CUST_ADD, CUST_PHONE, USER_ROLE) "
                       + "VALUES (?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setString(1, custId);
                ps.setString(2, custName);
                ps.setString(3, email);
                ps.setString(4, password);
                ps.setString(5, custAddress);
                ps.setString(6, "-");           // Default phone
                ps.setString(7, "Customer");    // Default role
                int rows = ps.executeUpdate();
                System.out.println("Inserted rows: " + rows);
            }

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Generate next ID like C001
    private String generateNextId(Connection conn, String table, String col,
                                  String prefix, int digits) throws SQLException {
        String sql = "SELECT MAX(" + col + ") FROM " + table + " WHERE " + col + " LIKE ?";
        int next = 1;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, prefix + "%");
            ResultSet rs = ps.executeQuery();
            if (rs.next() && rs.getString(1) != null) {
                next = Integer.parseInt(rs.getString(1).substring(prefix.length())) + 1;
            }
        }
        return prefix + String.format("%0" + digits + "d", next);
    }
}
