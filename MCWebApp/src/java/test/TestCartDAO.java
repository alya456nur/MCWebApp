/*package test;

import dao.CartDAO;
import view.CartView;

import java.math.BigDecimal;
import java.util.List;

public class TestCartDAO {

    public static void main(String[] args) {

        CartDAO dao = new CartDAO();

        System.out.println("=== Fetching cart items for dummy customer C001 ===");
        List<CartView> cart = dao.getCartItems();

        if (cart.isEmpty()) {
            System.out.println("No items found in cart for customer C001.");
        } else {
            for (CartView c : cart) {
                System.out.println(
                    "Order ID: " + c.getOrderId() +
                    " | OrderVar ID: " + c.getOrderVarId() +
                    " | Var ID: " + c.getVarId() +
                    " | Product Name: " + c.getProdName() +
                    " | Variation Name: " + c.getVarName() +
                    " | Quantity: " + c.getProdQty() +
                    " | Total Price: " + c.getTotalPrice() +
                    " | Img Path: " + c.getImgPath() +
                    " | Customer ID: " + c.getCustId()
                );
            }
        }

        System.out.println("\n=== Calculating total price ===");
   
    }
}
*/