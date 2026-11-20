package dao;

import util.DBConnection;
import view.InventoryView;

import java.sql.*;
import java.time.LocalDate;

public class StockInProductDAO {

    // ===== READ (for stockInProduct.jsp) =====
    public InventoryView getStockEntry(String varId, Connection c) throws SQLException {

        String sql =
            "SELECT p.PROD_ID, p.PROD_NAME, p.PROD_CODE, p.CATEGORY, " +
            "       v.VAR_ID, v.SKU, v.VAR_NAME, v.VAR_QTY, v.VAR_STATUS " +
            "FROM PRODUCT p " +
            "JOIN VARIATION v ON p.PROD_ID = v.PROD_ID " +
            "WHERE v.VAR_ID = ?";

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, varId);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;

                InventoryView iv = new InventoryView();
                iv.setProdId(rs.getString("PROD_ID"));
                iv.setProdName(rs.getString("PROD_NAME"));
                iv.setProdCode(rs.getString("PROD_CODE"));
                iv.setCategory(rs.getString("CATEGORY"));
                iv.setVarId(rs.getString("VAR_ID"));
                iv.setSku(rs.getString("SKU"));
                iv.setVarName(rs.getString("VAR_NAME"));
                iv.setVarQty(rs.getInt("VAR_QTY"));
                iv.setVarStatus(rs.getString("VAR_STATUS"));
                return iv;
            }
        }
    }

    // ===== WRITE (Stock In) =====
    public void stockIn(String varId, int qtyIn, String remark, String staffId) throws SQLException {

        if (qtyIn <= 0) throw new SQLException("Stock In quantity must be > 0");

        try (Connection conn = DBConnection.createConnection()) {
            conn.setAutoCommit(false);

            try {
                int currentQty;
                String prodId;

                // 1️⃣ Read VARIATION
                String qVar = "SELECT VAR_QTY, PROD_ID FROM VARIATION WHERE VAR_ID = ?";
                try (PreparedStatement ps = conn.prepareStatement(qVar)) {
                    ps.setString(1, varId);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) throw new SQLException("VAR_ID not found");
                        currentQty = rs.getInt("VAR_QTY");
                        prodId = rs.getString("PROD_ID");
                    }
                }

                int newQty = currentQty + qtyIn;
                String newStatus = newQty > 0 ? "In-stock" : "Out-stock";

                // 2️⃣ Update VARIATION (TOTAL STOCK)
                String upVar =
                    "UPDATE VARIATION " +
                    "SET QTY_BEFORE = ?, VAR_QTY = ?, VAR_STATUS = ?, LAST_UPDATE = ? " +
                    "WHERE VAR_ID = ?";

                try (PreparedStatement ps = conn.prepareStatement(upVar)) {
                    ps.setInt(1, currentQty);
                    ps.setInt(2, newQty);
                    ps.setString(3, newStatus);
                    ps.setDate(4, Date.valueOf(LocalDate.now()));
                    ps.setString(5, varId);
                    ps.executeUpdate();
                }

                // 3️⃣ Update PRODUCT total
                String upProd =
                    "UPDATE PRODUCT " +
                    "SET TOTAL_QTY = (SELECT COALESCE(SUM(VAR_QTY),0) FROM VARIATION WHERE PROD_ID = ?), " +
                    "    PROD_STATUS = CASE WHEN TOTAL_QTY > 0 THEN 'In-stock' ELSE 'Out-stock' END, " +
                    "    LAST_UPDATE = ? " +
                    "WHERE PROD_ID = ?";

                try (PreparedStatement ps = conn.prepareStatement(upProd)) {
                    ps.setString(1, prodId);
                    ps.setDate(2, Date.valueOf(LocalDate.now()));
                    ps.setString(3, prodId);
                    ps.executeUpdate();
                }

                // 4️⃣ STOCK: overwrite STOCK_QTY with latest input
                String findStock =
                    "SELECT STOCK_ID FROM STOCK WHERE VAR_ID = ? AND STOCK_TYPE = 'In'";

                String stockId = null;

                try (PreparedStatement ps = conn.prepareStatement(findStock)) {
                    ps.setString(1, varId);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) stockId = rs.getString("STOCK_ID");
                    }
                }

                if (stockId != null) {
                    String upStock =
                        "UPDATE STOCK SET STOCK_QTY = ?, STOCK_DATE = ?, REMARK = ?, STAFF_ID = ? " +
                        "WHERE STOCK_ID = ?";

                    try (PreparedStatement ps = conn.prepareStatement(upStock)) {
                        ps.setInt(1, qtyIn); // overwrite
                        ps.setDate(2, Date.valueOf(LocalDate.now()));
                        ps.setString(3, remark);
                        ps.setString(4, staffId);
                        ps.setString(5, stockId);
                        ps.executeUpdate();
                    }
                } else {
                    String insStock =
                        "INSERT INTO STOCK VALUES (?, 'In', ?, ?, ?, ?, ?)";

                    try (PreparedStatement ps = conn.prepareStatement(insStock)) {
                        ps.setString(1, generateNextStockId(conn));
                        ps.setInt(2, qtyIn);
                        ps.setDate(3, Date.valueOf(LocalDate.now()));
                        ps.setString(4, remark);
                        ps.setString(5, varId);
                        ps.setString(6, staffId);
                        ps.executeUpdate();
                    }
                }
                
                // 5. Create the stock movement for the ledger
                String insLedger =
                    "INSERT INTO STOCK (STOCK_ID, STOCK_TYPE, STOCK_QTY, STOCK_DATE, REMARK, VAR_ID, STAFF_ID, ORDER_ID) " +
                    "VALUES (?, 'In', ?, ?, ?, ?, ?, NULL)";

                try (PreparedStatement psLedger = conn.prepareStatement(insLedger)) {
                    psLedger.setString(1, generateNextStockId(conn)); // new STOCK_ID
                    psLedger.setInt(2, qtyIn);                        // same as the qty just written
                    psLedger.setDate(3, Date.valueOf(LocalDate.now()));
                    psLedger.setString(4, remark);                    // your remark
                    psLedger.setString(5, varId);
                    psLedger.setString(6, staffId);
                    psLedger.executeUpdate();
                }              

                conn.commit();

            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    private String generateNextStockId(Connection conn) throws SQLException {
        String sql = "SELECT MAX(STOCK_ID) FROM STOCK WHERE STOCK_ID LIKE 'ST%'";
        int next = 1;
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next() && rs.getString(1) != null)
                next = Integer.parseInt(rs.getString(1).substring(2)) + 1;
        }
        return "ST" + String.format("%03d", next);
    }
}
