package com.PointOfSaleSystem.Controllers;

import com.PointOfSaleSystem.StoreDatabase.StoreDatabase;

public class ClockInController {

    private boolean clockedIn;
    private int cashEmployeeID;
    private String clockInPassword;

    public ClockInController() {

        // Connect to store database and initialise "employees" collection
        StoreDatabase storeDB = StoreDatabase.getInstance();
        storeDB.initialiseEmployeesCollection();
    }

    // Define getter methods
    public boolean getClockedIn() {
        return clockedIn;
    }

    public int getCashEmployeeID() {
        return cashEmployeeID;
    }

    public String getClockInPassword() {
        return clockInPassword;
    }

}
