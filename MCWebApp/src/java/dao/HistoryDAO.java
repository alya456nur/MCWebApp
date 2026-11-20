package dao;

import view.ReceiptView;
import util.DBConnection;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HistoryDAO {

    // Fetch all receipts/orders for a customer
    public List<ReceiptView> getReceiptEntries(String custId) {
        List<ReceiptView> list = new ArrayList<>();

        String sql =
            "SELECT r.RECEIPT_ID, r.RECEIPT_DATE, r.PAY_TYPE, r.GRAND_TOTAL, " +
            "o.ORDER_ID, o.ORDER_NO, o.STATUS, o.SUB_TOTAL, r.GST " +
            "FROM RECEIPT r " +
            "JOIN ORDERS o ON r.ORDER_ID = o.ORDER_ID " +
            "WHERE o.CUST_ID = ? AND o.STATUS = 'Completed' " +
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
            "v.VAR_NAME, v.VAR_PRICE, ov.TOTAL_PRICE, o.STATUS " +
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
                    item.setVarPrice(rs.getBigDecimal("VAR_PRICE"));
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
    
    // Filter the receipt by date picked
    public List<ReceiptView> getReceiptEntriesByDate(
        String custId, String startDate, String endDate) {

    List<ReceiptView> list = new ArrayList<>();

    String sql =
        "SELECT r.RECEIPT_ID, r.RECEIPT_DATE, r.PAY_TYPE, r.GRAND_TOTAL, " +
        "o.ORDER_ID, o.ORDER_NO, o.STATUS, o.SUB_TOTAL, r.GST " +
        "FROM RECEIPT r " +
        "JOIN ORDERS o ON r.ORDER_ID = o.ORDER_ID " +
        "WHERE o.CUST_ID = ? " +
        "AND o.STATUS = 'Completed' " +
        "AND r.RECEIPT_DATE BETWEEN ? AND ? " +
        "ORDER BY r.RECEIPT_DATE";

    try (Connection c = DBConnection.createConnection();
         PreparedStatement ps = c.prepareStatement(sql)) {

        ps.setString(1, custId);
        ps.setDate(2, Date.valueOf(startDate));
        ps.setDate(3, Date.valueOf(endDate));

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            ReceiptView receipt = new ReceiptView();
            receipt.setReceiptId(rs.getString("RECEIPT_ID"));
            receipt.setReceiptDate(rs.getDate("RECEIPT_DATE").toString());
            receipt.setPayType(rs.getString("PAY_TYPE"));
            receipt.setGrandTotal(rs.getBigDecimal("GRAND_TOTAL"));
            receipt.setOrderId(rs.getString("ORDER_ID"));
            receipt.setOrderNo(rs.getString("ORDER_NO"));
            receipt.setStatus(rs.getString("STATUS"));
            receipt.setSubTotal(rs.getBigDecimal("SUB_TOTAL"));
            receipt.setGst(rs.getBigDecimal("GST"));

            receipt.setOrderItems(getOrderItems(receipt.getOrderId(), c));
            list.add(receipt);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return list;
}

}
