package com.PointOfSaleSystem.Controllers;

import com.PointOfSaleSystem.CentralPOSLogic.CentralPointOfSalesController;
import com.PointOfSaleSystem.StoreDatabase.EmployeeDatabase;
import com.mongodb.client.model.Filters;
import org.bson.conversions.Bson;

import java.util.Scanner;

public class EmployeeInputController extends CentralPointOfSalesController {

    // Define instance variables
    private CentralPointOfSalesController centralPointOfSalesController;
    private int employeeID;
    private String loginPassword;
    private EmployeeDatabase employeeDB;

    // Define class constructor
    public EmployeeInputController(CentralPointOfSalesController controller) {

        super(controller);

        // Connect to store database and initialise "employees" collection
        employeeDB = EmployeeDatabase.getInstance();
        employeeDB.initialiseEmployeesCollection();
    }

    // Prompt user for their employee ID
    public int getEmployeeIDInput() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your Employee ID here: ");
        return scanner.nextInt();
    }

    // Prompt user for their login password
    public String getLoginPasswordInput() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your login password here: ");
        return scanner.nextLine();
    }

    // Check if user's employee ID is valid
    private boolean isEmployeeIDValid(int id) {

        boolean isValidEmployeeID;
        isValidEmployeeID = employeeDB.isInEmployeeDatabase(id);
        return isValidEmployeeID;
    }

    // Check if user's login password is valid
    private boolean isLoginPasswordValid(int employeeID, String password) {

        boolean isValidLoginPassword;

        Bson filter = Filters.and(Filters.eq("employeeID", employeeID),
                Filters.eq("loginPassword", password));

        isValidLoginPassword = employeeDB.getEmployeesCollection().find(filter).iterator().hasNext();

        return isValidLoginPassword;
    }

    // Manage employee ID input
    public void verifyEmployeeIDInput(int associateID) throws Exception{

        if(isEmployeeIDValid(associateID)) {
            this.employeeID = associateID;
        } else {
            throw new Exception("Employee ID could not be found in database. Please try again.\n");
        }
    }

    // Manage login password input
    public void verifyLoginPasswordInput(String passwordInput) throws Exception {
        if(isLoginPasswordValid(getEmployeeID(), passwordInput)) {
            this.loginPassword = passwordInput;
        } else {
            throw new Exception("Incorrect password. Please try again.");
        }
    }

    // Define getter methods
    public int getEmployeeID() {
        return employeeID;
    }
    public String getLoginPassword() {
        return loginPassword;
    }
}
