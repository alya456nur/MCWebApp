package dao;

import view.ReceiptView;
import util.DBConnection;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustOrderDAO {

    // Fetch all receipts/orders for a customer
    public List<ReceiptView> getOrderEntries(String custId) {
        List<ReceiptView> list = new ArrayList<>();

        String sql =
            "SELECT r.RECEIPT_ID, r.RECEIPT_DATE, r.PAY_TYPE, r.GRAND_TOTAL, " +
            "o.ORDER_ID, o.ORDER_NO, o.STATUS, o.SUB_TOTAL, r.GST " +
            "FROM RECEIPT r " +
            "JOIN ORDERS o ON r.ORDER_ID = o.ORDER_ID " +
            "WHERE o.CUST_ID = ? AND o.STATUS = 'Pending' " +
            "ORDER BY r.RECEIPT_ID";

        try (Connection c = DBConnection.createConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, custId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ReceiptView receipt = new ReceiptView();

                    receipt.setReceiptId(rs.getString("RECEIPT_ID"));
                    receipt.setReceiptDate(rs.getDate("RECEIPT_DATE") != null
                            ? rs.getDate("RECEIPT_DATE").toString()
                            : null);
                    receipt.setPayType(rs.getString("PAY_TYPE"));
                    receipt.setGrandTotal(rs.getBigDecimal("GRAND_TOTAL"));

                    receipt.setOrderId(rs.getString("ORDER_ID"));
                    receipt.setOrderNo(rs.getString("ORDER_NO"));
                    receipt.setStatus(rs.getString("STATUS"));
                    receipt.setSubTotal(rs.getBigDecimal("SUB_TOTAL"));
                    receipt.setGst(rs.getBigDecimal("GST"));

                    // fetch all items for this order
                    receipt.setOrderItems(getOrderItems(receipt.getOrderId(), c));

                    list.add(receipt);
                }

                if (list.isEmpty()) {
                    System.out.println("No orders found for customer: " + custId);
                }
            }

        } catch (SQLException e) {
            System.err.println("ERROR fetching receipts for customer: " + custId);
            e.printStackTrace();
        }

        return list;
    }

    // Fetch all order items for a given order
    public List<ReceiptView> getOrderItems(String orderId, Connection c) {
        List<ReceiptView> items = new ArrayList<>();
        if (c == null) return items;

        String sql =
            "SELECT o.ORDER_ID, v.VAR_ID, v.SKU, ov.PROD_QTY, p.PROD_ID, p.PROD_NAME, " +
            "v.VAR_NAME, ov.TOTAL_PRICE, o.STATUS " +
            "FROM ORDERS o " +
            "JOIN ORDER_VAR ov ON o.ORDER_ID = ov.ORDER_ID " +
            "JOIN VARIATION v ON ov.VAR_ID = v.VAR_ID " +
            "JOIN PRODUCT p ON v.PROD_ID = p.PROD_ID " +
            "WHERE o.ORDER_ID = ?";

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, orderId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ReceiptView item = new ReceiptView();

                    item.setOrderId(rs.getString("ORDER_ID"));
                    item.setVarId(rs.getString("VAR_ID"));
                    item.setProdId(rs.getString("PROD_ID"));
                    item.setProdName(rs.getString("PROD_NAME"));
                    item.setVarName(rs.getString("VAR_NAME"));
                    item.setProdQty(rs.getInt("PROD_QTY"));
                    item.setSku(rs.getString("SKU"));
                    item.setTotalPrice(rs.getBigDecimal("TOTAL_PRICE"));
                    item.setStatus(rs.getString("STATUS"));

                    items.add(item);
                }
            }

        } catch (SQLException e) {
            System.err.println("ERROR fetching order items for ORDER_ID: " + orderId);
            e.printStackTrace();
        }

        return items;
    }
    
    // Cancelling order: mark order as Cancelled and assign staff
    public String cancelCustOrder(String orderId) throws SQLException {

        if (orderId == null || orderId.trim().isEmpty())
            throw new SQLException("Order is not selected.");

        try (Connection c = DBConnection.createConnection()) {
            c.setAutoCommit(false);

            try {
                // 1) Update ORDERS status to Cancelled
                String updOrder =
                    "UPDATE ORDERS SET STATUS = 'Cancelled' WHERE ORDER_ID = ?";
                try (PreparedStatement ps = c.prepareStatement(updOrder)) {
                    ps.setString(1, orderId);
                    ps.executeUpdate();
                }
                
                // 2) Record stock for cancelled order
                String selOrderVars = "SELECT VAR_ID, PROD_QTY FROM ORDER_VAR WHERE ORDER_ID = ?";
                try (PreparedStatement psSel = c.prepareStatement(selOrderVars)) {
                    psSel.setString(1, orderId);

                    try (ResultSet rs = psSel.executeQuery()) {
                       String insStock = "INSERT INTO STOCK (STOCK_ID, STOCK_TYPE, STOCK_QTY, STOCK_DATE, REMARK, VAR_ID, STAFF_ID, ORDER_ID) " +
                             "VALUES (?, 'In', ?, CURRENT_DATE, 'Order Cancelled', ?, ?, ?)";
                        String updVariation = "UPDATE VARIATION SET VAR_QTY = VAR_QTY + ? WHERE VAR_ID = ?";

                        while (rs.next()) {
                            String varId = rs.getString("VAR_ID");
                            int qty = rs.getInt("PROD_QTY");

                            // --- Generate next STOCK_ID ---
                            String stockId = null;
                            String selMaxId = "SELECT MAX(STOCK_ID) AS MAX_ID FROM STOCK";
                            try (PreparedStatement psMax = c.prepareStatement(selMaxId);
                                 ResultSet rsMax = psMax.executeQuery()) {
                                if (rsMax.next() && rsMax.getString("MAX_ID") != null) {
                                    String maxId = rsMax.getString("MAX_ID"); // e.g., ST004
                                    int num = Integer.parseInt(maxId.substring(2)); // skip "ST"
                                    stockId = String.format("ST%03d", num + 1); // ST005
                                } else {
                                    stockId = "ST001"; // first record
                                }
                            }

                            // 3a) Insert STOCK IN record
                            try (PreparedStatement psIns = c.prepareStatement(insStock)) {
                                psIns.setString(1, stockId);  // STOCK_ID
                                psIns.setInt(2, qty);         // STOCK_QTY
                                psIns.setString(3, varId);    // VAR_ID
                                psIns.setString(4, "S001");   // default STAFF_ID
                                psIns.setString(5, orderId);  // ORDER_ID
                                psIns.executeUpdate();
                            }

                            // 3b) Add back to VAR_QTY
                            try (PreparedStatement psUpd = c.prepareStatement(updVariation)) {
                                psUpd.setInt(1, qty);
                                psUpd.setString(2, varId);
                                psUpd.executeUpdate();
                            }
                        }
                    }
                }

                c.commit();
                return orderId;

            } catch (SQLException e) {
                c.rollback();
                throw e;
            } finally {
                c.setAutoCommit(true);
            }
        }
    }
}
