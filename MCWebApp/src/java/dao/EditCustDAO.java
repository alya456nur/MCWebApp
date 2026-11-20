package dao;

import model.Customer;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EditCustDAO {

    public Customer getCustomerById(String custId) throws SQLException {
        String sql = "SELECT CUST_ID, CUST_NAME, CUST_EMAIL, CUST_ADD, CUST_PHONE, USER_ROLE "
                + "FROM CUSTOMER WHERE CUST_ID = ?";

        try (Connection c = DBConnection.createConnection();
                PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, custId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Customer cust = new Customer();
                    cust.setCustId(rs.getString("CUST_ID"));
                    cust.setCustName(rs.getString("CUST_NAME"));
                    cust.setCustEmail(rs.getString("CUST_EMAIL"));
                    cust.setCustAdd(rs.getString("CUST_ADD"));
                    cust.setCustPhone(rs.getString("CUST_PHONE"));
                    cust.setUserRole(rs.getString("USER_ROLE"));
                    return cust;
                }
            }
        }
        return null;
    }

    public boolean updateCustomerProfile(String custId, String name, String phone, String address) throws SQLException {
        String sql = "UPDATE CUSTOMER SET CUST_NAME = ?, CUST_PHONE = ?, CUST_ADD = ? WHERE CUST_ID = ?";

        try (Connection c = DBConnection.createConnection();
                PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, phone);
            ps.setString(3, address);
            ps.setString(4, custId);

            return ps.executeUpdate() > 0;
        }
    }
}
