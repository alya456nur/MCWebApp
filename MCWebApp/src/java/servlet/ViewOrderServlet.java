package servlet;

import dao.ManageOrderDAO;
import view.ManageOrderView;
import util.DBConnection;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/ViewOrder")
public class ViewOrderServlet extends HttpServlet {

    private ManageOrderDAO dao;

    @Override
    public void init() {
        dao = new ManageOrderDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String orderId = request.getParameter("orderId");
       
        if (orderId == null || orderId.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing orderId");
            return;
        }

        try (Connection c = DBConnection.createConnection()) {

            List<ManageOrderView> orders =
                    dao.getDetailedOrderEntry(orderId, c);

            if (orders.isEmpty()) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Order not found");
                return;
            }

            request.setAttribute("orders", orders);

            RequestDispatcher rd =
                    request.getRequestDispatcher("viewOrder.jsp");
            rd.forward(request, response);

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String orderId = request.getParameter("orderId");
        String action  = request.getParameter("action");

        HttpSession session = request.getSession(false);
        String staffId = (String) session.getAttribute("STAFF_ID");

        if (staffId == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Staff not logged in.");
            return;
        }

        if (orderId == null || orderId.isEmpty() || action == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid request.");
            return;
        }

        try {
            if ("process".equals(action)) {
                dao.processOrder(staffId, orderId);
                session.setAttribute("success", "Order completed successfully.");

            } else if ("cancel".equals(action)) {
                dao.cancelOrder(staffId, orderId);
                session.setAttribute("success", "Order cancelled successfully.");
            }

            response.sendRedirect(request.getContextPath() + "/ManageOrder");

        } catch (Exception e) {
            throw new ServletException("Order action failed", e);
        }
    }


}
