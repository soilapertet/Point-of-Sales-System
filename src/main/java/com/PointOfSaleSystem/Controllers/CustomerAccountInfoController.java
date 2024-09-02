package com.PointOfSaleSystem.Controllers;

import com.PointOfSaleSystem.CentralPOSLogic.CentralPointOfSalesController;

public class CustomerAccountInfoController extends CentralPointOfSalesController  {

    // Define instance variables
    private String customerFirstName;
    private String customerLastName;
    private String employeeName;
    private long phoneNumber;
    private String emailAddress;
    private int membershipID;
    private boolean guestMode;
    private boolean staffPurchase;

    // Initialise class constructor
    public CustomerAccountInfoController(CentralPointOfSalesController controller) {
        super(controller);
    }

    // Define getter methods
    public long getPhoneNumber() { return phoneNumber; }
    public String getCustomerFirstName() { return customerFirstName; }
    public String getCustomerLastName() { return customerLastName; }
    public String getEmailAddress() { return emailAddress; }
    public String getEmployeeName() { return employeeName; }
    public int getMembershipID() { return membershipID; }
    public boolean isGuestMode() { return guestMode; }
    public boolean isStaffPurchase() { return staffPurchase; }

    // Define setter methods
    public void setCustomerFirstName(String customerFirstName) { this.customerFirstName = customerFirstName; }
    public void setCustomerLastName(String customerLastName) { this.customerLastName = customerLastName; }
    public void setEmployeeName(String employeeName) { this.employeeName = employeeName; }
    public void setPhoneNumber(long phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setEmailAddress(String emailAddress) { this.emailAddress = emailAddress; }
    public void setMembershipID(int membershipID) { this.membershipID = membershipID; }
    public void setGuestMode(boolean guestMode) { this.guestMode = guestMode; }
    public void setStaffPurchase(boolean staffPurchase) { this.staffPurchase = staffPurchase; }
}
