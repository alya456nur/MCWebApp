package test;

import dao.ManageOrderDAO;
import view.ManageOrderView;
import util.DBConnection;
import java.sql.Connection;
import java.util.List;

public class TestDetailedViewDAO {

    public static void main(String[] args) {

        try (Connection c = DBConnection.createConnection()) {

            if (c == null) {
                System.err.println("DB connection failed.");
                return;
            }

            ManageOrderDAO dao = new ManageOrderDAO();
            String orderId = "O001"; // <-- must be in quotes

            // Fetch specific order
            List<ManageOrderView> orders = dao.getDetailedOrderEntry(orderId, c);

            System.out.println("Orders loaded: " + orders.size());
            for (ManageOrderView order : orders) {

                // Print header info
                System.out.println("========================================");
                System.out.println("Receipt ID: " + order.getReceiptId());
                System.out.println("Receipt Date: " + order.getReceiptDate());
                System.out.println("Pay Type: " + order.getPayType());
                System.out.println("Grand Total: " + order.getGrandTotal());

                System.out.println("Order ID: " + order.getOrderId());
                System.out.println("Order No: " + order.getOrderNo());

                System.out.println("Customer ID: " + order.getCustId());
                System.out.println("Customer No: " + order.getCustNo());
                System.out.println("Customer Name: " + order.getCustName());
                System.out.println("Customer Address: " + order.getCustAdd());
                System.out.println("Customer Phone: " + order.getCustPhone());
                System.out.println("Customer Email: " + order.getCustEmail());

                // Print order items
                if (order.getOrderItem() != null) {
                    System.out.println("--- Items ---");
                    for (ManageOrderView item : order.getOrderItem()) {
                        System.out.println(
                            "Var ID: " + item.getVarId() +
                            " | SKU: " + item.getSku() +
                            " | Var Name: " + item.getVarName() +
                            " | Quantity: " + item.getProdQty() +
                            " | Total Price: " + item.getTotalPrice() +
                            " | Status: " + item.getStatus()
                        );
                    }
                }

                System.out.println("========================================\n");
            }

        } catch (Exception e) {
            System.err.println("Unexpected error in TestDetailedViewDAO");
            e.printStackTrace();
        }

    }
}
