package model;

import java.time.LocalDate;

public class Stock {

    private String stockId;       // eg: ST001
    private String stockType;     // In | Out
    private int stockQty;
    private LocalDate stockDate;
    private String remark;        // User input, e.g., "PR Giveaway"
    private String varId;         // FK → Variation
    private String staffId;       // FK → Staff

    // Default constructor
    public Stock() {}

    // Full constructor
    public Stock(String stockId, String stockType, int stockQty, LocalDate stockDate, 
                 String remark, String varId, String staffId) {
        this.stockId = stockId;
        this.stockType = stockType;
        this.stockQty = stockQty;
        this.stockDate = stockDate;
        this.remark = remark;
        this.varId = varId;
        this.staffId = staffId;
    }

    // Getters
    public String getStockId() { return stockId; }
    public String getStockType() { return stockType; }
    public int getStockQty() { return stockQty; }
    public LocalDate getStockDate() { return stockDate; }
    public String getRemark() { return remark; }
    public String getVarId() { return varId; }
    public String getStaffId() { return staffId; }

    // Setters
    public void setStockId(String stockId) { this.stockId = stockId; }
    public void setStockType(String stockType) { this.stockType = stockType; }
    public void setStockQty(int stockQty) { this.stockQty = stockQty; }
    public void setStockDate(LocalDate stockDate) { this.stockDate = stockDate; }
    public void setRemark(String remark) { this.remark = remark; }
    public void setVarId(String varId) { this.varId = varId; }
    public void setStaffId(String staffId) { this.staffId = staffId; }
}
