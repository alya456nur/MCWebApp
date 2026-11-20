package view;

import java.math.BigDecimal;
import java.sql.Date;

public class CustOrderView {

    // Receipt info
    private String receiptId;
    private Date receiptDate;
    private String payType;
    private BigDecimal gst;
    private BigDecimal subTotal;
    private BigDecimal grandTotal;

    // Customer info
    private String custId;
    private String custNo;
    private String custName;

    // Order info
    private String orderId;
    private String orderNo;
    private String status;

    // Order item info
    private String orderItemId;
    private int prodQty;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;

    // Product info
    private String prodId;
    private String prodName;

    // Variation info
    private String varId;
    private String varName;
    private BigDecimal varPrice;

    public CustOrderView() {}

    // Full constructor
    public CustOrderView(
            String receiptId,
            Date receiptDate,
            String payType,
            BigDecimal gst,
            BigDecimal subTotal,
            BigDecimal grandTotal,
            String custId,
            String custNo,
            String custName,
            String orderId,
            String orderNo,
            String status,
            String orderItemId,
            int prodQty,
            BigDecimal unitPrice,
            BigDecimal totalPrice,
            String prodId,
            String prodName,
            String varId,
            String varName,
            BigDecimal varPrice
    ) {
        this.receiptId = receiptId;
        this.receiptDate = receiptDate;
        this.payType = payType;
        this.gst = gst;
        this.subTotal = subTotal;
        this.grandTotal = grandTotal;
        this.custId = custId;
        this.custNo = custNo;
        this.custName = custName;
        this.orderId = orderId;
        this.orderNo = orderNo;
        this.status = status;
        this.orderItemId = orderItemId;
        this.prodQty = prodQty;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
        this.prodId = prodId;
        this.prodName = prodName;
        this.varId = varId;
        this.varName = varName;
        this.varPrice = varPrice;
    }

    // Getters
    public String getReceiptId() { return receiptId; }
    public Date getReceiptDate() { return receiptDate; }
    public String getPayType() { return payType; }
    public BigDecimal getGst() { return gst; }
    public BigDecimal getSubTotal() { return subTotal; }
    public BigDecimal getGrandTotal() { return grandTotal; }

    public String getCustId() { return custId; }
    public String getCustNo() { return custNo; }
    public String getCustName() { return custName; }

    public String getOrderId() { return orderId; }
    public String getOrderNo() { return orderNo; }
    public String getStatus() { return status; }

    public String getOrderItemId() { return orderItemId; }
    public int getProdQty() { return prodQty; }
    public BigDecimal getUnitPrice() { return unitPrice; }
    public BigDecimal getTotalPrice() { return totalPrice; }

    public String getProdId() { return prodId; }
    public String getProdName() { return prodName; }

    public String getVarId() { return varId; }
    public String getVarName() { return varName; }
    public BigDecimal getVarPrice() { return varPrice; }

    // Setters
    public void setReceiptId(String receiptId) { this.receiptId = receiptId; }
    public void setReceiptDate(Date receiptDate) { this.receiptDate = receiptDate; }
    public void setPayType(String payType) { this.payType = payType; }
    public void setGst(BigDecimal gst) { this.gst = gst; }
    public void setSubTotal(BigDecimal subTotal) { this.subTotal = subTotal; }
    public void setGrandTotal(BigDecimal grandTotal) { this.grandTotal = grandTotal; }

    public void setCustId(String custId) { this.custId = custId; }
    public void setCustNo(String custNo) { this.custNo = custNo; }
    public void setCustName(String custName) { this.custName = custName; }

    public void setOrderId(String orderId) { this.orderId = orderId; }
    public void setOrderNo(String orderNo) { this.orderNo = orderNo; }
    public void setStatus(String status) { this.status = status; }

    public void setOrderItemId(String orderItemId) { this.orderItemId = orderItemId; }
    public void setProdQty(int prodQty) { this.prodQty = prodQty; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }
    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }

    public void setProdId(String prodId) { this.prodId = prodId; }
    public void setProdName(String prodName) { this.prodName = prodName; }

    public void setVarId(String varId) { this.varId = varId; }
    public void setVarName(String varName) { this.varName = varName; }
    public void setVarPrice(BigDecimal varPrice) { this.varPrice = varPrice; }
}
