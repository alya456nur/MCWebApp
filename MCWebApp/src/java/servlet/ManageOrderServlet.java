package servlet;

import dao.ManageOrderDAO;
import view.ManageOrderView;
import model.Receipt;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.List;

@WebServlet("/ManageOrder")
public class ManageOrderServlet extends HttpServlet {

    private ManageOrderDAO dao;

    @Override
    public void init() {
        dao = new ManageOrderDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String status = request.getParameter("status");

        List<Receipt> orders;

        // Filter by status
        if (status != null && !status.isEmpty()) {
            orders = dao.getOrderByStatus(status);
            request.setAttribute("filterStatus", status);
        } else {
            orders = dao.getOrderEntries();
        }

        request.setAttribute("orders", orders);

        request.getRequestDispatcher("manageOrder.jsp")
               .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Optional: handle dropdown filtering if needed later
        doGet(request, response);
    }
}
