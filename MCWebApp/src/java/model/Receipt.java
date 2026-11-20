package model;
import view.ManageOrderView;

import java.time.LocalDate;
import java.math.BigDecimal;
import java.util.List;

public class Receipt {

    private String receiptId;       // eg: R001
    private String payType;         // Bank transaction channel: Maybank | CIMB Bank | Public Bank
    private LocalDate receiptDate;
    private BigDecimal gst;
    private BigDecimal grandTotal;  // Grand total after added with GST
    private String staffId;         // FK Staff
    private String orderId;         // FK Order
    private List<ManageOrderView> orderItem;

    // Default constructor
    public Receipt() {}

    // Full constructor
    public Receipt(String receiptId, String payType, LocalDate receiptDate,
                   BigDecimal gst, BigDecimal grandTotal, String staffId, String orderId) {
        this.receiptId = receiptId;
        this.payType = payType;
        this.receiptDate = receiptDate;
        this.gst = gst;
        this.grandTotal = grandTotal;
        this.staffId = staffId;
        this.orderId = orderId;
    }

    // Getters
    public String getReceiptId() { return receiptId; }
    public String getPayType() { return payType; }
    public LocalDate getReceiptDate() { return receiptDate; }
    public BigDecimal getGst() { return gst; }
    public BigDecimal getGrandTotal() { return grandTotal; }
    public String getStaffId() { return staffId; }
    public String getOrderId() { return orderId; }
    public List<ManageOrderView> getOrderItem() { return orderItem; }

    // Setters
    public void setReceiptId(String receiptId) { this.receiptId = receiptId; }
    public void setPayType(String payType) { this.payType = payType; }
    public void setReceiptDate(LocalDate receiptDate) { this.receiptDate = receiptDate; }
    public void setGst(BigDecimal gst) { this.gst = gst; }
    public void setGrandTotal(BigDecimal grandTotal) { this.grandTotal = grandTotal; }
    public void setStaffId(String staffId) { this.staffId = staffId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }
    public void setOrderItem(List<ManageOrderView> orderItem) { this.orderItem = orderItem; }

    public void setOrderNo(String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setStatus(String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setSubTotal(BigDecimal bigDecimal) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getStatus() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getSubTotal() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
