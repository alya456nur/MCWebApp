package test;

import dao.StaffDashboardDAO;
import dao.LedgerDAO;
import dao.InventoryDAO;
import dao.ManageOrderDAO;
import view.LedgerView;
import view.InventoryView;
import view.ManageOrderView;

import util.DBConnection;
import java.sql.Connection;
import java.util.List;

import java.util.List;

public class testDAO {

    public static void main(String[] args) {
        
        /*
        StaffDashboardDAO dao = new StaffDashboardDAO();

        System.out.println("Product count: " + dao.getProductCount());
        System.out.println("Total orders: " + dao.getTotalOrders());

        // Test fetching all products
        dao.getAllProducts().forEach(p -> {
            System.out.println("Product: " + p.getProdName() + " | Variations: " + p.getVariations().size());
        });
        */
        
        /*
        LedgerDAO dao = new LedgerDAO();

        List<StockEntry> entries = dao.getAllStockEntries();

        System.out.println("Stock entries loaded: " + entries.size());
        for (LedgerView entry : entries) {
            System.out.println(
                entry.getStockDate() + " | " +
                entry.getStaffName() + " | " +
                entry.getProdCode() + " | " +
                entry.getProdName() + " | " +
                entry.getVarName() + " | " +
                entry.getStockType() + " | " +
                entry.getStockQty() + " | " +
                entry.getQtyBefore() + " | " +
                entry.getVarQty() + " | " +
                entry.getRemark()
            );
        }
        */

        /*// InventoryDAO test
        InventoryDAO dao = new InventoryDAO();

        // Test summary helpers
        System.out.println("Product count: " + dao.getProductCount());
        System.out.println("Stock In count: " + dao.getStockInCount());
        System.out.println("Stock Out count: " + dao.getStockOutCount());
        System.out.println("Low stock count: " + dao.getLowStockCount());
        System.out.println("Inventory value: RM " + dao.getInventoryValue());

        // Fetch all inventory entries
        List<InventoryEntry> invEntries = dao.getAllInventoryEntries();

        System.out.println("Inventory entries loaded: " + invEntries.size());
        for (InventoryEntry entry : invEntries) {
            System.out.println(
                entry.getProdCode() + " | " +
                entry.getProdName() + " | " +
                entry.getVarName() + " | " +
                entry.getSku() + " | " +
                entry.getCategory() + " | " +
                entry.getVarPrice() + " | " +
                entry.getVarQty() + " | " +
                entry.getVarStatus() + " | " +
                entry.getCreateDate() + " | " +
                entry.getLastUpdate() + " | " +
                entry.getVarId()
            );
        }
        */

        /*// Initialize the DAO
        InventoryDAO dao = new InventoryDAO();

        // Fetch all stock movement entries
        List<StockMovementEntry> entries = dao.getAllStockMovementEntries();

        System.out.println("Stock movement entries loaded: " + entries.size());
        for (StockMovementEntry entry : entries) {
            System.out.println(
                entry.getVarName() + " | " +
                entry.getSku() + " | " +
                entry.getStockIn() + " | " +
                entry.getStockOut() + " | " +
                entry.getVarQty() + " | " +
                entry.getVarStatus() + " | " +
                entry.getVarId()
                
            );
        }

        // Optionally, test summary info
        int totalStockIn = entries.stream().mapToInt(StockMovementEntry::getStockIn).sum();
        int totalStockOut = entries.stream().mapToInt(StockMovementEntry::getStockOut).sum();
        System.out.println("Total stock in: " + totalStockIn);
        System.out.println("Total stock out: " + totalStockOut);

        */
        
    }
}
