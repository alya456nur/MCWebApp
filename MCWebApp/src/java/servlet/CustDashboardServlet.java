package servlet;

import dao.CustDashboardDAO;
import view.CustDashboardView;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.List;

@WebServlet("/CustDashboard")
public class CustDashboardServlet extends HttpServlet {

    private CustDashboardDAO dao;

    @Override
    public void init() {
        dao = new CustDashboardDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // All products + variations
        List<CustDashboardView> eyesProduct = dao.getEyesVariations();
        request.setAttribute("eyesProducts", eyesProduct);
        
        // All products + variations
        List<CustDashboardView> lipsProducts = dao.getLipsVariations();
        request.setAttribute("lipsProducts", lipsProducts);

        RequestDispatcher rd = request.getRequestDispatcher("custDashboard.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Optional: handle dropdown filtering if needed later
        doGet(request, response);
    }
}
