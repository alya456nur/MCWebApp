package view;

import java.math.BigDecimal;
import java.util.List;

public class ReceiptView {

    // ===== Receipt / Customer info =====
    private String receiptId;
    private String receiptDate;
    private String payType;
    private BigDecimal grandTotal;

    private String orderNo;
    private String custId;
    private String custNo;
    private String custName;

    // ===== Order / Item info =====
    private String orderId;
    private String varId;
    private String prodId;
    private String prodName;
    private String varName;
    private int prodQty;
    private BigDecimal varPrice;   // optional for shopping history
    private BigDecimal totalPrice;
    private String status;
    private BigDecimal subTotal;
    private BigDecimal gst;
    private String sku;
    private String imgPath;
    private String orderVarId;

    // ===== Nested items for reuse =====
    private List<ReceiptView> orderItems;

    public ReceiptView() {}

    // Full constructor
    public ReceiptView(String receiptId, String receiptDate, String payType, BigDecimal grandTotal,
                       String orderNo, String custId, String custNo, String custName,
                       String orderId, String varId, String prodId, String prodName, String varName,
                       int prodQty, BigDecimal varPrice, BigDecimal totalPrice, String status,
                       BigDecimal subTotal, BigDecimal gst, String sku, List<ReceiptView> orderItems) {

        this.receiptId = receiptId;
        this.receiptDate = receiptDate;
        this.payType = payType;
        this.grandTotal = grandTotal;
        this.orderNo = orderNo;
        this.custId = custId;
        this.custNo = custNo;
        this.custName = custName;

        this.orderId = orderId;
        this.varId = varId;
        this.prodId = prodId;
        this.prodName = prodName;
        this.varName = varName;
        this.prodQty = prodQty;
        this.varPrice = varPrice;
        this.totalPrice = totalPrice;
        this.status = status;
        this.subTotal = subTotal;
        this.gst = gst;
        this.sku = sku;

        this.orderItems = orderItems;
    }

    // ===== Getters =====
    public String getReceiptId() { return receiptId; }
    public String getReceiptDate() { return receiptDate; }
    public String getPayType() { return payType; }
    public BigDecimal getGrandTotal() { return grandTotal; }

    public String getOrderNo() { return orderNo; }
    public String getCustId() { return custId; }
    public String getCustNo() { return custNo; }
    public String getCustName() { return custName; }

    public String getOrderId() { return orderId; }
    public String getVarId() { return varId; }
    public String getProdId() { return prodId; }
    public String getProdName() { return prodName; }
    public String getVarName() { return varName; }
    public int getProdQty() { return prodQty; }
    public BigDecimal getVarPrice() { return varPrice; }
    public BigDecimal getTotalPrice() { return totalPrice; }
    public String getStatus() { return status; }
    public BigDecimal getSubTotal() { return subTotal; }
    public BigDecimal getGst() { return gst; }
    public String getSku() { return sku; }
    public String getImgPath() { return imgPath; } 
    public String getOrderVarId() { return orderVarId; }    

    public List<ReceiptView> getOrderItems() { return orderItems; }

    // ===== Setters =====
    public void setReceiptId(String receiptId) { this.receiptId = receiptId; }
    public void setReceiptDate(String receiptDate) { this.receiptDate = receiptDate; }
    public void setPayType(String payType) { this.payType = payType; }
    public void setGrandTotal(BigDecimal grandTotal) { this.grandTotal = grandTotal; }

    public void setOrderNo(String orderNo) { this.orderNo = orderNo; }
    public void setCustId(String custId) { this.custId = custId; }
    public void setCustNo(String custNo) { this.custNo = custNo; }
    public void setCustName(String custName) { this.custName = custName; }

    public void setOrderId(String orderId) { this.orderId = orderId; }
    public void setVarId(String varId) { this.varId = varId; }
    public void setProdId(String prodId) { this.prodId = prodId; }
    public void setProdName(String prodName) { this.prodName = prodName; }
    public void setVarName(String varName) { this.varName = varName; }
    public void setProdQty(int prodQty) { this.prodQty = prodQty; }
    public void setVarPrice(BigDecimal varPrice) { this.varPrice = varPrice; }
    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }
    public void setStatus(String status) { this.status = status; }
    public void setSubTotal(BigDecimal subTotal) { this.subTotal = subTotal; }
    public void setGst(BigDecimal gst) { this.gst = gst; }
    public void setSku(String sku) { this.sku = sku; }
    public void setImgPath(String imgPath) { this.imgPath = imgPath; }
    public void setOrderVarId(String orderVarId) { this.orderVarId = orderVarId; }
    public void setOrderItems(List<ReceiptView> orderItems) { this.orderItems = orderItems; }

}
