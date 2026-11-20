// dao/CartDAO.java
package dao;

import util.DBConnection;
import view.CartView;

import java.sql.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CartDAO {

    // 1) Cart items = ORDER_VAR rows where ORDER_ID IS NULL (current user's cart)
    public List<CartView> getCartItems(String custId) {
        List<CartView> list = new ArrayList<>();

        String sql =
                "SELECT ov.ORDERVAR_ID, ov.ORDER_ID, ov.VAR_ID, v.IMG_PATH, " +
                "       p.PROD_NAME, v.VAR_NAME, ov.PROD_QTY, ov.TOTAL_PRICE, ov.CUST_ID " +
                "FROM ORDER_VAR ov " +
                "JOIN VARIATION v ON ov.VAR_ID = v.VAR_ID " +
                "JOIN PRODUCT p ON v.PROD_ID = p.PROD_ID " +
                "WHERE ov.CUST_ID = ? AND ov.ORDER_ID IS NULL " +
                "ORDER BY ov.ORDERVAR_ID";

        try (Connection c = DBConnection.createConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, custId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    CartView cv = new CartView();
                    cv.setOrderVarId(rs.getString("ORDERVAR_ID"));
                    cv.setOrderId(rs.getString("ORDER_ID")); // will be null in cart
                    cv.setVarId(rs.getString("VAR_ID"));
                    cv.setImgPath(rs.getString("IMG_PATH")); // keep stored /assets/images/...
                    cv.setProdName(rs.getString("PROD_NAME"));
                    cv.setVarName(rs.getString("VAR_NAME"));
                    cv.setProdQty(rs.getInt("PROD_QTY"));
                    cv.setTotalPrice(rs.getBigDecimal("TOTAL_PRICE"));
                    cv.setCustId(rs.getString("CUST_ID"));
                    list.add(cv);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // checkout selected items: creates ORDERS, assigns selected ORDER_VAR.ORDER_ID, inserts RECEIPT
// checkout selected items: creates ORDERS, assigns selected ORDER_VAR.ORDER_ID, inserts RECEIPT
public String checkoutSelected(String custId, String payType, List<String> orderVarIds) throws SQLException {

    if (custId == null || custId.trim().isEmpty()) throw new SQLException("Customer not logged in.");
    if (payType == null || payType.trim().isEmpty()) throw new SQLException("Payment type is required.");
    if (orderVarIds == null || orderVarIds.isEmpty()) throw new SQLException("No items selected.");

    String staffId = "S001";

    try (Connection c = DBConnection.createConnection()) {
        c.setAutoCommit(false);

        try {
            // 1) Verify selected items belong to this user's cart
            for (String ovId : orderVarIds) {
                if (!isOrderVarInNullCart(c, custId, ovId)) {
                    throw new SQLException("Invalid cart item selection: " + ovId);
                }
            }

            // 2) Subtotal
            BigDecimal subTotal = getSelectedSubtotal(c, custId, orderVarIds);
            if (subTotal.compareTo(BigDecimal.ZERO) <= 0) {
                throw new SQLException("Selected items subtotal is RM0.00");
            }

            // 3) Create ORDER
            String newOrderId = generateNextId(c, "ORDERS", "ORDER_ID", "O", 3);
            String orderNo = "ORD-" + newOrderId.substring(1);
            String custNo = "CN" + custId.substring(1);

            String insOrder =
                "INSERT INTO ORDERS (ORDER_ID, ORDER_NO, CUST_NO, SUB_TOTAL, STATUS, CUST_ID) " +
                "VALUES (?, ?, ?, ?, 'Pending', ?)";

            try (PreparedStatement ps = c.prepareStatement(insOrder)) {
                ps.setString(1, newOrderId);
                ps.setString(2, orderNo);
                ps.setString(3, custNo);
                ps.setBigDecimal(4, subTotal);
                ps.setString(5, custId);
                ps.executeUpdate();
            }

            // 4) Assign ORDER_VAR to ORDER
            String upd =
                "UPDATE ORDER_VAR SET ORDER_ID = ? " +
                "WHERE ORDERVAR_ID = ? AND CUST_ID = ? AND ORDER_ID IS NULL";

            try (PreparedStatement ps = c.prepareStatement(upd)) {
                for (String ovId : orderVarIds) {
                    ps.setString(1, newOrderId);
                    ps.setString(2, ovId);
                    ps.setString(3, custId);
                    ps.addBatch();
                }
                ps.executeBatch();
            }

            // 4.5 Deduct inventory stock

            // Get VAR_ID and quantity purchased for selected items
            String selectItems =
                "SELECT VAR_ID, PROD_QTY FROM ORDER_VAR WHERE ORDERVAR_ID = ?";

            // Update stock for each variation
            String updateStock =
                "UPDATE VARIATION SET VAR_QTY = VAR_QTY - ? WHERE VAR_ID = ? AND VAR_QTY >= ?";

            try (
                PreparedStatement psSelect = c.prepareStatement(selectItems);
                PreparedStatement psUpdate = c.prepareStatement(updateStock)
            ) {
                for (String ovId : orderVarIds) {

                    psSelect.setString(1, ovId);
                    try (ResultSet rs = psSelect.executeQuery()) {

                        if (!rs.next()) {
                            throw new SQLException("Order item not found: " + ovId);
                        }

                        String varId = rs.getString("VAR_ID");          // Sini
                        int qty = rs.getInt("PROD_QTY");          // Sini

                        psUpdate.setInt(1, qty);                  
                        psUpdate.setString(2, varId);              
                        psUpdate.setInt(3, qty);                  

                        int updated = psUpdate.executeUpdate();
                        if (updated == 0) {
                            throw new SQLException("Insufficient stock for variation: " + varId);
                        }
                    }
                }
            }
            
            // Insert STOCK OUT record
            String insStock =
                "INSERT INTO STOCK (STOCK_ID, STOCK_TYPE, STOCK_QTY, STOCK_DATE, REMARK, VAR_ID, STAFF_ID, ORDER_ID) " +
                "VALUES (?, 'Out', ?, CURRENT_DATE, 'Online Purchase', ?, ?, ?)";

            try (PreparedStatement psStock = c.prepareStatement(insStock)) {

                for (String ovId : orderVarIds) {

                    String sel =
                        "SELECT VAR_ID, PROD_QTY FROM ORDER_VAR WHERE ORDERVAR_ID = ?";
                    try (PreparedStatement psSel = c.prepareStatement(sel)) {
                        psSel.setString(1, ovId);

                        try (ResultSet rs = psSel.executeQuery()) {
                            if (!rs.next()) {
                                throw new SQLException("Order item not found: " + ovId);
                            }

                            String varId = rs.getString("VAR_ID");
                            int qty = rs.getInt("PROD_QTY");

                            String stockId = generateNextId(c, "STOCK", "STOCK_ID", "ST", 3);

                            psStock.setString(1, stockId);
                            psStock.setInt(2, qty);
                            psStock.setString(3, varId);
                            psStock.setString(4, staffId);   // default system cashier
                            psStock.setString(5, newOrderId); // store the order ID
                            psStock.addBatch();


                            psStock.executeUpdate(); // dO not use batch
                        }
                    }
                }
            }

            // 5) Insert RECEIPT
            BigDecimal gst = subTotal.multiply(new BigDecimal("0.06"));
            BigDecimal grandTotal = subTotal.add(gst);

            String receiptId = generateNextId(c, "RECEIPT", "RECEIPT_ID", "R", 3);

            String insReceipt =
                "INSERT INTO RECEIPT (RECEIPT_ID, PAY_TYPE, RECEIPT_DATE, GST, GRAND_TOTAL, STAFF_ID, ORDER_ID) " +
                "VALUES (?, ?, CURRENT_DATE, ?, ?, ?, ?)";

            try (PreparedStatement ps = c.prepareStatement(insReceipt)) {
                ps.setString(1, receiptId);
                ps.setString(2, payType);
                ps.setBigDecimal(3, gst);
                ps.setBigDecimal(4, grandTotal);
                ps.setString(5, staffId);
                ps.setString(6, newOrderId);
                ps.executeUpdate();
            }

            c.commit();
            return receiptId;

        } catch (SQLException e) {
            c.rollback();
            throw e;
        } finally {
            c.setAutoCommit(true);
        }
    }
}

    
    // Remove from cart: delete from ORDER_VAR table
    public String removeFromCart(String orderVarId) throws SQLException {

        if (orderVarId == null || orderVarId.trim().isEmpty())
            throw new SQLException("Order item not selected.");

        try (Connection c = DBConnection.createConnection()) {
            c.setAutoCommit(false);

            try {
                // 1) Remove order item from the cart
                String deleteItem =
                    "DELETE FROM ORDER_VAR WHERE ORDERVAR_ID = ?";
                try (PreparedStatement ps = c.prepareStatement(deleteItem)) {
                    ps.setString(1, orderVarId);
                    ps.executeUpdate();
                }

                c.commit();
                return orderVarId;

            } catch (SQLException e) {
                c.rollback();
                throw e;
            } finally {
                c.setAutoCommit(true);
            }
        }
    }

    // ---- helpers ----

    private boolean isOrderVarInNullCart(Connection c, String custId, String orderVarId) throws SQLException {
        String sql =
                "SELECT 1 FROM ORDER_VAR " +
                "WHERE ORDERVAR_ID = ? AND CUST_ID = ? AND ORDER_ID IS NULL";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, orderVarId);
            ps.setString(2, custId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    private BigDecimal getSelectedSubtotal(Connection c, String custId, List<String> orderVarIds) throws SQLException {
        BigDecimal total = BigDecimal.ZERO;

        String sql =
                "SELECT TOTAL_PRICE FROM ORDER_VAR " +
                "WHERE ORDERVAR_ID = ? AND CUST_ID = ? AND ORDER_ID IS NULL";

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            for (String ovId : orderVarIds) {
                ps.setString(1, ovId);
                ps.setString(2, custId);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        BigDecimal p = rs.getBigDecimal("TOTAL_PRICE");
                        if (p != null) total = total.add(p);
                    }
                }
            }
        }
        return total;
    }

    private String generateNextId(Connection conn, String table, String col, String prefix, int digits) throws SQLException {
        String sql = "SELECT MAX(" + col + ") FROM " + table + " WHERE " + col + " LIKE ?";
        int nextNum = 1;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, prefix + "%");
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String maxId = rs.getString(1);
                    if (maxId != null && maxId.startsWith(prefix)) {
                        String num = maxId.substring(prefix.length());
                        try {
                            nextNum = Integer.parseInt(num) + 1;
                        } catch (Exception ignored) {}
                    }
                }
            }
        }

        return prefix + String.format("%0" + digits + "d", nextNum);
    }
}
