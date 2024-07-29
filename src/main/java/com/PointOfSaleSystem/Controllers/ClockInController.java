package com.PointOfSaleSystem.Controllers;

import com.PointOfSaleSystem.CentralPOSFacade.CentralPointOfSalesFacade;
import com.PointOfSaleSystem.StoreDatabase.EmployeeDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;

public class ClockInController  extends CentralPointOfSalesFacade {

    private EmployeeDatabase employeeDB;
    private boolean clockedIn;
    private int cashEmployeeID;
    private String clockInPassword;
    private boolean clockedInStatus;

    public ClockInController(CentralPointOfSalesFacade facade) {

        super(facade);

        // Connect to store database and initialise "employees" collection
        employeeDB = EmployeeDatabase.getInstance();
        employeeDB.initialiseEmployeesCollection();
    }

    public void clockInEmployee() {

        // Access the current EmployerInputController instead of initialising a new one
        EmployeeInputController eic = this.getCentralPOSFacade().getEmployeeInputController();
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
            setClockedInStatus();
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

    // Update clockedIn status of the user
    private void setClockedInStatus() {

        // Initialise filter using employeeID
        Bson filter = Filters.eq("employeeID", cashEmployeeID);

        // Specify the update
        Bson update = Updates.set("clockedIn", true);

        // Find document and update clock in status
        UpdateResult result = employeeDB.getEmployeesCollection().updateOne(filter, update);

        System.out.println("Successfully update clockedIn status of employee");

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
