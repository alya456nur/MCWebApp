package servlet;

import dao.AddStaffDAO;
import model.Staff;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.regex.Pattern;

@WebServlet("/AddStaff")
public class AddStaffServlet extends HttpServlet {

    private AddStaffDAO dao;

    // Simple email regex (good enough for uni projects)
    private static final Pattern EMAIL_PATTERN
            = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    @Override
    public void init() {
        dao = new AddStaffDAO();
    }

    // ================= GET =================
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("STAFF_ID") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        if (!"admin".equals(session.getAttribute("USER_ROLE"))) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Admin only");
            return;
        }

        request.getRequestDispatcher("addStaff.jsp").forward(request, response);
    }

    // ================= POST =================
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || !"admin".equals(session.getAttribute("USER_ROLE"))) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        try {
            String name = request.getParameter("staff_name");
            String email = request.getParameter("staff_email");
            String password = request.getParameter("staff_password");
            String phone = request.getParameter("staff_phone");
            String role = request.getParameter("user_role");

            // ================= VALIDATION =================
            // 1. Check blank fields
            if (isBlank(name) || isBlank(email) || isBlank(password) || isBlank(phone) || isBlank(role)) {
                request.setAttribute("error", "All required fields must be filled.");
                request.getRequestDispatcher("addStaff.jsp").forward(request, response);
                return;
            }

            // 2. Email format
            if (!EMAIL_PATTERN.matcher(email).matches()) {
                request.setAttribute("error", "Invalid email format.");
                request.getRequestDispatcher("addStaff.jsp").forward(request, response);
                return;
            }

            // 3. Email uniqueness
            if (dao.emailExists(email)) {
                request.setAttribute("error", "Email already exists.");
                request.getRequestDispatcher("addStaff.jsp").forward(request, response);
                return;
            }

            // ================= SAVE =================
            Staff staff = new Staff();
            staff.setStaffName(name.trim());
            staff.setStaffEmail(email.trim());
            staff.setStaffPass(password); // (hash later if needed)
            staff.setStaffPhone(phone != null ? phone.trim() : null);
            staff.setUserRole(role);

            dao.addStaff(staff);

            response.sendRedirect(request.getContextPath() + "/Staff");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Failed to add staff.");
            request.getRequestDispatcher("addStaff.jsp").forward(request, response);
        }
    }

    // ================= HELPER =================
    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
