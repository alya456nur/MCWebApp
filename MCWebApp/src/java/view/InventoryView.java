package view;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * View model for Inventory-related pages.
 * Read-only / display-only
 */
public class InventoryView {

    // ===== Product fields =====
    private String prodId;
    private String prodCode;
    private String prodName;
    private String category;
    private String imgPath;

    // ===== Variation fields =====
    private String varId;
    private String sku;
    private String varName;
    private BigDecimal varPrice;
    private int varQty;
    private String varStatus;

    // ===== Inventory dates =====
    private LocalDate createDate;
    private LocalDate lastUpdate;

    // ===== Stock movement summary =====
    private int stockIn;
    private String stockType;
    private int stockOut;
    private int stockQty;
    private String remark;

    // ===== Constructors =====
    public InventoryView() {}

    // ===== Getters =====
    public String getProdId() { return prodId; }
    public String getProdCode() { return prodCode; }
    public String getProdName() { return prodName; }
    public String getCategory() { return category; }
    public String getImgPath() { return imgPath; }

    public String getVarId() { return varId; }
    public String getSku() { return sku; }
    public String getVarName() { return varName; }
    public BigDecimal getVarPrice() { return varPrice; }
    public int getVarQty() { return varQty; }
    public String getVarStatus() { return varStatus; }

    public LocalDate getCreateDate() { return createDate; }
    public LocalDate getLastUpdate() { return lastUpdate; }

    public int getStockIn() { return stockIn; }
    public String getStockType() { return stockType; }
    public int getStockOut() { return stockOut; }
    public int getStockQty() { return stockQty; }
    public String getRemark() { return remark; }

    // ===== Setters =====
    public void setProdId(String prodId) { this.prodId = prodId; }
    public void setProdCode(String prodCode) { this.prodCode = prodCode; }
    public void setProdName(String prodName) { this.prodName = prodName; }
    public void setCategory(String category) { this.category = category; }
    public void setImgPath(String imgPath) { this.imgPath = imgPath; }

    public void setVarId(String varId) { this.varId = varId; }
    public void setSku(String sku) { this.sku = sku; }
    public void setVarName(String varName) { this.varName = varName; }
    public void setVarPrice(BigDecimal varPrice) { this.varPrice = varPrice; }
    public void setVarQty(int varQty) { this.varQty = varQty; }
    public void setVarStatus(String varStatus) { this.varStatus = varStatus; }

    public void setCreateDate(LocalDate createDate) { this.createDate = createDate; }
    public void setLastUpdate(LocalDate lastUpdate) { this.lastUpdate = lastUpdate; }

    public void setStockIn(int stockIn) { this.stockIn = stockIn; }
    public void setStockType(String stockType) { this.stockType = stockType; }
    public void setStockOut(int stockOut) { this.stockOut = stockOut; }
    public void setStockQty(int stockQty) { this.stockQty = stockQty; }
    public void setRemark(String remark) { this.remark = remark; }
}
