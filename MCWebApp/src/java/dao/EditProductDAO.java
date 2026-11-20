package dao;

import util.DBConnection;
import view.InventoryView;

import java.math.BigDecimal;
import java.sql.*;

public class EditProductDAO {

    public InventoryView getEditProductEntry(String varId, Connection c) {
        String sql
                = "SELECT p.PROD_ID, p.PROD_NAME, p.PROD_CODE, p.CATEGORY, "
                + "       v.VAR_ID, v.IMG_PATH, v.SKU, v.VAR_NAME, v.VAR_PRICE, v.VAR_QTY, v.VAR_STATUS "
                + "FROM PRODUCT p "
                + "JOIN VARIATION v ON p.PROD_ID = v.PROD_ID "
                + "WHERE v.VAR_ID = ?";

        if (c == null) {
            return null;
        }

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, varId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    InventoryView entry = new InventoryView();

                    entry.setProdId(rs.getString("PROD_ID"));
                    entry.setProdName(rs.getString("PROD_NAME"));
                    entry.setProdCode(rs.getString("PROD_CODE"));
                    entry.setCategory(rs.getString("CATEGORY"));

                    entry.setVarId(rs.getString("VAR_ID"));
                    entry.setSku(rs.getString("SKU"));
                    entry.setVarName(rs.getString("VAR_NAME"));
                    entry.setVarPrice(rs.getBigDecimal("VAR_PRICE"));
                    entry.setVarQty(rs.getInt("VAR_QTY"));
                    entry.setVarStatus(rs.getString("VAR_STATUS"));

                    String img = rs.getString("IMG_PATH");
                    entry.setImgPath(img == null ? "/assets/images/default.png" : img);

                    return entry;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateProductAndVariation(Connection c,
            String prodId,
            String prodName,
            String prodCode,
            String category,
            String varId,
            String sku,
            String varName,
            BigDecimal varPrice,
            int varQty,
            String varStatus,
            String newImgPathOrNull) throws SQLException {

        // update PRODUCT
        String upProd
                = "UPDATE PRODUCT SET PROD_NAME = ?, PROD_CODE = ?, CATEGORY = ?, LAST_UPDATE = CURRENT_DATE "
                + "WHERE PROD_ID = ?";

        try (PreparedStatement ps = c.prepareStatement(upProd)) {
            ps.setString(1, prodName);
            ps.setString(2, prodCode);
            ps.setString(3, category);
            ps.setString(4, prodId);
            ps.executeUpdate();
        }

        // update VARIATION (img optional)
        String upVar;
        if (newImgPathOrNull != null && !newImgPathOrNull.trim().isEmpty()) {
            upVar
                    = "UPDATE VARIATION SET SKU=?, VAR_NAME=?, VAR_PRICE=?, VAR_QTY=?, VAR_STATUS=?, IMG_PATH=?, LAST_UPDATE=CURRENT_DATE "
                    + "WHERE VAR_ID=?";
        } else {
            upVar
                    = "UPDATE VARIATION SET SKU=?, VAR_NAME=?, VAR_PRICE=?, VAR_QTY=?, VAR_STATUS=?, LAST_UPDATE=CURRENT_DATE "
                    + "WHERE VAR_ID=?";
        }

        try (PreparedStatement ps = c.prepareStatement(upVar)) {
            int i = 1;
            ps.setString(i++, sku);
            ps.setString(i++, varName);
            ps.setBigDecimal(i++, varPrice);
            ps.setInt(i++, varQty);
            ps.setString(i++, varStatus);

            if (newImgPathOrNull != null && !newImgPathOrNull.trim().isEmpty()) {
                ps.setString(i++, newImgPathOrNull); // stored path like /assets/images/xxx.jpg
            }

            ps.setString(i, varId);
            ps.executeUpdate();
        }

        // optional: keep PRODUCT.TOTAL_QTY in sync (sum VARIATION)
        String sync
                = "UPDATE PRODUCT p "
                + "SET TOTAL_QTY = (SELECT COALESCE(SUM(VAR_QTY),0) FROM VARIATION v WHERE v.PROD_ID = p.PROD_ID) "
                + "WHERE p.PROD_ID = ?";
        try (PreparedStatement ps = c.prepareStatement(sync)) {
            ps.setString(1, prodId);
            ps.executeUpdate();
        }
    }
}
