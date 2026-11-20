package model;

import java.math.BigDecimal;

public class OrderVar {

    private String orderId;       // FK → Order
    private String varId;         // FK → Variation
    private int prodQty;          // Quantity of this variation
    private BigDecimal totalPrice; // Total price of the items

    // Default constructor
    public OrderVar() {}

    // Full constructor
    public OrderVar(String orderId, String varId, int prodQty, BigDecimal totalPrice) {
        this.orderId = orderId;
        this.varId = varId;
        this.prodQty = prodQty;
        this.totalPrice = totalPrice;
    }

    // Getters
    public String getOrderId() { return orderId; }
    public String getVarId() { return varId; }
    public int getProdQty() { return prodQty; }
    public BigDecimal getTotalPrice() { return totalPrice; }

    // Setters
    public void setOrderId(String orderId) { this.orderId = orderId; }
    public void setVarId(String varId) { this.varId = varId; }
    public void setProdQty(int prodQty) { this.prodQty = prodQty; }
    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }
}
