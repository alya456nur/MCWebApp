package servlet;

import dao.InventoryDAO;
import view.InventoryView;
import util.DBConnection;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/Inventory")
public class InventoryServlet extends HttpServlet {

    private InventoryDAO dao;

    @Override
    public void init() {
        dao = new InventoryDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // --- OTHERWISE, DISPLAY FULL INVENTORY ---
        try {
            String startDate = request.getParameter("startDate");
            String endDate = request.getParameter("endDate");

            // Filter summary by date
            if (startDate != null && endDate != null && !startDate.isEmpty() && !endDate.isEmpty()) {
                // Filtered summary counts
                request.setAttribute("productCount", dao.getProductCount(startDate, endDate));
                request.setAttribute("stockInCount", dao.getStockInCount(startDate, endDate));
                request.setAttribute("stockOutCount", dao.getStockOutCount(startDate, endDate));
                request.setAttribute("lowStockCount", dao.getLowStockCount(startDate, endDate));
                request.setAttribute("inventoryValue", dao.getInventoryValue(startDate, endDate));
                
                // All product list
                List<InventoryView> products = dao.getAllInventoryEntries();
                request.setAttribute("products", products);

                // Filtered all stock movement list
                List<InventoryView> stock = dao.getAllStockMovementEntries(startDate, endDate);
                request.setAttribute("stock", stock);

            } else {
                // Summary counts
                request.setAttribute("productCount", dao.getProductCount());
                request.setAttribute("stockInCount", dao.getStockInCount());
                request.setAttribute("stockOutCount", dao.getStockOutCount());
                request.setAttribute("lowStockCount", dao.getLowStockCount());
                request.setAttribute("inventoryValue", dao.getInventoryValue());

                // All product list
                List<InventoryView> products = dao.getAllInventoryEntries();
                request.setAttribute("products", products);

                // All stock movement list
                List<InventoryView> stock = dao.getAllStockMovementEntries();
                request.setAttribute("stock", stock);
                
            }
            
            // Forward to main inventory page
            RequestDispatcher rd = request.getRequestDispatcher("inventory.jsp");
            rd.forward(request, response);
           
        } catch (Exception e) {
            throw new ServletException("Error fetching inventory data", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Optional: handle form submissions or filtering later
        doGet(request, response);
    }
}
