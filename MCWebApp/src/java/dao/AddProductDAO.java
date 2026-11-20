package dao;

import util.DBConnection;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;

public class AddProductDAO {

    public boolean addProduct(
            String prodCode,
            String prodName,
            String category,
            String varName,
            String sku,
            BigDecimal varPrice,
            int varQty,
            String imgPath,
            String staffId
    ) throws SQLException {

        try (Connection conn = DBConnection.createConnection()) {
            conn.setAutoCommit(false);

            try {
                String prodId  = generateNextId(conn, "PRODUCT", "PROD_ID", "P", 3);
                String varId   = generateNextId(conn, "VARIATION", "VAR_ID", "V", 3);
                String stockId = generateNextId(conn, "STOCK", "STOCK_ID", "ST", 3);

                String prodStatus = (varQty > 0) ? "In-stock" : "Out-stock";
                String varStatus  = (varQty > 0) ? "In-stock" : "Out-stock";

                // 1️⃣ PRODUCT
                String productSQL =
                        "INSERT INTO PRODUCT VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

                try (PreparedStatement ps = conn.prepareStatement(productSQL)) {
                    ps.setString(1, prodId);
                    ps.setString(2, prodCode);
                    ps.setString(3, prodName);
                    ps.setString(4, category);
                    ps.setInt(5, varQty);
                    ps.setString(6, prodStatus);
                    ps.setString(7, imgPath);
                    ps.setDate(8, Date.valueOf(LocalDate.now()));
                    ps.setDate(9, Date.valueOf(LocalDate.now()));
                    ps.executeUpdate();
                }

                // 2️⃣ VARIATION
                String varSQL =
                        "INSERT INTO VARIATION VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                try (PreparedStatement ps = conn.prepareStatement(varSQL)) {
                    ps.setString(1, varId);
                    ps.setString(2, varName);
                    ps.setString(3, sku);
                    ps.setBigDecimal(4, varPrice);
                    ps.setInt(5, 0);          // QTY_BEFORE
                    ps.setInt(6, varQty);     // VAR_QTY
                    ps.setInt(7, 0);          // SOLD_QTY
                    ps.setString(8, varStatus);
                    ps.setString(9, imgPath);
                    ps.setDate(10, Date.valueOf(LocalDate.now()));
                    ps.setDate(11, Date.valueOf(LocalDate.now()));
                    ps.setString(12, prodId);
                    ps.executeUpdate();
                }

                // 3️⃣ STOCK (Initial IN)
                String stockSQL =
                        "INSERT INTO STOCK VALUES (?, ?, ?, ?, ?, ?, ?)";

                try (PreparedStatement ps = conn.prepareStatement(stockSQL)) {
                    ps.setString(1, stockId);
                    ps.setString(2, "In"); // 🔥 MATCH YOUR DATA
                    ps.setInt(3, varQty);
                    ps.setDate(4, Date.valueOf(LocalDate.now()));
                    ps.setString(5, "Initial stock");
                    ps.setString(6, varId);
                    ps.setString(7, staffId);
                    ps.executeUpdate();
                }

                conn.commit();
                return true;

            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }

    private String generateNextId(Connection conn, String table, String col,
                                  String prefix, int digits) throws SQLException {

        String sql = "SELECT MAX(" + col + ") FROM " + table + " WHERE " + col + " LIKE ?";
        int next = 1;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, prefix + "%");
            ResultSet rs = ps.executeQuery();
            if (rs.next() && rs.getString(1) != null) {
                next = Integer.parseInt(rs.getString(1).substring(prefix.length())) + 1;
            }
        }
        return prefix + String.format("%0" + digits + "d", next);
    }
}
