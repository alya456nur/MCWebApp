// Custom model class is only to create object for display purpose.
package view;

import java.time.LocalDate;

public class LedgerView {

    private LocalDate stockDate;   // STOCK_DATE
    private String stockType;      // STOCK_TYPE (In / Out)
    private int stockQty;          // STOCK_QTY
    private int qtyBefore;         // QTY_BEFORE
    private int varQty;            // VAR_QTY (updated qty)
    private String staffName;       // STAFF.STAFF_NAME
    private String remark;         // Remark
    private String prodCode;       // PRODUCT.PROD_CODE
    private String prodName;       // PRODUCT.PROD_NAME
    private String varName;        // VARIATION.VAR_NAME
    private String orderId;      // ORDER ID
    
    
    // Default constructor
    public LedgerView() {}

    // Full constructor
    public LedgerView(LocalDate stockDate, String stockType, int stockQty, int qtyBefore,
                      int varQty, String staffName, String remark, String prodCode, String prodName, String varName) {
        this.stockDate = stockDate;
        this.stockType = stockType;
        this.stockQty = stockQty;
        this.qtyBefore = qtyBefore;
        this.varQty = varQty;
        this.remark = staffName;
        this.remark = remark;
        this.prodCode = prodCode;
        this.prodName = prodName;
        this.varName = varName;
    }

    // Getters
    public LocalDate getStockDate() { return stockDate; }
    public String getStockType() { return stockType; }
    public int getStockQty() { return stockQty; }
    public int getQtyBefore() { return qtyBefore; }
    public int getVarQty() { return varQty; }
    public String getStaffName() { return staffName; }
    public String getRemark() { return remark; }
    public String getProdCode() { return prodCode; }
    public String getProdName() { return prodName; }
    public String getVarName() { return varName; }
    public String getOrderId() { return orderId; }

    // Setters
    public void setStockDate(LocalDate stockDate) { this.stockDate = stockDate; }
    public void setStockType(String stockType) { this.stockType = stockType; }
    public void setStockQty(int stockQty) { this.stockQty = stockQty; }
    public void setQtyBefore(int qtyBefore) { this.qtyBefore = qtyBefore; }
    public void setVarQty(int varQty) { this.varQty = varQty; }
    public void setStaffName(String staffName) { this.staffName = staffName; }
    public void setRemark(String remark) { this.remark = remark; }
    public void setProdCode(String prodCode) { this.prodCode = prodCode; }
    public void setProdName(String prodName) { this.prodName = prodName; }
    public void setVarName(String varName) { this.varName = varName; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

}
