package view;

import java.math.BigDecimal;

public class CartView {

    private String imgPath;     // VARIATION.IMG_PATH
    private String prodName;    // PRODUCT.PROD_NAME
    private String varName;     // VARIATION.VAR_NAME
    private int prodQty;        // ORDER_VAR.PROD_QTY
    private BigDecimal totalPrice; // ORDER_VAR.TOTAL_PRICE
    private String orderId;     // ORDERS.ORDER_ID
    private String varId;       // VARIATION.VAR_ID
    private String orderVarId;      // ORDERVAR.ORDERVAR_ID
    private String custId;      // ORDERVAR.CUST_ID

    public CartView() {}

    public CartView(String imgPath, String prodName, String varName, int prodQty,
                    BigDecimal totalPrice, String orderId, String varId, String orderVarId, String custId) {
        this.imgPath = imgPath;
        this.prodName = prodName;
        this.varName = varName;
        this.prodQty = prodQty;
        this.totalPrice = totalPrice;
        this.orderId = orderId;
        this.varId = varId;
        this.orderVarId = orderVarId;
        this.custId = custId;
    }

    // Getters
    public String getImgPath() { return imgPath; }
    public String getProdName() { return prodName; }
    public String getVarName() { return varName; }
    public int getProdQty() { return prodQty; }
    public BigDecimal getTotalPrice() { return totalPrice; }
    public String getOrderId() { return orderId; }
    public String getVarId() { return varId; }
    public String getOrderVarId() { return orderVarId; }
    public String getCustId() { return custId; }

    // Setters
    public void setImgPath(String imgPath) { this.imgPath = imgPath; }
    public void setProdName(String prodName) { this.prodName = prodName; }
    public void setVarName(String varName) { this.varName = varName; }
    public void setProdQty(int prodQty) { this.prodQty = prodQty; }
    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }
    public void setOrderId(String orderId) { this.orderId = orderId; }
    public void setVarId(String varId) { this.varId = varId; }
    public void setOrderVarId(String orderVarId) { this.orderVarId = orderVarId; }
    public void setCustId(String custId) { this.custId = custId; }
}
