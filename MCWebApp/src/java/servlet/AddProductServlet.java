package servlet;

import dao.AddProductDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.sql.SQLException;

@WebServlet("/AddProduct")
@MultipartConfig(
        location = "C:/Users/user/Documents/DEGREE/SEM 4/CSC584/Group Project/images",
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 5 * 1024 * 1024,
        maxRequestSize = 10 * 1024 * 1024
)
public class AddProductServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("STAFF_ID") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String staffId = (String) session.getAttribute("STAFF_ID");

        try {
            String prodName = request.getParameter("prodName");
            String prodCode = request.getParameter("prodCode");
            String sku = request.getParameter("sku");
            String category = request.getParameter("category");
            String varName = request.getParameter("varName");

            BigDecimal varPrice = new BigDecimal(request.getParameter("varPrice"));
            int varQty = Integer.parseInt(request.getParameter("varQty"));

            // Image upload
            Part imagePart = request.getPart("imgPath");
            String imgPath = null;

            if (imagePart != null && imagePart.getSize() > 0) {
                String fileName = Paths.get(imagePart.getSubmittedFileName())
                        .getFileName()
                        .toString();

                String safeName = System.currentTimeMillis() + "_" + fileName;
                imagePart.write(safeName);

                imgPath = "/images/" + safeName;
            }

            AddProductDAO dao = new AddProductDAO();
            dao.addProduct(
                    prodCode,
                    prodName,
                    category,
                    varName,
                    sku,
                    varPrice,
                    varQty,
                    imgPath,
                    staffId        // 🔥 NEW
            );

            response.sendRedirect(request.getContextPath() + "/Inventory");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Failed to add product.");
            request.getRequestDispatcher("/Inventory").forward(request, response);
        }
    }
}
