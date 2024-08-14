package com.PointOfSaleSystem.Controllers;

import com.PointOfSaleSystem.CentralPOSLogic.CentralPointOfSalesController;
import com.PointOfSaleSystem.StoreDatabase.EmployeeDatabase;
import com.mongodb.client.model.Filters;
import org.bson.conversions.Bson;

public class ClockInController extends CentralPointOfSalesController {

    private EmployeeDatabase employeeDB;
    private boolean clockedIn;
    private int cashEmployeeID;
    private String clockInPassword;
    private boolean clockedInStatus;

    public ClockInController(CentralPointOfSalesController facade) {

        super(facade);

        // Connect to store database and initialise "employees" collection
        employeeDB = EmployeeDatabase.getInstance();
        employeeDB.initialiseEmployeesCollection();
    }

    public void clockInEmployee() {

        EmployeeInputController eic = this.getEmployeeInputController();
        boolean isCashEmployee;

        // 1. Get employeeID
        eic.getEmployeeIDInput();

        // 2. Check if employee is in Cash department
        cashEmployeeID = eic.getEmployeeID();
        isCashEmployee = employeeDB.isInCashDep(cashEmployeeID);

        // 3. Update clock in status if they are cash-trained; else, display error message
        if(isCashEmployee) {
            eic.getLoginPasswordInput();
            clockInPassword = eic.getLoginPassword();
            clockedIn = true;
            System.out.println("You have successfully clocked in!!!");
        } else {
            System.err.println("Error: You are not authorised to clock into the cash register.");
        }
    }

    // Get the clocked in status of the user from the MongoDB database
    private void getClockedInStatusFromDB() {

        Bson filter = Filters.and(Filters.eq("employeeID", cashEmployeeID),
                Filters.eq("loginPassword", clockInPassword));

        clockedInStatus = employeeDB.getEmployeesCollection().find(filter).first().getBoolean("clockedIn");
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

    public boolean getClockedInStatus() {
        return clockedInStatus;
    }
}
