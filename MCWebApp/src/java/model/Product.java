package model;

import java.time.LocalDate;
import java.util.List;

public class Product {

    private String prodId;           // eg: P001
    private String prodCode;         // eg: ST | CBS | WBT
    private String prodName;
    private String category;         // eg: Eyes | Lips
    private int totalQty;
    private String prodStatus;       // eg: In-Stock | Out-of-stock
    private String imgPath;
    private LocalDate createDate;    // YYYY-MM-DD
    private LocalDate lastUpdate;
    private List<Variation> variations;

    // Default constructor
    public Product() {}

    // Full constructor
    public Product(String prodId, String prodCode, String prodName, String category,
                   int totalQty, String prodStatus, String imgPath,
                   LocalDate createDate, LocalDate lastUpdate) {
        this.prodId = prodId;
        this.prodCode = prodCode;
        this.prodName = prodName;
        this.category = category;
        this.totalQty = totalQty;
        this.prodStatus = prodStatus;
        this.imgPath = imgPath;
        this.createDate = createDate;
        this.lastUpdate = lastUpdate;
    }

    // Getters
    public String getProdId() { return prodId; }
    public String getProdCode() { return prodCode; }
    public String getProdName() { return prodName; }
    public String getCategory() { return category; }
    public int getTotalQty() { return totalQty; }
    public String getProdStatus() { return prodStatus; }
    public String getImgPath() { return imgPath; }
    public LocalDate getCreateDate() { return createDate; }
    public LocalDate getLastUpdate() { return lastUpdate; }
    public List<Variation> getVariations() { return variations; }

    // Setters
    public void setProdId(String prodId) { this.prodId = prodId; }
    public void setProdCode(String prodCode) { this.prodCode = prodCode; }
    public void setProdName(String prodName) { this.prodName = prodName; }
    public void setCategory(String category) { this.category = category; }
    public void setTotalQty(int totalQty) { this.totalQty = totalQty; }
    public void setProdStatus(String prodStatus) { this.prodStatus = prodStatus; }
    public void setImgPath(String imgPath) { this.imgPath = imgPath; }
    public void setCreateDate(LocalDate createDate) { this.createDate = createDate; }
    public void setLastUpdate(LocalDate lastUpdate) { this.lastUpdate = lastUpdate; }
    public void setVariations(List<Variation> variations) { this.variations = variations; }
}
