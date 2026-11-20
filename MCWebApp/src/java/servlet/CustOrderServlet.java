package servlet;

import dao.CustOrderDAO;
import view.ReceiptView;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.List;

@WebServlet("/Order")
public class CustOrderServlet extends HttpServlet {

    private CustOrderDAO dao;

    @Override
    public void init() {
        dao = new CustOrderDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("CUST_ID") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String custId = (String) session.getAttribute("CUST_ID");

        List<ReceiptView> orders = dao.getOrderEntries(custId);
        request.setAttribute("orders", orders);

        RequestDispatcher rd = request.getRequestDispatcher("order.jsp");
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

        String orderIdToCancel = request.getParameter("orderId");

        if (orderIdToCancel != null && !orderIdToCancel.isEmpty()) {
            try {
                dao.cancelCustOrder(orderIdToCancel);  // calls your DAO
                session.setAttribute("success", "Order has been cancelled successfully.");
            } catch (Exception e) {
                e.printStackTrace();
                session.setAttribute("error", "Failed to cancel order: " + e.getMessage());
            }
        }

        // Redirect back to order page
        response.sendRedirect(request.getContextPath() + "/Order");
    }

}
