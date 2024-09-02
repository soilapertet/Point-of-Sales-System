package com.PointOfSaleSystem.Controllers;

import com.PointOfSaleSystem.CentralPOSLogic.CentralPointOfSalesController;
import com.PointOfSaleSystem.StoreDatabase.EmployeeDatabase;
import com.mongodb.client.model.Filters;
import org.bson.conversions.Bson;

public class EmployeeLoginController extends CentralPointOfSalesController {

    private EmployeeDatabase employeeDB;
    private EmployeeInputController employeeInputController;
    private boolean loggedIn;
    private int cashEmployeeID;
    private String loginPassword;
    private boolean loginStatus;

    public EmployeeLoginController(CentralPointOfSalesController controller) {

        super(controller);

        // Connect to store database and initialise "employees" collection
        employeeDB = EmployeeDatabase.getInstance();
        employeeDB.initialiseEmployeesCollection();

        // Get EmployeeInputController class instance
        employeeInputController = this.getCentralPOSController().getEmployeeInputController();
    }

    public void logInEmployee(int associateIDInput, String passwordInput) throws Exception {

        boolean isCashEmployee;

        // 1. Get employeeID
        employeeInputController.verifyEmployeeIDInput(associateIDInput);

        // 2. Check if employee is in Cash department
        isCashEmployee = employeeDB.isInCashDep(employeeInputController.getEmployeeID());

        // 3. Update login status if they are cash-trained; else, display error message
        if(isCashEmployee) {
            cashEmployeeID = employeeInputController.getEmployeeID();
            employeeInputController.verifyLoginPasswordInput(passwordInput);
            loginPassword = employeeInputController.getLoginPassword();
            loggedIn = true;
        } else {
            throw new Exception("Error: You are not authorised to clock into the cash register.");
        }
    }

    // Get the login status of the user from the MongoDB database
    private void getLoginStatusFromDB() {

        Bson filter = Filters.and(Filters.eq("employeeID", cashEmployeeID),
                Filters.eq("loginPassword", loginPassword));

        loginStatus = employeeDB.getEmployeesCollection().find(filter).first().getBoolean("loggedIn");
    }

    // Define getter methods
    public boolean getLoggedIn() {
        return loggedIn;
    }
    public int getCashEmployeeID() {
        return cashEmployeeID;
    }
    public String getLoginPassword() {
        return loginPassword;
    }
    public boolean getLoginStatus() {
        return loginStatus;
    }

}
