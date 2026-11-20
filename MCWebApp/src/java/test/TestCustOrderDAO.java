package test;

import dao.CustOrderDAO;
import view.ReceiptView;

import java.util.List;

public class TestCustOrderDAO {
    public static void main(String[] args) {
        CustOrderDAO dao = new CustOrderDAO();
        String testCustId = "C001"; // change to a valid customer ID in your DB

        List<ReceiptView> receipts = dao.getOrderEntries(testCustId);

        for (ReceiptView r : receipts) {
            System.out.println("Receipt ID: " + r.getReceiptId());
            System.out.println("Order ID: " + r.getOrderId());
            System.out.println("Status: " + r.getStatus());
            System.out.println("Subtotal: " + r.getSubTotal());
            System.out.println("GST: " + r.getGst());
            System.out.println("Grand Total: " + r.getGrandTotal());
            System.out.println("Receipt Date: " + r.getReceiptDate());
            System.out.println("Items:");
            r.getOrderItems().forEach(item -> {
                System.out.println("  Var ID: " + item.getVarId() + ", SKU: " + item.getSku() +
                        ", Qty: " + item.getProdQty() + ", Name: " + item.getProdName() +
                        ", Total: " + item.getTotalPrice());
            });
            System.out.println("---------------------------");
        }
    }
}
