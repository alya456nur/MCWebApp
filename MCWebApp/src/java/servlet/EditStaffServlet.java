package servlet;

import dao.EditStaffDAO;
import model.Staff;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.regex.Pattern;

@WebServlet("/EditStaff")
public class EditStaffServlet extends HttpServlet {

    private EditStaffDAO dao;

    private static final Pattern EMAIL_PATTERN
            = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    @Override
    public void init() {
        dao = new EditStaffDAO();
    }

    private boolean isAdmin(HttpSession session) {
        if (session == null) {
            return false;
        }
        Object staffId = session.getAttribute("STAFF_ID");
        Object role = session.getAttribute("USER_ROLE"); // must be "admin"
        return staffId != null && role != null && "admin".equalsIgnoreCase(role.toString());
    }

    // Show edit page
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (!isAdmin(session)) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String staffId = request.getParameter("staffId");
        if (staffId == null || staffId.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing staffId");
            return;
        }

        try {
            Staff staff = dao.getStaffById(staffId);
            if (staff == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Staff not found");
                return;
            }

            request.setAttribute("staff", staff);
            request.getRequestDispatcher("editStaff.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Database error", e);
        }
    }

    // Save edit
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (!isAdmin(session)) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String staffId = request.getParameter("staff_id"); // hidden
        String name = request.getParameter("staff_name");
        String email = request.getParameter("staff_email");
        String password = request.getParameter("staff_password"); // optional
        String phone = request.getParameter("staff_phone");
        String role = request.getParameter("user_role");

        // ===== validation =====
        if (staffId == null || staffId.trim().isEmpty()
                || name == null || name.trim().isEmpty()
                || email == null || email.trim().isEmpty()
                || role == null || role.trim().isEmpty()) {

            request.setAttribute("error", "Please fill in all required fields (Name, Email, Role).");
            forwardBack(request, response, staffId);
            return;
        }

        if (!EMAIL_PATTERN.matcher(email.trim()).matches()) {
            request.setAttribute("error", "Invalid email format.");
            forwardBack(request, response, staffId);
            return;
        }

        if (!("admin".equalsIgnoreCase(role) || "staff".equalsIgnoreCase(role))) {
            request.setAttribute("error", "Invalid role selected.");
            forwardBack(request, response, staffId);
            return;
        }

        try {
            if (dao.emailExistsForOtherStaff(email.trim(), staffId.trim())) {
                request.setAttribute("error", "Email already exists for another staff.");
                forwardBack(request, response, staffId);
                return;
            }

            Staff updated = new Staff();
            updated.setStaffId(staffId.trim());
            updated.setStaffName(name.trim());
            updated.setStaffEmail(email.trim());
            updated.setStaffPhone(phone == null ? null : phone.trim());
            updated.setUserRole(role.trim().toLowerCase());

            // If password blank -> keep old password
            if (password != null && !password.trim().isEmpty()) {
                updated.setStaffPass(password.trim());
                dao.updateStaffWithPassword(updated);
            } else {
                dao.updateStaffNoPassword(updated);
            }

            response.sendRedirect(request.getContextPath() + "/Staff");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Failed to update staff.");
            forwardBack(request, response, staffId);
        }
    }

    private void forwardBack(HttpServletRequest request, HttpServletResponse response, String staffId)
            throws ServletException, IOException {
        try {
            if (staffId != null) {
                Staff staff = dao.getStaffById(staffId);
                request.setAttribute("staff", staff);
            }
        } catch (Exception ignored) {
        }
        request.getRequestDispatcher("editStaff.jsp").forward(request, response);
    }
}
