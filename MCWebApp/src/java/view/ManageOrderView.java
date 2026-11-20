// Custom model class is only to create object for display purpose.
package view;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ManageOrderView {

    /* ======================
       Order / Receipt header
       ====================== */
    private String receiptId;
    private LocalDate receiptDate;
    private String payType;
    private BigDecimal grandTotal;
    private BigDecimal gst;
    private BigDecimal subTotal;

    private String orderId;
    private String orderNo;
    private String custNo;

    /* ======================
       Customer info
       ====================== */
    private String custId;
    private String custName;
    private String custAdd;
    private String custPhone;
    private String custEmail;

    /* ======================
       Order item fields
       (used by getAllOrderEntries)
       ====================== */
    private String varId;
    private String sku;
    private int prodQty;
    private String prodName;
    private String varName;
    private BigDecimal totalPrice;
    private String status;

    /* ======================
       Nested order items
       ====================== */
    private List<ManageOrderView> orderItem;

    // Default constructor
    public ManageOrderView() {}

    /* ============
       Getters
       ============ */
    public String getReceiptId() { return receiptId; }
    public LocalDate getReceiptDate() { return receiptDate; }
    public String getPayType() { return payType; }
    public BigDecimal getGrandTotal() { return grandTotal; }
    public BigDecimal getGst() { return gst; }
    public BigDecimal getSubTotal() { return subTotal; }

    public String getOrderId() { return orderId; }
    public String getOrderNo() { return orderNo; }

    public String getCustId() { return custId; }
    public String getCustNo() { return custNo; }
    public String getCustName() { return custName; }
    public String getCustAdd() { return custAdd; }
    public String getCustPhone() { return custPhone; }
    public String getCustEmail() { return custEmail; }

    public String getVarId() { return varId; }
    public String getSku() { return sku; }
    public int getProdQty() { return prodQty; }
    public String getProdName() { return prodName; }
    public String getVarName() { return varName; }
    public BigDecimal getTotalPrice() { return totalPrice; }
    public String getStatus() { return status; }

    public List<ManageOrderView> getOrderItem() { return orderItem; }

    /* ============
       Setters
       ============ */
    public void setReceiptId(String receiptId) { this.receiptId = receiptId; }
    public void setReceiptDate(LocalDate receiptDate) { this.receiptDate = receiptDate; }
    public void setPayType(String payType) { this.payType = payType; }
    public void setGrandTotal(BigDecimal grandTotal) { this.grandTotal = grandTotal; }
    public void setGst(BigDecimal gst) { this.gst = gst; }
    public void setSubTotal(BigDecimal subTotal) { this.subTotal = subTotal; }

    public void setOrderId(String orderId) { this.orderId = orderId; }
    public void setOrderNo(String orderNo) { this.orderNo = orderNo; }

    public void setCustId(String custId) { this.custId = custId; }
    public void setCustNo(String custNo) { this.custNo = custNo; }
    public void setCustName(String custName) { this.custName = custName; }
    public void setCustAdd(String custAdd) { this.custAdd = custAdd; }
    public void setCustPhone(String custPhone) { this.custPhone = custPhone; }
    public void setCustEmail(String custEmail) { this.custEmail = custEmail; }

    public void setVarId(String varId) { this.varId = varId; }
    public void setSku(String sku) { this.sku = sku; }
    public void setProdQty(int prodQty) { this.prodQty = prodQty; }
    public void setProdName(String prodName) { this.prodName = prodName; }
    public void setVarName(String varName) { this.varName = varName; }
    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }
    public void setStatus(String status) { this.status = status; }

    public void setOrderItem(List<ManageOrderView> orderItem) {
        this.orderItem = orderItem;
    }
}
