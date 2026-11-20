package view;

import java.math.BigDecimal;
import java.util.List;
import model.Variation;

public class CustDashboardView {

    private String imgPath;
    private String prodId;
    private String prodName;
    private String varName;
    private BigDecimal varPrice;
    private String varId;
    private String sku;
    private String category;
    private List<Variation> variations;

    // Default constructor
    public CustDashboardView() {}

    // Full constructor
    public CustDashboardView(String imgPath, String prodName, String varName, BigDecimal varPrice, 
                        String varId, String sku, String category) {
        this.imgPath = imgPath;
        this.prodName = prodName;
        this.varName = varName;
        this.varPrice = varPrice;
        this.varId = varId;
        this.sku = sku;
        this.category = category;
    }

    // Getters
    public String getImgPath() { return imgPath; }
    public String getProdId() { return prodId; }
    public String getProdName() { return prodName; }
    public String getVarName() { return varName; }
    public BigDecimal getVarPrice() { return varPrice; }
    public String getVarId() { return varId; }
    public String getSku() { return sku; }
    public String getCategory() { return category; }
    public List<Variation> getVariations() { return variations; }
    
    // Setters
    public void setImgPath(String imgPath) { this.imgPath = imgPath; }
    public void setProdId(String prodId) { this.prodId = prodId; } 
    public void setProdName(String prodName) { this.prodName = prodName; }
    public void setVarName(String varName) { this.varName = varName; }
    public void setVarPrice(BigDecimal varPrice) { this.varPrice = varPrice; }
    public void setVarId(String varId) { this.varId = varId; }
    public void setSku(String sku) { this.sku = sku; }
    public void setCategory(String category) { this.category = category; }
    public void setVariations(List<Variation> variations) { this.variations = variations; }

}
