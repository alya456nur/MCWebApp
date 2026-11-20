package servlet;

import dao.HistoryDAO;
import view.ReceiptView;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.List;

@WebServlet("/History")
public class HistoryServlet extends HttpServlet {

    private HistoryDAO dao;

    @Override
    public void init() {
        dao = new HistoryDAO();
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

        // Load all the receipt into JSP
        List<ReceiptView> receipt = dao.getReceiptEntries(custId);
        request.setAttribute("receipt", receipt);
        
        // Load filtered receipts
        String startDate = request.getParameter("startDate");
        String endDate   = request.getParameter("endDate");

        if (startDate != null && endDate != null &&
            !startDate.isEmpty() && !endDate.isEmpty()) {

            receipt = dao.getReceiptEntriesByDate(custId, startDate, endDate);
            request.setAttribute("filterStart", startDate);
            request.setAttribute("filterEnd", endDate);

        } else {
            receipt = dao.getReceiptEntries(custId);
        }

        // Set attribute
        request.setAttribute("receipt", receipt);
        request.getRequestDispatcher("history.jsp").forward(request, response);

        RequestDispatcher rd = request.getRequestDispatcher("history.jsp");
        rd.forward(request, response);
    }
}
