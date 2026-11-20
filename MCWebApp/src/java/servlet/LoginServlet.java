package servlet;

import dao.UserDAO;
import model.Staff;
import model.Customer;
import util.DBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("pass");

        try (Connection conn = DBConnection.createConnection()) {
            if (conn == null) {
                throw new SQLException("DBConnection returned null connection");
            }

            UserDAO dao = new UserDAO(conn);

            HttpSession session = request.getSession(true); // create session if not exists

            // STAFF LOGIN
            Staff staff = dao.getStaffByEmailAndPassword(email, password);
            if (staff != null) {
                session.setAttribute("STAFF_ID", staff.getStaffId());

                // ✅ store BOTH (use whichever you prefer later)
                session.setAttribute("role", "staff"); // general type
                session.setAttribute("USER_ROLE", staff.getUserRole()); // "admin" or "staff"

                session.setMaxInactiveInterval(30 * 60); // 30 mins
                response.sendRedirect(request.getContextPath() + "/StaffDashboard");
                return;
            }

            // CUSTOMER LOGIN
            Customer customer = dao.getCustomerByEmailAndPassword(email, password);
            if (customer != null) {
                session.setAttribute("CUST_ID", customer.getCustId());
                session.setAttribute("role", "customer");
                session.setMaxInactiveInterval(30 * 60); // 30 mins
                response.sendRedirect(request.getContextPath() + "/CustDashboard");
                return;
            }

            // INVALID LOGIN
            request.setAttribute("error", "Invalid email or password.");
            request.getRequestDispatcher("login.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Database error: " + e.getMessage());
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("login.jsp");
    }
}
