package dao;

import view.InventoryView;

import java.math.BigDecimal;
import util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryDAO {
    
    // Helper function to get a single Int
    private int getSingleInt(String sql, String label) {
        try (Connection c = DBConnection.createConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) return rs.getInt(1); // Unsafe and will explode

        } catch (SQLException e) {
            System.err.println("ERROR fetching " + label + " from database.");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unexpected error fetching " + label);
            e.printStackTrace();
        }
        return 0;
    }

    // Helper function to get a single big decimal
    public BigDecimal getSingleBigDecimal(String sql, String label) {
        try (
            Connection c = DBConnection.createConnection();
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            if (rs.next()) {
                return rs.getBigDecimal(1);
            }
        } catch (SQLException e) {
            System.err.println("ERROR fetching " + label + " from database.");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unexpected error fetching " + label);
            e.printStackTrace();
        }
        return BigDecimal.ZERO;
    }

    // Fetch and display the summary
    public int getProductCount() {
        return getSingleInt("SELECT SUM(VAR_QTY) FROM VARIATION", "Product Count");
    }
    
    public int getStockInCount() {
        return getSingleInt("SELECT COUNT(*) FROM STOCK WHERE STOCK_TYPE = 'In'", "Stock In Count");
    }
    
    public int getStockOutCount() {
        return getSingleInt("SELECT COUNT(*) FROM STOCK WHERE STOCK_TYPE = 'Out'", "Stock Out Count");
    }
    
    public int getLowStockCount() {
        return getSingleInt("SELECT COUNT(*) FROM VARIATION WHERE VAR_QTY < 10 ", "Low Stock Count");
    }

    public BigDecimal getInventoryValue() {
        return getSingleBigDecimal(
            "SELECT COALESCE(SUM(VAR_PRICE * VAR_QTY),0) " +
            "FROM VARIATION",
            "Inventory Value"
        );
    }
    
    // Filtered
    // Total stocks of all variations filtered by variation creation date
    public int getProductCount(String startDate, String endDate) {
        int total = 0;
        String sql = "SELECT SUM(VAR_QTY) FROM VARIATION WHERE CREATE_DATE BETWEEN ? AND ?";

        try (Connection c = DBConnection.createConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(startDate));
            ps.setDate(2, Date.valueOf(endDate));

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) total = rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return total;
    }

    // Total stock "In" filtered by STOCK_DATE
    public int getStockInCount(String startDate, String endDate) {
        int total = 0;
        String sql = "SELECT COUNT(*) FROM STOCK WHERE STOCK_TYPE = 'In' AND STOCK_DATE BETWEEN ? AND ?";

        try (Connection c = DBConnection.createConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(startDate));
            ps.setDate(2, Date.valueOf(endDate));

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) total = rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return total;
    }

    // Total stock "Out" filtered by STOCK_DATE
    public int getStockOutCount(String startDate, String endDate) {
        int total = 0;
        String sql = "SELECT COUNT(*) FROM STOCK WHERE STOCK_TYPE = 'Out' AND STOCK_DATE BETWEEN ? AND ?";

        try (Connection c = DBConnection.createConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(startDate));
            ps.setDate(2, Date.valueOf(endDate));

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) total = rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return total;
    }

    // Low stock count filtered by variation creation date
    public int getLowStockCount(String startDate, String endDate) {
        int total = 0;
        String sql = "SELECT COUNT(*) FROM VARIATION WHERE VAR_QTY < 10 AND LAST_UPDATE BETWEEN ? AND ?";

        try (Connection c = DBConnection.createConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(startDate));
            ps.setDate(2, Date.valueOf(endDate));

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) total = rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return total;
    }

    // Inventory value filtered by variation creation date
    public BigDecimal getInventoryValue(String startDate, String endDate) {
        BigDecimal total = BigDecimal.ZERO;
        String sql = "SELECT COALESCE(SUM(VAR_PRICE * VAR_QTY),0) FROM VARIATION WHERE CREATE_DATE BETWEEN ? AND ?";

        try (Connection c = DBConnection.createConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(startDate));
            ps.setDate(2, Date.valueOf(endDate));

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) total = rs.getBigDecimal(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return total;
    }

    // Function to fetch the inventory entry
    public List<InventoryView> getAllInventoryEntries() {
        List<InventoryView> list = new ArrayList<>();
        java.sql.Date sqlDate;

        String sql = "SELECT v.IMG_PATH, p.PROD_CODE, p.PROD_NAME, v.SKU, " +
              "v.VAR_NAME, p.CATEGORY, v.VAR_PRICE, v.VAR_QTY, v.VAR_STATUS, v.CREATE_DATE, v.LAST_UPDATE, v.VAR_ID " +
              "FROM VARIATION v " +
              "JOIN PRODUCT p ON v.PROD_ID = p.PROD_ID " +
              "ORDER BY v.SKU ASC";

        try (Connection c = DBConnection.createConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                InventoryView entry = new InventoryView();
                
                entry.setImgPath(rs.getString("IMG_PATH"));
                entry.setProdCode(rs.getString("PROD_CODE"));
                entry.setProdName(rs.getString("PROD_NAME"));
                entry.setSku(rs.getString("SKU"));
                entry.setVarName(rs.getString("VAR_NAME"));
                entry.setCategory(rs.getString("CATEGORY"));
                entry.setVarPrice(rs.getBigDecimal("VAR_PRICE"));
                entry.setVarQty(rs.getInt("VAR_QTY"));
                entry.setVarStatus(rs.getString("VAR_STATUS"));
                sqlDate = rs.getDate("CREATE_DATE");
                if (sqlDate != null) {
                    entry.setCreateDate(sqlDate.toLocalDate());
                } else {
                    entry.setCreateDate(null);
                }

                sqlDate = rs.getDate("LAST_UPDATE");
                if (sqlDate != null) {
                    entry.setLastUpdate(sqlDate.toLocalDate());
                } else {
                    entry.setLastUpdate(null);
                }
                entry.setVarId(rs.getString("VAR_ID"));

                list.add(entry);
            }

        } catch (SQLException e) {
            System.err.println("ERROR fetching inventory entries");
            e.printStackTrace();
        }

        return list;
    }
    
    // Function to fetch the stock movement
    public List<InventoryView> getAllStockMovementEntries() {
        List<InventoryView> list = new ArrayList<>();
        
        String sql = "SELECT v.VAR_NAME, v.SKU, "
           + "SUM(CASE WHEN s.STOCK_TYPE = 'In' THEN s.STOCK_QTY ELSE 0 END) AS STOCK_IN, "
           + "SUM(CASE WHEN s.STOCK_TYPE = 'Out' THEN s.STOCK_QTY ELSE 0 END) AS STOCK_OUT, "
           + "v.VAR_QTY, v.VAR_STATUS, v.VAR_ID "
           + "FROM STOCK s "
           + "JOIN VARIATION v ON s.VAR_ID = v.VAR_ID "
           + "JOIN PRODUCT p ON v.PROD_ID = p.PROD_ID "
           + "GROUP BY v.VAR_ID, v.VAR_NAME, v.SKU, v.VAR_QTY, v.VAR_STATUS, v.VAR_ID "
           + "ORDER BY v.SKU";

        try (Connection c = DBConnection.createConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                InventoryView entry = new InventoryView();
                
                entry.setVarName(rs.getString("VAR_NAME"));
                entry.setSku(rs.getString("SKU"));
                entry.setStockIn(rs.getInt("STOCK_IN"));
                entry.setStockOut(rs.getInt("STOCK_OUT"));
                entry.setVarQty(rs.getInt("VAR_QTY"));
                entry.setVarStatus(rs.getString("VAR_STATUS"));
                entry.setVarId(rs.getString("VAR_ID"));

                list.add(entry);
            }

        } catch (SQLException e) {
            System.err.println("ERROR fetching inventory entries");
            e.printStackTrace();
        }

        return list;
    }
    
    // Filtered function to fetch the stock movement by STOCK_DATE
    public List<InventoryView> getAllStockMovementEntries(String startDate, String endDate) {
        List<InventoryView> list = new ArrayList<>();

        String sql = "SELECT v.VAR_NAME, v.SKU, " +
                     "SUM(CASE WHEN s.STOCK_TYPE = 'In' THEN s.STOCK_QTY ELSE 0 END) AS STOCK_IN, " +
                     "SUM(CASE WHEN s.STOCK_TYPE = 'Out' THEN s.STOCK_QTY ELSE 0 END) AS STOCK_OUT, " +
                     "v.VAR_QTY, v.VAR_STATUS, v.VAR_ID " +
                     "FROM VARIATION v " +
                     "LEFT JOIN STOCK s ON s.VAR_ID = v.VAR_ID " +
                     "JOIN PRODUCT p ON v.PROD_ID = p.PROD_ID " +
                     "WHERE s.STOCK_DATE BETWEEN ? AND ? " +
                     "GROUP BY v.VAR_ID, v.VAR_NAME, v.SKU, v.VAR_QTY, v.VAR_STATUS " +
                     "ORDER BY v.SKU";

        try (Connection c = DBConnection.createConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(startDate));
            ps.setDate(2, Date.valueOf(endDate));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    InventoryView entry = new InventoryView();

                    entry.setVarName(rs.getString("VAR_NAME"));
                    entry.setSku(rs.getString("SKU"));
                    entry.setStockIn(rs.getInt("STOCK_IN"));
                    entry.setStockOut(rs.getInt("STOCK_OUT"));
                    entry.setVarQty(rs.getInt("VAR_QTY"));
                    entry.setVarStatus(rs.getString("VAR_STATUS"));
                    entry.setVarId(rs.getString("VAR_ID"));

                    list.add(entry);
                }
            }

        } catch (SQLException e) {
            System.err.println("ERROR fetching stock movement entries (filtered)");
            e.printStackTrace();
        }

        return list;
    }
    
}
