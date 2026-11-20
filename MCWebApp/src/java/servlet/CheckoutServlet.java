// servlet/CheckoutServlet.java
package servlet;

import dao.CartDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/Checkout")
public class CheckoutServlet extends HttpServlet {

    private CartDAO dao;

    @Override
    public void init() {
        dao = new CartDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("CUST_ID") == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Please login first.");
            return;
        }

        String custId = (String) session.getAttribute("CUST_ID");
        String payType = request.getParameter("payType");

        String[] selected = request.getParameterValues("orderVarIds");

        if (payType == null || payType.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Payment type is required.");
            return;
        }

        if (selected == null || selected.length == 0) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No items selected.");
            return;
        }

        List<String> orderVarIds = Arrays.stream(selected)
                .filter(s -> s != null && !s.trim().isEmpty())
                .collect(Collectors.toList());

        try {
            String receiptId = dao.checkoutSelected(custId, payType, orderVarIds);
            response.sendRedirect(request.getContextPath() + "/Cart?success=1&receiptId=" + receiptId);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Checkout failed: " + e.getMessage());
        }
    }
}
