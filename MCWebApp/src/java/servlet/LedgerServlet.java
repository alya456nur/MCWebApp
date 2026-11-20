package servlet;

import dao.LedgerDAO;
import view.LedgerView;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.List;

@WebServlet("/Ledger")
public class LedgerServlet extends HttpServlet {

    private LedgerDAO dao;

    @Override
    public void init() {
        dao = new LedgerDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String startDate = request.getParameter("startDate");
        String endDate   = request.getParameter("endDate");

        List<LedgerView> record;

        if (startDate != null && endDate != null &&
            !startDate.isEmpty() && !endDate.isEmpty()) {

            record = dao.getStockByDate(startDate, endDate);
            request.setAttribute("filterStart", startDate);
            request.setAttribute("filterEnd", endDate);

        } else {
            record = dao.getAllStockEntries();
        }

        request.setAttribute("record", record);

        request.getRequestDispatcher("ledger.jsp")
               .forward(request, response);
    }
}
