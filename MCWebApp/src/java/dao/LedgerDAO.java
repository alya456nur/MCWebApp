package dao;

import view.LedgerView;

import util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LedgerDAO {
    
    // Function to fetch the stock movement
    public List<LedgerView> getAllStockEntries() {
        List<LedgerView> list = new ArrayList<>();

        String sql = "SELECT s.STOCK_DATE, s.ORDER_ID, p.PROD_CODE, p.PROD_NAME, v.VAR_NAME, " +
                     "s.STOCK_TYPE, s.STOCK_QTY, st.STAFF_NAME, s.REMARK " +
                     "FROM STOCK s " +
                     "LEFT JOIN ORDERS o ON s.ORDER_ID = o.ORDER_ID " + // keep all stock rows
                     "JOIN STAFF st ON s.STAFF_ID = st.STAFF_ID " +
                     "JOIN VARIATION v ON s.VAR_ID = v.VAR_ID " +
                     "JOIN PRODUCT p ON v.PROD_ID = p.PROD_ID " +
                     "ORDER BY s.STOCK_ID DESC";

        try (Connection c = DBConnection.createConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                LedgerView entry = new LedgerView();

                java.sql.Date sqlDate = rs.getDate("STOCK_DATE");
                entry.setStockDate(sqlDate != null ? sqlDate.toLocalDate() : null);

                entry.setOrderId(rs.getString("ORDER_ID"));
                entry.setStaffName(rs.getString("STAFF_NAME"));
                entry.setProdCode(rs.getString("PROD_CODE"));
                entry.setProdName(rs.getString("PROD_NAME"));
                entry.setVarName(rs.getString("VAR_NAME"));
                entry.setStockType(rs.getString("STOCK_TYPE"));
                entry.setStockQty(rs.getInt("STOCK_QTY"));
                entry.setRemark(rs.getString("REMARK"));

                list.add(entry);
            }

        } catch (SQLException e) {
            System.err.println("ERROR fetching stock entries");
            e.printStackTrace();
        }

        return list;
    }

    
    // Filter the records by date picked
    public List<LedgerView> getStockByDate(String startDate, String endDate) {

        List<LedgerView> list = new ArrayList<>();

        String sql =
            "SELECT s.STOCK_DATE, p.PROD_CODE, p.PROD_NAME, v.VAR_NAME, " +
            "s.STOCK_TYPE, s.STOCK_QTY, v.QTY_BEFORE, v.VAR_QTY, " +
            "st.STAFF_NAME, s.REMARK " +
            "FROM STOCK s " +
            "JOIN STAFF st ON s.STAFF_ID = st.STAFF_ID " +
            "JOIN VARIATION v ON s.VAR_ID = v.VAR_ID " +
            "JOIN PRODUCT p ON v.PROD_ID = p.PROD_ID " +
            "WHERE s.STOCK_DATE BETWEEN ? AND ? " +
            "ORDER BY s.STOCK_ID";

        try (Connection c = DBConnection.createConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(startDate));
            ps.setDate(2, Date.valueOf(endDate));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    LedgerView entry = new LedgerView();

                    Date sqlDate = rs.getDate("STOCK_DATE");
                    entry.setStockDate(
                        sqlDate != null ? sqlDate.toLocalDate() : null
                    );

                    entry.setStaffName(rs.getString("STAFF_NAME"));
                    entry.setProdCode(rs.getString("PROD_CODE"));
                    entry.setProdName(rs.getString("PROD_NAME"));
                    entry.setVarName(rs.getString("VAR_NAME"));
                    entry.setStockType(rs.getString("STOCK_TYPE"));
                    entry.setStockQty(rs.getInt("STOCK_QTY"));
                    entry.setQtyBefore(rs.getInt("QTY_BEFORE"));
                    entry.setVarQty(rs.getInt("VAR_QTY"));
                    entry.setRemark(rs.getString("REMARK"));

                    list.add(entry);
                }
            }

        } catch (SQLException e) {
            System.err.println("ERROR fetching filtered stock entries");
            e.printStackTrace();
        }

        return list;
    }
    
}
