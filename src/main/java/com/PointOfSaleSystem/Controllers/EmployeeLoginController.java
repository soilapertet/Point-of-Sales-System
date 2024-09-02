package com.PointOfSaleSystem.Controllers;

import com.PointOfSaleSystem.CentralPOSLogic.CentralPointOfSalesController;
import com.PointOfSaleSystem.StoreDatabase.EmployeeDatabase;
import com.mongodb.MongoException;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
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

            // 4. Update the login status in mongodb
            updateLoginStatusInDB();

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

    // Update the login status in mongodb database
    private void updateLoginStatusInDB() {

        // a. Create a query/filter document
        Document queryDoc = new Document().append("employeeID", cashEmployeeID);

        // b. Define the update to be made
        Bson update = Updates.set("loggedIn", true);

        // c. Make update in mongodb database
        try {
            // Find the document matching the query and update the field "loggedIn"
            UpdateResult result = employeeDB.getEmployeesCollection().updateOne(queryDoc, update);

            // Prints the number of updated documents and the upserted document ID, if an upsert was performed
            System.out.println("Modified document count: " + result.getModifiedCount());
            System.out.println("Upserted id: " + result.getUpsertedId());

        } catch(MongoException me) {
            me.printStackTrace();
        }
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
