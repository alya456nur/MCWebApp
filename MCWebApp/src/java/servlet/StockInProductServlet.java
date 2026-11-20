package servlet;

import dao.StockInProductDAO;
import util.DBConnection;
import view.InventoryView;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;

@WebServlet("/StockInProduct")
public class StockInProductServlet extends HttpServlet {

    private StockInProductDAO dao;

    @Override
    public void init() {
        dao = new StockInProductDAO();
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
            request.getRequestDispatcher("/stockInProduct.jsp").forward(request, response);

        } catch (Exception e) {
            throw new ServletException("Error loading Stock In page", e);
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
            int stockInQty = Integer.parseInt(request.getParameter("stockIn"));
            String remark = request.getParameter("remark");
            String staffId = (String) session.getAttribute("STAFF_ID");

            dao.stockIn(varId, stockInQty, remark, staffId);

            response.sendRedirect(request.getContextPath() + "/Inventory");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Failed to stock in product: " + e.getMessage());
            request.getRequestDispatcher("/stockInProduct.jsp").forward(request, response);
        }
    }
}
