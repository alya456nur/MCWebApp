package model;

public class Staff {

    private String staffId;       // eg: S001
    private String staffEmail;
    private String staffPass;
    private String staffName;
    private String staffPhone;    // eg: XXX-XXXXXXX
    private String userRole;      // eg: admin | staff

    // Default constructor
    public Staff() {}

    // Full constructor
    public Staff(String staffId, String staffEmail, String staffPass,
                 String staffName, String staffPhone, String userRole) {
        this.staffId = staffId;
        this.staffEmail = staffEmail;
        this.staffPass = staffPass;
        this.staffName = staffName;
        this.staffPhone = staffPhone;
        this.userRole = userRole;
    }

    // Getters
    public String getStaffId() { return staffId; }
    public String getStaffEmail() { return staffEmail; }
    public String getStaffPass() { return staffPass; }
    public String getStaffName() { return staffName; }
    public String getStaffPhone() { return staffPhone; }
    public String getUserRole() { return userRole; }

    // Setters
    public void setStaffId(String staffId) { this.staffId = staffId; }
    public void setStaffEmail(String staffEmail) { this.staffEmail = staffEmail; }
    public void setStaffPass(String staffPass) { this.staffPass = staffPass; }
    public void setStaffName(String staffName) { this.staffName = staffName; }
    public void setStaffPhone(String staffPhone) { this.staffPhone = staffPhone; }
    public void setUserRole(String userRole) { this.userRole = userRole; }
}
