package model;

import java.time.LocalDate;
import java.math.BigDecimal;

public class Variation {

    private String varId;         // eg: V001 
    private String varName;
    private String sku;           // SS-NN eg: ST-01 or WBT-01
    private BigDecimal varPrice;
    private BigDecimal sales;    
    private int qtyBefore;
    private int varQty;
    private int soldQty;
    private String varStatus;     // In-stock | Out-stock
    private String imgPath;
    private LocalDate createDate;
    private LocalDate lastUpdate;
    private String prodId;        // FK → Product

    // Default constructor
    public Variation() {}

    // Constructor with all fields
    public Variation(String varId, String varName, String sku, BigDecimal varPrice,
                     int qtyBefore, int varQty, int soldQty, String imgPath, String varStatus,
                     LocalDate createDate, LocalDate lastUpdate, String prodId) {
        this.varId = varId;
        this.varName = varName;
        this.sku = sku;
        this.varPrice = varPrice;
        this.qtyBefore = qtyBefore;
        this.varQty = varQty;
        this.soldQty = soldQty;
        this.imgPath = imgPath;
        this.varStatus = varStatus;
        this.createDate = createDate != null ? createDate : LocalDate.now();
        this.lastUpdate = lastUpdate != null ? lastUpdate : LocalDate.now();
        this.prodId = prodId;
    }

    // Getters
    public String getVarId() { return varId; }
    public String getVarName() { return varName; }
    public String getSku() { return sku; }
    public BigDecimal getVarPrice() { return varPrice; }
    public BigDecimal getSales() { return sales; }
    public int getQtyBefore() { return qtyBefore; }
    public int getVarQty() { return varQty; }
    public int getSoldQty() { return soldQty; }
    public String getVarStatus() { return varStatus; }
    public String getImgPath() { return imgPath; }
    public LocalDate getCreateDate() { return createDate; }
    public LocalDate getLastUpdate() { return lastUpdate; }
    public String getProdId() { return prodId; }

    // Setters
    public void setVarId(String varId) { this.varId = varId; }
    public void setVarName(String varName) { this.varName = varName; }
    public void setSku(String sku) { this.sku = sku; }
    public void setVarPrice(BigDecimal varPrice) { this.varPrice = varPrice; }
    public void setSales(BigDecimal sales) { this.sales = sales; }    
    public void setQtyBefore(int qtyBefore) { this.qtyBefore = qtyBefore; }
    public void setVarQty(int varQty) { this.varQty = varQty; }
    public void setSoldQty(int soldQty) { this.soldQty = soldQty; }
    public void setVarStatus(String varStatus) { this.varStatus = varStatus; }
    public void setImgPath(String imgPath) { this.imgPath = imgPath; }
    public void setCreateDate(LocalDate createDate) { this.createDate = createDate; }
    public void setLastUpdate(LocalDate lastUpdate) { this.lastUpdate = lastUpdate; }
    public void setProdId(String prodId) { this.prodId = prodId; }
}
