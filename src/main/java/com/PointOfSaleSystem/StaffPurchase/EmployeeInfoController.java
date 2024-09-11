package com.PointOfSaleSystem.StaffPurchase;

import com.PointOfSaleSystem.CentralPOSLogic.CentralPointOfSalesController;
import org.bson.types.ObjectId;

public class EmployeeInfoController extends CentralPointOfSalesController {

    // Define instance variables
    private String employeeName;
    private int employeeID;
    private ObjectId uniqueID;
    private double staffDiscountPercent;
    private double discountBufferPercent;
    private String employeeStatus;

    public EmployeeInfoController(CentralPointOfSalesController controller) {
        super(controller);

        this.staffDiscountPercent = 0.5;
        this.discountBufferPercent = 0.1;
    }

    // Define getter methods
    public String getEmployeeName() { return employeeName; }
    public int getEmployeeID() { return employeeID; }
    public ObjectId getUniqueID() { return uniqueID; }

    // Define setter methods
    public void setEmployeeName(String employeeName) { this.employeeName = employeeName; }
    public void setEmployeeID(int employeeID) { this.employeeID = employeeID; }
    public void setUniqueID(ObjectId uniqueID) { this.uniqueID = uniqueID; }
}
