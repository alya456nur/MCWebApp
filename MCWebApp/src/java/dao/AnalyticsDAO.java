// dao/AnalyticsDAO.java
package dao;

import util.DBConnection;
import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class AnalyticsDAO {

    // Returns revenue for each month of the current year
    public Map<String, Double> getMonthlyRevenue() throws SQLException {
        Map<String, Double> revenue = new LinkedHashMap<>();

        // Initialize months with 0
        String[] months = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
        for (String m : months) revenue.put(m, 0.0);

        String sql = "SELECT MONTH(RECEIPT_DATE) AS MONTH, SUM(GRAND_TOTAL) AS TOTAL " +
                     "FROM RECEIPT " +
                     "GROUP BY MONTH(RECEIPT_DATE)";

        try (Connection c = DBConnection.createConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int monthNum = rs.getInt("month"); // 1 = Jan
                double total = rs.getDouble("total");
                revenue.put(months[monthNum - 1], total);
            }
        }
        return revenue;
    }
    
    // New method: filter revenue by start/end date (strings)
    public Map<String, Double> getMonthlyRevenue(String startDate, String endDate) throws SQLException {
        Map<String, Double> revenue = new LinkedHashMap<>();
        String[] months = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
        for (String m : months) revenue.put(m, 0.0);

        String sql = "SELECT MONTH(RECEIPT_DATE) AS MONTH, SUM(GRAND_TOTAL) AS TOTAL " +
                     "FROM RECEIPT " +
                     "WHERE RECEIPT_DATE BETWEEN ? AND ? " +
                     "GROUP BY MONTH(RECEIPT_DATE)";

        try (Connection c = DBConnection.createConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            // convert string dates to java.sql.Date inside DAO
            ps.setDate(1, Date.valueOf(startDate));
            ps.setDate(2, Date.valueOf(endDate));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int monthNum = rs.getInt("month");
                    double total = rs.getDouble("total");
                    revenue.put(months[monthNum - 1], total);
                }
            }
        }
        return revenue;
    }
}
