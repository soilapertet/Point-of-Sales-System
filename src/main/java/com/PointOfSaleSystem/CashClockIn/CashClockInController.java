package com.PointOfSaleSystem.CashClockIn;

public class CashClockInController {

    // Initialise instance variables
    private final int employeeID;
    private String loginPassword;
    private boolean clockInStatus;

    // Define class constructor
    public CashClockInController(int employeeID, String loginPassword) {
        this.employeeID = employeeID;
        this.loginPassword = loginPassword;
    }

    // Define getter methods
    public int getEmployeeID() {
        return employeeID;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public  boolean getClockInStatus() {
        return clockInStatus;
    }

    // Define setter methods
    public void setClockInStatus(boolean status) {
        this.clockInStatus = status;
    }
}
