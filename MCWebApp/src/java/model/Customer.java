package model;

public class Customer {

    private String custId;       // eg: C001
    private String custName;
    private String custEmail;
    private String custPass;
    private String custAdd;
    private String custPhone;    // eg: XXX-XXXXXXX
    private String userRole;

    public Customer() {
    }

    public Customer(String custId, String custName, String custEmail, String custPass,
            String custAdd, String custPhone, String userRole) {
        this.custId = custId;
        this.custName = custName;
        this.custEmail = custEmail;
        this.custPass = custPass;
        this.custAdd = custAdd;
        this.custPhone = custPhone;
        this.userRole = userRole;
    }

    public String getCustId() {
        return custId;
    }

    public String getCustName() {
        return custName;
    }

    public String getCustEmail() {
        return custEmail;
    }

    public String getCustPass() {
        return custPass;
    }

    public String getCustAdd() {
        return custAdd;
    }

    public String getCustPhone() {
        return custPhone;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public void setCustEmail(String custEmail) {
        this.custEmail = custEmail;
    }

    public void setCustPass(String custPass) {
        this.custPass = custPass;
    }

    public void setCustAdd(String custAdd) {
        this.custAdd = custAdd;
    }

    public void setCustPhone(String custPhone) {
        this.custPhone = custPhone;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
}
