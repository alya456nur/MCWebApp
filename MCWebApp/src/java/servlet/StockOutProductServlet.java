package servlet;

import dao.StockOutProductDAO;
import util.DBConnection;
import view.InventoryView;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;

@WebServlet("/StockOutProduct")
public class StockOutProductServlet extends HttpServlet {

    private StockOutProductDAO dao;

    @Override
    public void init() {
        dao = new StockOutProductDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("STAFF_ID") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String varId = request.getParameter("varId");
        if (varId == null || varId.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/Inventory");
            return;
        }

        try (Connection c = DBConnection.createConnection()) {
            InventoryView entry = dao.getStockEntry(varId, c);
            if (entry == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Variation not found");
                return;
            }

            request.setAttribute("product", entry);
            request.getRequestDispatcher("/stockOutProduct.jsp").forward(request, response);

        } catch (Exception e) {
            throw new ServletException("Error loading Stock Out page", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("STAFF_ID") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        try {
            String varId = request.getParameter("varId");
            int stockOutQty = Integer.parseInt(request.getParameter("stockOut"));
            String remark = request.getParameter("remark");
            String staffId = (String) session.getAttribute("STAFF_ID");

            dao.stockOut(varId, stockOutQty, remark, staffId);

            response.sendRedirect(request.getContextPath() + "/Inventory");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Failed to stock out product: " + e.getMessage());
            request.getRequestDispatcher("/stockOutProduct.jsp").forward(request, response);
        }
    }
}
