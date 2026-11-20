package servlet;

import dao.StaffDAO;
import model.Staff;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.List;

@WebServlet("/Staff")
public class StaffServlet extends HttpServlet {

    private StaffDAO dao;

    @Override
    public void init() {
        dao = new StaffDAO();
    }

    private boolean isAdmin(HttpSession session) {
        if (session == null) {
            return false;
        }
        Object staffId = session.getAttribute("STAFF_ID");
        Object role = session.getAttribute("USER_ROLE"); // "admin" or "staff"
        return staffId != null && role != null && "admin".equalsIgnoreCase(role.toString());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        // Must login
        if (session == null || session.getAttribute("STAFF_ID") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // Admin-only staff management page
        if (!isAdmin(session)) {
            // Forward to a JSP that shows the modal, then redirects only after OK/Close
            request.setAttribute("denyTitle", "Access Denied");
            request.setAttribute("denyMsg", "You can’t open the Staff section because it is for Admin role only.");
            RequestDispatcher rd = request.getRequestDispatcher("/staff_access_denied.jsp");
            rd.forward(request, response);
            return;
        }

        // All staff
        List<Staff> staff = dao.getAllStaff();
        request.setAttribute("staff", staff);

        RequestDispatcher rd = request.getRequestDispatcher("staff.jsp");
        rd.forward(request, response);
    }
}
