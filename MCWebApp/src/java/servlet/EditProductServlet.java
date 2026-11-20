package servlet;

import dao.EditProductDAO;
import util.DBConnection;
import view.InventoryView;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;

@WebServlet("/EditProduct")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1MB
        maxFileSize = 10 * 1024 * 1024, // 10MB
        maxRequestSize = 20 * 1024 * 1024 // 20MB
)
public class EditProductServlet extends HttpServlet {

    private EditProductDAO dao;

    // ✅ Your required folder
    private static final String EXTERNAL_IMAGE_DIR
            = "C:/Users/user/Documents/DEGREE/SEM 4/CSC584/Group Project/images";

    @Override
    public void init() {
        dao = new EditProductDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String varId = request.getParameter("varId");
        if (varId == null || varId.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/Inventory");
            return;
        }

        try (Connection c = DBConnection.createConnection()) {
            InventoryView entry = dao.getEditProductEntry(varId, c);
            if (entry == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found");
                return;
            }
            request.setAttribute("product", entry);
            request.getRequestDispatcher("editInventory.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500, "DB error: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String varId = request.getParameter("varId");
        String prodId = request.getParameter("prodId");

        String prodName = request.getParameter("prodName");
        String prodCode = request.getParameter("prodCode");
        String category = request.getParameter("category");

        String sku = request.getParameter("sku");
        String varName = request.getParameter("varName");
        String varPriceStr = request.getParameter("varPrice");
        String varQtyStr = request.getParameter("varQty");
        String varStatus = request.getParameter("varStatus");

        String existingImgPath = request.getParameter("existingImgPath");

        // basic validation
        if (isBlank(varId) || isBlank(prodId) || isBlank(prodName) || isBlank(prodCode)
                || isBlank(category) || isBlank(sku) || isBlank(varPriceStr) || isBlank(varQtyStr) || isBlank(varStatus)) {
            request.setAttribute("error", "All required fields must be filled.");
            doGet(request, response);
            return;
        }

        BigDecimal varPrice;
        int varQty;
        try {
            varPrice = new BigDecimal(varPriceStr);
            varQty = Integer.parseInt(varQtyStr);
            if (varQty < 0) {
                throw new NumberFormatException("Negative qty");
            }
        } catch (Exception e) {
            request.setAttribute("error", "Invalid price/stock value.");
            doGet(request, response);
            return;
        }

        String newImgPath = null;

        // ✅ handle image upload
        Part imagePart = request.getPart("productImage");
        if (imagePart != null && imagePart.getSize() > 0) {

            String fileName = getSubmittedFileName(imagePart);
            if (fileName == null) {
                fileName = "upload.jpg";
            }

            String ext = getExt(fileName).toLowerCase();
            if (!(ext.equals(".jpg") || ext.equals(".jpeg") || ext.equals(".png") || ext.equals(".webp"))) {
                request.setAttribute("error", "Only JPG, PNG, WEBP allowed.");
                doGet(request, response);
                return;
            }

            // create a safe unique name
            String safeName = "prod_" + varId + "_" + System.currentTimeMillis() + ext;

            // 1) save to your external folder (C:\...\images)
            File externalDir = new File(EXTERNAL_IMAGE_DIR);
            if (!externalDir.exists()) {
                externalDir.mkdirs();
            }

            File externalFile = new File(externalDir, safeName);
            try (InputStream in = imagePart.getInputStream()) {
                Files.copy(in, externalFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }

            // 2) copy into webapp /assets/images so JSP can load it
            String webImagesRealPath = getServletContext().getRealPath("/assets/images");
            File webDir = new File(webImagesRealPath);
            if (!webDir.exists()) {
                webDir.mkdirs();
            }

            Path webTarget = new File(webDir, safeName).toPath();
            Files.copy(externalFile.toPath(), webTarget, StandardCopyOption.REPLACE_EXISTING);

            // DB should store web path
            newImgPath = "/assets/images/" + safeName;
        }

        // if no upload, keep old
        if (newImgPath == null) {
            newImgPath = existingImgPath;
        }

        try (Connection c = DBConnection.createConnection()) {
            c.setAutoCommit(false);
            try {
                dao.updateProductAndVariation(
                        c,
                        prodId, prodName, prodCode, category,
                        varId, sku, varName, varPrice, varQty, varStatus,
                        newImgPath
                );
                c.commit();

                response.sendRedirect(request.getContextPath() + "/EditProduct?varId=" + varId + "&success=1");

            } catch (Exception e) {
                c.rollback();
                throw e;
            } finally {
                c.setAutoCommit(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Update failed: " + e.getMessage());
            doGet(request, response);
        }
    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    private static String getExt(String fileName) {
        int dot = fileName.lastIndexOf('.');
        return dot >= 0 ? fileName.substring(dot) : "";
    }

    private static String getSubmittedFileName(Part part) {
        // works for most servlet containers
        String header = part.getHeader("content-disposition");
        if (header == null) {
            return null;
        }

        for (String cd : header.split(";")) {
            cd = cd.trim();
            if (cd.startsWith("filename")) {
                String name = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                // remove path from IE / Windows uploads
                return name.substring(name.lastIndexOf('/') + 1).substring(name.lastIndexOf('\\') + 1);
            }
        }
        return null;
    }
}
