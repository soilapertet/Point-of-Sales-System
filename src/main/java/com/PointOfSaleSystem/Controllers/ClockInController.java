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

    public ClockInController(CentralPointOfSalesController controller) {

        super(controller);

        // Connect to store database and initialise "employees" collection
        employeeDB = EmployeeDatabase.getInstance();
        employeeDB.initialiseEmployeesCollection();
    }

    public void clockInEmployee(int associateIDInput, String passwordInput) throws Exception {

        EmployeeInputController eic = this.getCentralPOSController().getEmployeeInputController();
        boolean isCashEmployee;

        // 1. Get employeeID
        eic.verifyEmployeeIDInput(associateIDInput);

        // 2. Check if employee is in Cash department
        isCashEmployee = employeeDB.isInCashDep(eic.getEmployeeID());

        // 3. Update clock in status if they are cash-trained; else, display error message
        if(isCashEmployee) {
            cashEmployeeID = eic.getEmployeeID();
            eic.verifyLoginPasswordInput(passwordInput);
            clockInPassword = eic.getLoginPassword();
            clockedIn = true;
        } else {
            throw new Exception("Error: You are not authorised to clock into the cash register.");
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
