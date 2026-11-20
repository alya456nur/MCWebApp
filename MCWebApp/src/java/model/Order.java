package model;

import java.math.BigDecimal;

public class Order {

    private String orderId;      // eg: O001
    private String orderNo;
    private String custNo;       // Just readable customer number, not FK
    private BigDecimal subTotal; // Sum of all total_price in OrderVar
    private String status;
    private String custId;       // FK → Customer

    // Default constructor
    public Order() {}

    // Full constructor
    public Order(String orderId, String orderNo, String custNo, BigDecimal subTotal, String status, String custId) {
        this.orderId = orderId;
        this.orderNo = orderNo;
        this.custNo = custNo;
        this.subTotal = subTotal;
        this.status = status;
        this.custId = custId;
    }

    // Getters
    public String getOrderId() { return orderId; }
    public String getOrderNo() { return orderNo; }
    public String getCustNo() { return custNo; }
    public BigDecimal getSubTotal() { return subTotal; }
    public String getStatus() { return status; }
    public String getCustId() { return custId; }

    // Setters
    public void setOrderId(String orderId) { this.orderId = orderId; }
    public void setOrderNo(String orderNo) { this.orderNo = orderNo; }
    public void setCustNo(String custNo) { this.custNo = custNo; }
    public void setSubTotal(BigDecimal subTotal) { this.subTotal = subTotal; }
    public void setStatus(String status) { this.status = status; }
    public void setCustId(String custId) { this.custId = custId; }
}
