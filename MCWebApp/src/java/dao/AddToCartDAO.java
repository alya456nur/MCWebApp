// dao/AddToCartDAO.java
package dao;

import util.DBConnection;

import java.math.BigDecimal;
import java.sql.*;

public class AddToCartDAO {

    public void addToCart(String custId, String varId, int qty) throws SQLException {
        if (custId == null || custId.trim().isEmpty()) {
            throw new SQLException("Customer not logged in.");
        }
        if (varId == null || varId.trim().isEmpty()) {
            throw new SQLException("VAR_ID is missing.");
        }
        if (qty <= 0) {
            throw new SQLException("Quantity must be > 0.");
        }

        try (Connection conn = DBConnection.createConnection()) {
            if (conn == null) {
                throw new SQLException("DBConnection returned null connection");
            }

            conn.setAutoCommit(false);

            try {
                // 1) get variation price
                BigDecimal price = getVariationPrice(varId, conn);
                if (price == null) {
                    throw new SQLException("Invalid VAR_ID: " + varId);
                }

                // 2) find existing cart row (ORDER_ID IS NULL) for same customer + same variation
                CartRow existing = getExistingCartRow(custId, varId, conn);

                if (existing != null) {
                    int newQty = existing.prodQty + qty;
                    BigDecimal newTotal = price.multiply(BigDecimal.valueOf(newQty));

                    String up =
                            "UPDATE ORDER_VAR " +
                            "SET PROD_QTY = ?, TOTAL_PRICE = ? " +
                            "WHERE ORDERVAR_ID = ? AND CUST_ID = ? AND ORDER_ID IS NULL";

                    try (PreparedStatement ps = conn.prepareStatement(up)) {
                        ps.setInt(1, newQty);
                        ps.setBigDecimal(2, newTotal);
                        ps.setString(3, existing.orderVarId);
                        ps.setString(4, custId);
                        ps.executeUpdate();
                    }

                } else {
                    // 3) insert new cart row with ORDER_ID = NULL
                    String orderVarId = generateNextOrderVarId(conn);
                    BigDecimal total = price.multiply(BigDecimal.valueOf(qty));

                    String ins =
                            "INSERT INTO ORDER_VAR (ORDERVAR_ID, ORDER_ID, VAR_ID, CUST_ID, PROD_QTY, TOTAL_PRICE) " +
                            "VALUES (?, NULL, ?, ?, ?, ?)";

                    try (PreparedStatement ps = conn.prepareStatement(ins)) {
                        ps.setString(1, orderVarId);
                        ps.setString(2, varId);
                        ps.setString(3, custId);
                        ps.setInt(4, qty);
                        ps.setBigDecimal(5, total);
                        ps.executeUpdate();
                    }
                }

                conn.commit();

            } catch (SQLException ex) {
                conn.rollback();
                throw ex;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }

    // ===== helper methods =====
    private BigDecimal getVariationPrice(String varId, Connection conn) throws SQLException {
        String sql = "SELECT VAR_PRICE FROM VARIATION WHERE VAR_ID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, varId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getBigDecimal("VAR_PRICE");
            }
        }
        return null;
    }

    private static class CartRow {
        String orderVarId;
        int prodQty;
        CartRow(String orderVarId, int prodQty) {
            this.orderVarId = orderVarId;
            this.prodQty = prodQty;
        }
    }

    private CartRow getExistingCartRow(String custId, String varId, Connection conn) throws SQLException {
        String sql =
                "SELECT ORDERVAR_ID, PROD_QTY " +
                "FROM ORDER_VAR " +
                "WHERE CUST_ID = ? AND VAR_ID = ? AND ORDER_ID IS NULL";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, custId);
            ps.setString(2, varId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new CartRow(rs.getString("ORDERVAR_ID"), rs.getInt("PROD_QTY"));
                }
            }
        }
        return null;
    }

    private String generateNextOrderVarId(Connection conn) throws SQLException {
        String sql = "SELECT MAX(ORDERVAR_ID) FROM ORDER_VAR WHERE ORDERVAR_ID LIKE 'OV%'";
        int next = 1;

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                String maxId = rs.getString(1); // e.g. OV020
                if (maxId != null && maxId.startsWith("OV")) {
                    try {
                        next = Integer.parseInt(maxId.substring(2)) + 1;
                    } catch (Exception ignored) {}
                }
            }
        }
        return "OV" + String.format("%03d", next);
    }
}
