package dao;

import model.Staff;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StaffDAO {

    // Fetch all staff from the STAFF table (NO PASSWORD)
    public List<Staff> getAllStaff() {
        List<Staff> staffList = new ArrayList<>();

        String sql
                = "SELECT STAFF_ID, STAFF_EMAIL, STAFF_NAME, STAFF_PHONE, USER_ROLE "
                + "FROM STAFF "
                + "ORDER BY STAFF_ID";

        try (Connection c = DBConnection.createConnection();
                PreparedStatement ps = c.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Staff s = new Staff();
                s.setStaffId(rs.getString("STAFF_ID"));
                s.setStaffEmail(rs.getString("STAFF_EMAIL"));
                s.setStaffName(rs.getString("STAFF_NAME"));
                s.setStaffPhone(rs.getString("STAFF_PHONE"));
                s.setUserRole(rs.getString("USER_ROLE")); // admin / staff
                staffList.add(s);
            }

        } catch (SQLException e) {
            System.err.println("ERROR fetching staff data");
            e.printStackTrace();
        }

        return staffList;
    }
}
