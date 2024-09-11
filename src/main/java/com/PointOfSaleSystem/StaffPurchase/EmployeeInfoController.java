package com.PointOfSaleSystem.StaffPurchase;

import com.PointOfSaleSystem.CentralPOSLogic.CentralPointOfSalesController;
import org.bson.types.ObjectId;

public class EmployeeInfoController extends CentralPointOfSalesController {

    // Define instance variables
    private int employeeID;
    private ObjectId uniqueID;
    private String employeeName;
    private String employmentType;
    private boolean staffPurchase;
    private boolean guestMode;
    private double staffDiscountPercent;
    private double discountBufferPercent;

    public EmployeeInfoController(CentralPointOfSalesController controller) {
        super(controller);

        this.staffDiscountPercent = 0.5;
        this.discountBufferPercent = 0.1;
    }

    // Define getter methods
    public String getEmployeeName() { return employeeName; }
    public int getEmployeeID() { return employeeID; }
    public ObjectId getUniqueID() { return uniqueID; }
    public String getEmploymentType() { return employmentType; }
    public boolean isStaffPurchase() { return staffPurchase; }
    public boolean isGuestMode() { return guestMode; }

    // Define setter methods
    public void setEmployeeName(String employeeName) { this.employeeName = employeeName; }
    public void setEmployeeID(int employeeID) { this.employeeID = employeeID; }
    public void setUniqueID(ObjectId uniqueID) { this.uniqueID = uniqueID; }
    public void setEmploymentType(String employmentType) { this.employmentType = employmentType; }
    public void setStaffPurchase(boolean staffPurchase) { this.staffPurchase = staffPurchase; }
    public void setGuestMode(boolean guestMode) { this.guestMode = guestMode; }
}
