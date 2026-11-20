package test;

import dao.ManageOrderDAO;
import model.Receipt;
import view.ManageOrderView;
import util.DBConnection;

import java.util.List;
import java.sql.Connection;

public class testManageOrderDAO {

    public static void main(String[] args) {

        ManageOrderDAO dao = new ManageOrderDAO();

        System.out.println("Fetching all receipts and associated order items...");

        try (Connection c = DBConnection.createConnection()) {

            // Fetch all receipts
            List<Receipt> receipts = dao.getOrderEntries();

            System.out.println("Total receipts loaded: " + receipts.size());
            System.out.println("========================================");

            for (Receipt receipt : receipts) {
                System.out.println("Receipt ID: " + receipt.getReceiptId());
                System.out.println("Order ID  : " + receipt.getOrderId());
                System.out.println("Date      : " + receipt.getReceiptDate());
                System.out.println("Payment   : " + receipt.getPayType());
                System.out.println("GrandTotal: " + receipt.getGrandTotal());
                System.out.println("Items:");

                List<ManageOrderView> items = receipt.getOrderItem();
                if (items != null && !items.isEmpty()) {
                    for (ManageOrderView item : items) {
                        System.out.println(
                            "  VAR_ID: " + item.getVarId() +
                            " | SKU: " + item.getSku() +
                            " | Qty: " + item.getProdQty() +
                            " | Var Name: " + item.getVarName() +
                            " | Total Price: " + item.getTotalPrice() +
                            " | Status: " + item.getStatus()
                        );
                    }
                } else {
                    System.out.println("  No items for this order.");
                }

                System.out.println("========================================");
            }

        } catch (Exception e) {
            System.err.println("Failed to fetch receipts and order items.");
            e.printStackTrace();
        }
    }
}
