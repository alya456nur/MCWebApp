//AddToCartServlet
package servlet;

import dao.AddToCartDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/AddToCart")
public class AddToCartServlet extends HttpServlet {

    private AddToCartDAO dao;

    @Override
    public void init() {
        dao = new AddToCartDAO();
    }

    private void writeJson(HttpServletResponse response, int status, String message) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // simple JSON (no extra libs)
        String safeMsg = (message == null) ? "" : message.replace("\\", "\\\\").replace("\"", "\\\"");
        response.getWriter().write("{\"message\":\"" + safeMsg + "\"}");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        // ✅ must login
        if (session == null || session.getAttribute("CUST_ID") == null) {
            writeJson(response, HttpServletResponse.SC_UNAUTHORIZED, "Please login first.");
            return;
        }

        String custId = String.valueOf(session.getAttribute("CUST_ID"));
        String varId = request.getParameter("varId");
        String qtyStr = request.getParameter("qty");

        // ✅ basic validation
        if (varId == null || varId.trim().isEmpty()) {
            writeJson(response, HttpServletResponse.SC_BAD_REQUEST, "Missing varId.");
            return;
        }

        int qty;
        try {
            qty = Integer.parseInt(qtyStr);
        } catch (Exception e) {
            writeJson(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid qty.");
            return;
        }

        if (qty <= 0) {
            writeJson(response, HttpServletResponse.SC_BAD_REQUEST, "Quantity must be > 0.");
            return;
        }

        try {
            dao.addToCart(custId, varId, qty);
            writeJson(response, HttpServletResponse.SC_OK, "OK");
        } catch (Exception e) {
            e.printStackTrace();
            writeJson(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }
}
