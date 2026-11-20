package servlet;

import dao.RegisterDAO;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet("/Register")
public class RegisterServlet extends HttpServlet {

    private RegisterDAO dao;

    @Override
    public void init() {
        dao = new RegisterDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Simply show signup page
        request.getRequestDispatcher("signUp.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get form parameters
        String custName = request.getParameter("name");
        String email    = request.getParameter("email");
        String password = request.getParameter("pass");
        String address  = request.getParameter("address");

        // Validate all fields
        if (custName == null || custName.isEmpty() ||
            email == null || email.isEmpty() ||
            password == null || password.isEmpty() ||
            address == null || address.isEmpty()) {

            request.setAttribute("error", "All fields are required.");
            request.getRequestDispatcher("signUp.jsp").forward(request, response);
            return;
        }

        // Register
        boolean success = dao.registerCustomer(custName, email, password, address);

        if (success) {
            // Redirect to login page on success
            response.sendRedirect(request.getContextPath() + "/login.jsp?msg=registered");
    
        } else {
            // Forward back with error
            request.setAttribute("error", "Registration failed. Email may already exist.");
            request.getRequestDispatcher("signUp.jsp").forward(request, response);

        }
    }
}
