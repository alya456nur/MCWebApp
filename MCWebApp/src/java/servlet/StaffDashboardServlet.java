package servlet;

import dao.StaffDashboardDAO;
import dao.AnalyticsDAO;
import model.Product;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/StaffDashboard")
public class StaffDashboardServlet extends HttpServlet {

    private StaffDashboardDAO dao;
    private AnalyticsDAO analytics;

    @Override
    public void init() {
        dao = new StaffDashboardDAO();
        analytics = new AnalyticsDAO(); // initialize this to prevent NPE
        
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        
        // Filter summary by date
        if (startDate != null && endDate != null && !startDate.isEmpty() && !endDate.isEmpty()) {
            // Filtered summary counts
            request.setAttribute("productCount", dao.getProductCount(startDate, endDate));
            request.setAttribute("completedCount", dao.getTotalOrders(startDate, endDate));
            request.setAttribute("pendingCount", dao.getPendingOrders(startDate, endDate));
            request.setAttribute("cancelledCount", dao.getCancelledOrders(startDate, endDate));
            request.setAttribute("customerCount", dao.getTotalCustomers());
            request.setAttribute("grandTotalSum", dao.getTotalSales(startDate, endDate));
                       
            // Filtered products + variations
            List<Product> products = dao.getAllProducts(startDate, endDate);
            request.setAttribute("products", products);
            
            // Filtered revenue map
            Map<String, Double> revenue;
            try {
                revenue = analytics.getMonthlyRevenue(startDate, endDate);
            } catch (Exception e) {
                revenue = new HashMap<>(); // fallback if DAO fails
            }
            if (revenue == null) {
                revenue = new HashMap<>();
            }
            request.setAttribute("revenue", revenue);
            
        } else {
            // Summary counts
            request.setAttribute("productCount", dao.getProductCount());
            request.setAttribute("completedCount", dao.getTotalOrders());
            request.setAttribute("pendingCount", dao.getPendingOrders());
            request.setAttribute("cancelledCount", dao.getCancelledOrders());
            request.setAttribute("customerCount", dao.getTotalCustomers());
            request.setAttribute("grandTotalSum", dao.getTotalSales());
            
            // All products + variations
            List<Product> products = dao.getAllProducts();
            request.setAttribute("products", products);
            
            // Revenue map
            Map<String, Double> revenue;
            try {
                revenue = analytics.getMonthlyRevenue();
            } catch (Exception e) {
                revenue = new HashMap<>(); // fallback if DAO fails
            }
            if (revenue == null) {
                revenue = new HashMap<>();
            }
            request.setAttribute("revenue", revenue);
        }
        
        // Forward once to JSP
        RequestDispatcher rd = request.getRequestDispatcher("staffDashboard.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
