package dao;

import model.Receipt;
import view.ManageOrderView;
import util.DBConnection;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ManageOrderDAO {

    // Helper: fetch single int
    private int getSingleInt(String sql, String label) {
        try (Connection c = DBConnection.createConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) return rs.getInt(1);

        } catch (SQLException e) {
            System.err.println("ERROR fetching " + label);
            e.printStackTrace();
        }
        return 0;
    }

    // Helper: fetch single BigDecimal
    public BigDecimal getSingleBigDecimal(String sql, String label) {
        try (Connection c = DBConnection.createConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) return rs.getBigDecimal(1);

        } catch (SQLException e) {
            System.err.println("ERROR fetching " + label);
            e.printStackTrace();
        }
        return BigDecimal.ZERO;
    }

    // Summary: fetch all receipts/orders
    public List<Receipt> getOrderEntries() {
        List<Receipt> list = new ArrayList<>();

        String sql = "SELECT RECEIPT_ID, RECEIPT_DATE, PAY_TYPE, GRAND_TOTAL, ORDER_ID FROM RECEIPT ORDER BY RECEIPT_DATE DESC";

        try (Connection c = DBConnection.createConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Receipt receipt = new Receipt();
                receipt.setReceiptId(rs.getString("RECEIPT_ID"));
                receipt.setOrderId(rs.getString("ORDER_ID"));

                Date sqlDate = rs.getDate("RECEIPT_DATE");
                receipt.setReceiptDate(sqlDate != null ? sqlDate.toLocalDate() : null);

                receipt.setPayType(rs.getString("PAY_TYPE"));
                receipt.setGrandTotal(rs.getBigDecimal("GRAND_TOTAL"));

                receipt.setOrderItem(getAllOrderEntries(receipt.getOrderId(), c));

                list.add(receipt);
            }

            if (list.isEmpty()) {
                System.out.println("No receipts found in the database.");
            }

        } catch (SQLException e) {
            System.err.println("ERROR fetching receipts");
            e.printStackTrace();
        }

        return list;
    }

    // Fetch detailed order + customer info + order items
    public List<ManageOrderView> getDetailedOrderEntry(String orderId, Connection c) {
        List<ManageOrderView> list = new ArrayList<>();

        if (c == null) {
            System.err.println("DB connection is null. Cannot fetch order.");
            return list;
        }

        String sql =
            "SELECT r.RECEIPT_ID, r.RECEIPT_DATE, r.PAY_TYPE, r.GST, r.GRAND_TOTAL, " +
            "o.ORDER_ID, o.ORDER_NO, o.STATUS, o.SUB_TOTAL, " + 
            "c.CUST_ID, o.CUST_NO, c.CUST_NAME, c.CUST_ADD, c.CUST_PHONE, c.CUST_EMAIL " +
            "FROM ORDERS o " +
            "LEFT JOIN RECEIPT r ON o.ORDER_ID = r.ORDER_ID " +
            "JOIN CUSTOMER c ON o.CUST_ID = c.CUST_ID " +
            "WHERE r.ORDER_ID = ?";

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, orderId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) { // handle multiple receipts if exist
                    ManageOrderView entry = new ManageOrderView();

                    entry.setReceiptId(rs.getString("RECEIPT_ID"));
                    entry.setOrderId(rs.getString("ORDER_ID"));
                    entry.setOrderNo(rs.getString("ORDER_NO")); 
                    entry.setStatus(rs.getString("STATUS"));

                    Date sqlDate = rs.getDate("RECEIPT_DATE");
                    entry.setReceiptDate(sqlDate != null ? sqlDate.toLocalDate() : null);

                    entry.setPayType(rs.getString("PAY_TYPE"));
                    
                    entry.setGst(rs.getBigDecimal("GST"));
                    entry.setSubTotal(rs.getBigDecimal("SUB_TOTAL"));
                    entry.setGrandTotal(rs.getBigDecimal("GRAND_TOTAL"));

                    entry.setCustId(rs.getString("CUST_ID"));
                    entry.setCustNo(rs.getString("CUST_NO"));
                    entry.setCustName(rs.getString("CUST_NAME"));
                    entry.setCustAdd(rs.getString("CUST_ADD"));
                    entry.setCustPhone(rs.getString("CUST_PHONE"));
                    entry.setCustEmail(rs.getString("CUST_EMAIL"));

                    // Fetch all items for this order
                    entry.setOrderItem(getAllOrderEntries(rs.getString("ORDER_ID"), c));

                    list.add(entry);
                }

                if (list.isEmpty()) {
                    System.out.println("No detailed order found for ORDER_ID: " + orderId);
                }

            }
        } catch (SQLException e) {
            System.err.println("ERROR fetching detailed order view for ORDER_ID: " + orderId);
            e.printStackTrace();
        }

        System.out.println("OrderId param = " + orderId);
        System.out.println("Running detailed order query for " + orderId);

        return list;
    }

    // Fetch all order items for a given order (used by both summary and detailed)
    public List<ManageOrderView> getAllOrderEntries(String orderId, Connection c) {
        List<ManageOrderView> list = new ArrayList<>();

        if (c == null) {
            System.err.println("DB connection is null. Cannot fetch order items.");
            return list;
        }

        String sql =
            "SELECT o.ORDER_ID, v.VAR_ID, v.SKU, ov.PROD_QTY, p.PROD_NAME, v.VAR_NAME, ov.TOTAL_PRICE, o.STATUS " +
            "FROM ORDERS o " +
            "JOIN ORDER_VAR ov ON o.ORDER_ID = ov.ORDER_ID " +
            "JOIN VARIATION v ON ov.VAR_ID = v.VAR_ID " +
            "JOIN PRODUCT p ON v.PROD_ID = p.PROD_ID " +
            "WHERE o.ORDER_ID = ?";

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, orderId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ManageOrderView item = new ManageOrderView();

                    item.setOrderId(rs.getString("ORDER_ID"));
                    item.setVarId(rs.getString("VAR_ID"));
                    item.setSku(rs.getString("SKU"));
                    item.setProdQty(rs.getInt("PROD_QTY"));
                    item.setVarName(rs.getString("VAR_NAME"));
                    item.setTotalPrice(rs.getBigDecimal("TOTAL_PRICE"));
                    item.setStatus(rs.getString("STATUS"));

                    list.add(item);
                }

                System.out.println("Loaded " + list.size() + " items for ORDER_ID: " + orderId);
            }
        } catch (SQLException e) {
            System.err.println("ERROR fetching order items for ORDER_ID: " + orderId);
            e.printStackTrace();
        }

        return list;
    }

// Processing order: mark order as Completed and assign staff
    public String processOrder(String staffId, String orderId) throws SQLException {

        if (staffId == null || staffId.trim().isEmpty()) {
            throw new SQLException("Staff not logged in.");
        }
        if (orderId == null || orderId.trim().isEmpty()) {
            throw new SQLException("Order is not selected.");
        }

        try (Connection c = DBConnection.createConnection()) {
            c.setAutoCommit(false);

            try {
                // 1) Update ORDERS status to Completed
                String updOrder
                        = "UPDATE ORDERS SET STATUS = 'Completed' WHERE ORDER_ID = ?";
                try (PreparedStatement ps = c.prepareStatement(updOrder)) {
                    ps.setString(1, orderId);
                    ps.executeUpdate();
                }

                // 2) Update RECEIPT with staff who handled the order
                String updReceipt
                        = "UPDATE RECEIPT SET STAFF_ID = ? WHERE ORDER_ID = ?";
                try (PreparedStatement ps = c.prepareStatement(updReceipt)) {
                    ps.setString(1, staffId);
                    ps.setString(2, orderId);
                    ps.executeUpdate();
                }

                // 3) Update STOCK with staff who handled the order
                String updStock = "UPDATE STOCK SET STAFF_ID = ?, REMARK = 'Order Processed' WHERE ORDER_ID = ? AND STOCK_TYPE = 'Out'";

                try (PreparedStatement ps = c.prepareStatement(updStock)) {
                    ps.setString(1, staffId);
                    ps.setString(2, orderId);

                    int updated = ps.executeUpdate(); // single update, all stock rows for this order
                    if (updated == 0) {
                        System.out.println("No stock entries updated for ORDER_ID: " + orderId);
                    }
                }

                // 4) Update SOLD_QTY for each variation in the order
                String selOrderVars
                        = "SELECT VAR_ID, PROD_QTY FROM ORDER_VAR WHERE ORDER_ID = ?";

                try (PreparedStatement psSel = c.prepareStatement(selOrderVars)) {
                    psSel.setString(1, orderId);

                    try (ResultSet rs = psSel.executeQuery()) {
                        String updSold
                                = "UPDATE VARIATION SET SOLD_QTY = COALESCE(SOLD_QTY, 0) + ? WHERE VAR_ID = ?";

                        while (rs.next()) {
                            String varId = rs.getString("VAR_ID");
                            int qty = rs.getInt("PROD_QTY");

                            try (PreparedStatement psUpd = c.prepareStatement(updSold)) {
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
    
    // Canncelling order: mark order as Cancelled and assign staff
    public String cancelOrder(String staffId, String orderId) throws SQLException {

        if (staffId == null || staffId.trim().isEmpty())
            throw new SQLException("Staff not logged in.");
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

                // 2) Update RECEIPT with staff who handled the order
                String updReceipt =
                    "UPDATE RECEIPT SET STAFF_ID = ? WHERE ORDER_ID = ?";
                try (PreparedStatement ps = c.prepareStatement(updReceipt)) {
                    ps.setString(1, staffId);
                    ps.setString(2, orderId);
                    ps.executeUpdate();
                }
                
                // 3) Record stock for cancelled order
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
                                psIns.setString(1, stockId);
                                psIns.setInt(2, qty);
                                psIns.setString(3, varId);
                                psIns.setString(4, staffId);
                                psIns.setString(5, orderId);
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
    
    // Filter the orders by status
    public List<Receipt> getOrderByStatus(String status) {

        List<Receipt> list = new ArrayList<>();

        String sql =
            "SELECT r.RECEIPT_ID, r.RECEIPT_DATE, r.PAY_TYPE, r.GRAND_TOTAL, r.ORDER_ID " +
            "FROM RECEIPT r " +
            "JOIN ORDERS o ON o.ORDER_ID = r.ORDER_ID " +
            "WHERE o.STATUS = ? " +
            "ORDER BY r.RECEIPT_DATE ASC";

        try (Connection c = DBConnection.createConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, status);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Receipt receipt = new Receipt();

                    receipt.setReceiptId(rs.getString("RECEIPT_ID"));
                    receipt.setOrderId(rs.getString("ORDER_ID"));

                    Date sqlDate = rs.getDate("RECEIPT_DATE");
                    receipt.setReceiptDate(
                        sqlDate != null ? sqlDate.toLocalDate() : null
                    );

                    receipt.setPayType(rs.getString("PAY_TYPE"));
                    receipt.setGrandTotal(rs.getBigDecimal("GRAND_TOTAL"));

                    receipt.setOrderItem(
                        getAllOrderEntries(receipt.getOrderId(), c)
                    );

                    list.add(receipt);
                }
            }

        } catch (SQLException e) {
            System.err.println("ERROR fetching receipts by status");
            e.printStackTrace();
        }

        return list;
    }
    
}
