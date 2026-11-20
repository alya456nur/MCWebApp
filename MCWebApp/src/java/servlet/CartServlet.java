// servlet/CartServlet.java
package servlet;

import dao.CartDAO;
import view.CartView;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.List;

@WebServlet("/Cart")
public class CartServlet extends HttpServlet {

    private CartDAO dao;

    @Override
    public void init() {
        dao = new CartDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("CUST_ID") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String custId = (String) session.getAttribute("CUST_ID");

        try {
            List<CartView> cartItems = dao.getCartItems(custId);
            request.setAttribute("cartItems", cartItems);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Failed to load cart: " + e.getMessage());
        }

        RequestDispatcher rd = request.getRequestDispatcher("cart.jsp");
        rd.forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("CUST_ID") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String orderVarId = request.getParameter("orderVarId");
        
        if (orderVarId != null && !orderVarId.isEmpty()) {
            try {
                dao.removeFromCart(orderVarId);
                session.setAttribute("success", "Item removed from cart successfully.");
            } catch (Exception e) {
                e.printStackTrace();
                session.setAttribute("error", "Failed to remove item: " + e.getMessage());
            }
        }

        response.sendRedirect(request.getContextPath() + "/Cart");
    }

}
