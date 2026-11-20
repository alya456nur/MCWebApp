package servlet;

import dao.EditCustDAO;
import model.Customer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/EditCust")
public class EditCustServlet extends HttpServlet {

    private EditCustDAO dao;

    @Override
    public void init() {
        dao = new EditCustDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("CUST_ID") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String custId = (String) session.getAttribute("CUST_ID");

        try {
            Customer cust = dao.getCustomerById(custId);
            if (cust == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Customer not found.");
                return;
            }

            request.setAttribute("cust", cust);
            request.getRequestDispatcher("/profile.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to load profile.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("CUST_ID") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String custId = (String) session.getAttribute("CUST_ID");

        String name = trim(request.getParameter("cust_name"));
        String phone = trim(request.getParameter("cust_phone"));
        String address = trim(request.getParameter("cust_address"));

        // ---- validation ----
        if (isBlank(name) || isBlank(phone)) {
            request.setAttribute("error", "Name and phone cannot be blank.");
            doGet(request, response);
            return;
        }

        // simple phone validation: allow digits, space, -, +
        if (!phone.matches("[0-9\\-\\+\\s]{7,20}")) {
            request.setAttribute("error", "Phone format is invalid.");
            doGet(request, response);
            return;
        }

        // address optional; if you want mandatory, uncomment:
        // if (isBlank(address)) { ... }
        try {
            boolean ok = dao.updateCustomerProfile(custId, name, phone, address);

            if (!ok) {
                request.setAttribute("error", "No changes saved (customer not found).");
                doGet(request, response);
                return;
            }

            // optionally update session display name if you store it
            session.setAttribute("CUST_NAME", name);

            response.sendRedirect(request.getContextPath() + "/EditCust?success=1");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Failed to update profile: " + e.getMessage());
            doGet(request, response);
        }
    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    private static String trim(String s) {
        return s == null ? "" : s.trim();
    }
}
