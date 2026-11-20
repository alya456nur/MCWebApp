package dao;

import java.math.BigDecimal;
import model.Product;
import model.Variation;

import util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StaffDashboardDAO {
    
    // Helper function to get a single Int
    private int getSingleInt(String sql, String label) {
        try (Connection c = DBConnection.createConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) return rs.getInt(1);

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

    public int getTotalOrders() {
        return getSingleInt("SELECT COUNT(*) FROM ORDERS WHERE STATUS = 'Completed'", "Total Orders");
    }

    public int getPendingOrders() {
        return getSingleInt("SELECT COUNT(*) FROM ORDERS WHERE STATUS = 'Pending'", "Pending Orders");
    }

    public int getCancelledOrders() {
        return getSingleInt("SELECT COUNT(*) FROM ORDERS WHERE STATUS = 'Cancelled'", "Cancelled Orders");
    }

    public int getTotalCustomers() {
        return getSingleInt("SELECT COUNT(*) FROM CUSTOMER", "Total Customers");
    }

    public BigDecimal getTotalSales() {
        return getSingleBigDecimal(
            "SELECT COALESCE(SUM(GRAND_TOTAL),0) " +
            "FROM RECEIPT r JOIN ORDERS o ON r.ORDER_ID = o.ORDER_ID " +
            "WHERE o.STATUS = 'Completed'",
            "Total Sales"
        );
    }
    
    // Fetch and display the summary base on date
    // Total stocks of all product by date
    public int getProductCount(String startDate, String endDate) {
        int total = 0;
        
        String sql = "SELECT SUM(VAR_QTY)"
                + "FROM VARIATION "
                + "WHERE CREATE_DATE BETWEEN ? AND ?";
        
        try (Connection c = DBConnection.createConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
  
            ps.setDate(1, Date.valueOf(startDate));
            ps.setDate(2, Date.valueOf(endDate));

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return total;
        
    }
    
    // Total completed orders by date
    public int getTotalOrders(String startDate, String endDate) {
        int total = 0;

        String sql =
            "SELECT COUNT(*) " +
            "FROM ORDERS o " +
            "JOIN RECEIPT r ON o.ORDER_ID = r.ORDER_ID " +
            "WHERE o.STATUS = 'Completed' " +
            "AND r.RECEIPT_DATE BETWEEN ? AND ?";

        try (Connection c = DBConnection.createConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
  
            ps.setDate(1, Date.valueOf(startDate));
            ps.setDate(2, Date.valueOf(endDate));

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return total;
    }

    // Generic helper
    // Total orders by status Pending, Cancelled, Completed
    private int getOrdersByStatus(String status, String startDate, String endDate, String label) {
        int total = 0;

        String sql =
            "SELECT COUNT(*) " +
            "FROM ORDERS o " +
            "JOIN RECEIPT r ON o.ORDER_ID = r.ORDER_ID " +
            "WHERE o.STATUS = ? " +
            "AND r.RECEIPT_DATE BETWEEN ? AND ?";

        try (Connection c = DBConnection.createConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setDate(2, Date.valueOf(startDate));
            ps.setDate(3, Date.valueOf(endDate));

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    total = rs.getInt(1);
                }
            }

        } catch (SQLException e) {
            System.err.println("ERROR fetching " + label);
            e.printStackTrace();
        }

        return total;
    }

    // Specific methods
    public int getPendingOrders(String startDate, String endDate) {
        return getOrdersByStatus("Pending", startDate, endDate, "Pending Orders");
    }

    public int getCancelledOrders(String startDate, String endDate) {
        return getOrdersByStatus("Cancelled", startDate, endDate, "Cancelled Orders");
    }
    
    // Filter the total sales by date
    public BigDecimal getTotalSales(String startDate, String endDate) {
        BigDecimal total = BigDecimal.ZERO;

        String sql =
            "SELECT COALESCE(SUM(GRAND_TOTAL),0) " +
            "FROM RECEIPT r " +
            "JOIN ORDERS o ON r.ORDER_ID = o.ORDER_ID " +
            "WHERE o.STATUS = 'Completed' " +
            "AND r.RECEIPT_DATE BETWEEN ? AND ?";

        try (Connection c = DBConnection.createConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(startDate));
            ps.setDate(2, Date.valueOf(endDate));

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    total = rs.getBigDecimal(1);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return total;
    }

    // Function to fetch all products with their variations
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String prodSql = "SELECT PROD_ID, PROD_NAME, PROD_CODE, CATEGORY, TOTAL_QTY, PROD_STATUS, IMG_PATH FROM PRODUCT";

        try (Connection c = DBConnection.createConnection();
             PreparedStatement ps = c.prepareStatement(prodSql);
             ResultSet rs = ps.executeQuery()) {

            if (c == null) {
                System.err.println("DB connection is null. Cannot fetch products.");
                return products;
            }

            while (rs.next()) {
                Product p = new Product();
                p.setProdId(rs.getString("PROD_ID"));
                p.setProdName(rs.getString("PROD_NAME"));
                p.setProdCode(rs.getString("PROD_CODE"));
                p.setCategory(rs.getString("CATEGORY"));
                p.setTotalQty(rs.getInt("TOTAL_QTY"));
                p.setProdStatus(rs.getString("PROD_STATUS"));
                p.setImgPath(rs.getString("IMG_PATH"));

                // Fetch the variations
                try {
                    p.setVariations(getVariationsForProduct(p.getProdId(), c));
                } catch (Exception e) {
                    System.err.println("Error fetching variations for product " + p.getProdId());
                    e.printStackTrace();
                }

                products.add(p);
            }

            System.out.println("Products loaded: " + products.size());

        } catch (SQLException e) {
            System.err.println("ERROR in getAllProducts()");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unexpected error in getAllProducts()");
            e.printStackTrace();
        }

        return products;
    }
    
    // Filtered, function to fetch products with variations filtered by creation date
    public List<Product> getAllProducts(String startDate, String endDate) {
        List<Product> products = new ArrayList<>();
        String prodSql = "SELECT PROD_ID, PROD_NAME, PROD_CODE, CATEGORY, TOTAL_QTY, PROD_STATUS, IMG_PATH " +
                         "FROM PRODUCT " +
                         "WHERE CREATE_DATE BETWEEN ? AND ?";

        try (Connection c = DBConnection.createConnection();
             PreparedStatement ps = c.prepareStatement(prodSql)) {

            if (c == null) {
                System.err.println("DB connection is null. Cannot fetch products.");
                return products;
            }

            // Convert Strings to java.sql.Date
            ps.setDate(1, Date.valueOf(startDate));
            ps.setDate(2, Date.valueOf(endDate));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Product p = new Product();
                    p.setProdId(rs.getString("PROD_ID"));
                    p.setProdName(rs.getString("PROD_NAME"));
                    p.setProdCode(rs.getString("PROD_CODE"));
                    p.setCategory(rs.getString("CATEGORY"));
                    p.setTotalQty(rs.getInt("TOTAL_QTY"));
                    p.setProdStatus(rs.getString("PROD_STATUS"));
                    p.setImgPath(rs.getString("IMG_PATH"));

                    // Fetch the variations for this product
                    try {
                        p.setVariations(getVariationsForProduct(p.getProdId(), startDate, endDate, c));
                    } catch (Exception e) {
                        System.err.println("Error fetching variations for product " + p.getProdId());
                        e.printStackTrace();
                    }
                    
                    products.add(p);
                }
            }

            System.out.println("Filtered products loaded: " + products.size());

        } catch (SQLException e) {
            System.err.println("ERROR in getProductsByDate()");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unexpected error in getProductsByDate()");
            e.printStackTrace();
        }

        return products;
    }
    
    // Function to variations of the product
    private List<Variation> getVariationsForProduct(String productId, Connection c) {
        List<Variation> list = new ArrayList<>();
        String sql = "SELECT VAR_ID, VAR_NAME, SOLD_QTY, VAR_PRICE, (SOLD_QTY * VAR_PRICE) AS SALES " +
                        "FROM VARIATION " +
                        "WHERE PROD_ID = ?";

        if (c == null) {
            System.err.println("DB connection is null. Cannot fetch variations.");
            return list;
        }

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, productId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Variation v = new Variation();
                    v.setVarId(rs.getString("VAR_ID"));
                    v.setVarName(rs.getString("VAR_NAME"));
                    v.setSoldQty(rs.getInt("SOLD_QTY"));
                    v.setVarPrice(rs.getBigDecimal("VAR_PRICE"));
                    v.setSales(rs.getBigDecimal("SALES"));
                    list.add(v);
                }
            }

            System.out.println("Variations for " + productId + ": " + list.size());

        } catch (SQLException e) {
            System.err.println("ERROR fetching variations for product " + productId);
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unexpected error fetching variations for product " + productId);
            e.printStackTrace();
        }

        return list;
    }
    
    // Filtered, function to fetch variations of the product filtered by date
    private List<Variation> getVariationsForProduct(String productId, String startDate, String endDate, Connection c) {
        List<Variation> list = new ArrayList<>();
        String sql = "SELECT VAR_ID, VAR_NAME, SOLD_QTY, VAR_PRICE, (SOLD_QTY * VAR_PRICE) AS SALES " +
                     "FROM VARIATION " +
                     "WHERE PROD_ID = ? " +
                     "AND CREATE_DATE BETWEEN ? AND ?";

        if (c == null) {
            System.err.println("DB connection is null. Cannot fetch variations.");
            return list;
        }

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, productId);
            ps.setDate(2, Date.valueOf(startDate));
            ps.setDate(3, Date.valueOf(endDate));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Variation v = new Variation();
                    v.setVarId(rs.getString("VAR_ID"));
                    v.setVarName(rs.getString("VAR_NAME"));
                    v.setSoldQty(rs.getInt("SOLD_QTY"));
                    v.setVarPrice(rs.getBigDecimal("VAR_PRICE"));
                    v.setSales(rs.getBigDecimal("SALES"));
                    list.add(v);
                }
            }

            System.out.println("Variations for " + productId + " (filtered): " + list.size());

        } catch (SQLException e) {
            System.err.println("ERROR fetching variations for product " + productId);
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unexpected error fetching variations for product " + productId);
            e.printStackTrace();
        }

        return list;
    }

}
