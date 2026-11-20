package dao;

import view.CustDashboardView;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Variation;

public class CustDashboardDAO {

    // 1. Get all variations with category 'Eyes'
    public List<CustDashboardView> getEyesVariations() {
        List<CustDashboardView> list = new ArrayList<>();
        String sql = "SELECT v.IMG_PATH, p.PROD_NAME, v.VAR_NAME, v.VAR_PRICE, v.VAR_ID, v.SKU, p.CATEGORY " +
                     "FROM VARIATION v " +
                     "JOIN PRODUCT p ON v.PROD_ID = p.PROD_ID " +
                     "WHERE p.CATEGORY = 'Eyes'";

        try (Connection c = DBConnection.createConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                CustDashboardView cv = new CustDashboardView();
                cv.setImgPath(rs.getString("IMG_PATH"));
                cv.setProdName(rs.getString("PROD_NAME"));
                cv.setVarName(rs.getString("VAR_NAME"));
                cv.setVarPrice(rs.getBigDecimal("VAR_PRICE"));
                cv.setVarId(rs.getString("VAR_ID"));
                cv.setSku(rs.getString("SKU"));
                cv.setCategory(rs.getString("CATEGORY"));

                list.add(cv);
            }

        } catch (SQLException e) {
            System.err.println("ERROR fetching Eyes variations");
            e.printStackTrace();
        }

        return list;
    }

    // Get all products with category 'Lips', and their variations
    public List<CustDashboardView> getLipsVariations() {
        List<CustDashboardView> products = new ArrayList<>();
        String sql = "SELECT PROD_ID, PROD_NAME, CATEGORY, IMG_PATH FROM PRODUCT WHERE CATEGORY = 'Lips'";

        try (Connection c = DBConnection.createConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                CustDashboardView product = new CustDashboardView();
                String prodId = rs.getString("PROD_ID");
                product.setProdId(prodId);
                product.setProdName(rs.getString("PROD_NAME"));
                product.setCategory(rs.getString("CATEGORY"));
                product.setImgPath(rs.getString("IMG_PATH"));

                // Fetch variations using the separate model.Variation class
                List<Variation> vars = getVariationsByProductId(prodId, c);
                product.setVariations(vars);

                products.add(product);
            }

        } catch (SQLException e) {
            System.err.println("ERROR fetching Lips products");
            e.printStackTrace();
        }

        return products;
    }

    // Fetch all variations for a given product ID
    private List<Variation> getVariationsByProductId(String prodId, Connection c) {
        List<Variation> list = new ArrayList<>();
        String sql = "SELECT VAR_ID, VAR_NAME, SKU, VAR_PRICE FROM VARIATION WHERE PROD_ID = ?";

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, prodId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Variation var = new Variation();
                    var.setVarId(rs.getString("VAR_ID"));
                    var.setVarName(rs.getString("VAR_NAME"));
                    var.setSku(rs.getString("SKU"));
                    var.setVarPrice(rs.getBigDecimal("VAR_PRICE"));
                    list.add(var);
                }
            }
        } catch (SQLException e) {
            System.err.println("ERROR fetching variations for product ID " + prodId);
            e.printStackTrace();
        }

        return list;
    }

}
