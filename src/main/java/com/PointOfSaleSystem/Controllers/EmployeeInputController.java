package com.PointOfSaleSystem.Controllers;

import com.PointOfSaleSystem.StoreDatabase.StoreDatabase;
import com.mongodb.client.model.Filters;
import org.bson.conversions.Bson;

import java.util.Scanner;

public class EmployeeInputController {

    private int employeeID;
    private String loginPassword;
    private boolean clockedInStatus;
    private StoreDatabase storeDB;

    // Define class constructor
    public EmployeeInputController() {
        storeDB = StoreDatabase.getInstance();
        storeDB.initialiseEmployeesCollection();
    }

    // Prompt user for their employee ID
    private int promptForEmployeeID() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your Employee ID here: ");
        int idInput = scanner.nextInt();

        return idInput;
    }

    // Prompt user for their login password
    private String promptForLoginPassword() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your login password here: ");
        String passwordInput = scanner.nextLine();

        return passwordInput;
    }

    // Check if user's employee ID is valid
    private boolean checkIfEmployeeIDIsValid(int id) {

        boolean isValidEmployeeID;
        isValidEmployeeID = storeDB.isInEmployeeDatabase(id);
        return isValidEmployeeID;
    }

    // Check if user's login password is valid
    private boolean checkIfLoginPasswordIsValid(int employeeID, String password) {

        boolean isValidLoginPassword;

        Bson filter = Filters.and(Filters.eq("employeeID", employeeID),
                Filters.eq("loginPassword", password));

        isValidLoginPassword = storeDB.getEmployeesCollection().find(filter).iterator().hasNext();

        return isValidLoginPassword;
    }

    // Get user's employee ID
    public void getEmployeeIDInput() {

        int idInput = promptForEmployeeID();

        while(!checkIfEmployeeIDIsValid(idInput)) {
            System.err.println("Employee ID not in database. Please try again.\n");
            idInput = promptForEmployeeID();
        }

        this.employeeID = idInput;
    }

    // Get user's login password
    public void getLoginPasswordInput() {

        String passwordInput = promptForLoginPassword();

        while(!checkIfLoginPasswordIsValid(getEmployeeID(), passwordInput)) {
            System.err.println("Incorrect password. Please try again.\n");
            passwordInput = promptForLoginPassword();
        }

        this.loginPassword = passwordInput;
    }

    // Get the clocked in status of the user from the MongoDB database
    private void getClockedInStatusFromDB() {

        Bson filter = Filters.and(Filters.eq("employeeID", employeeID),
                Filters.eq("loginPassword", loginPassword));

        clockedInStatus = storeDB.getEmployeesCollection().find(filter).first().getBoolean("clockedIn");
    }

    // Define getter methods
    public int getEmployeeID() {
        return employeeID;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public boolean getClockedInStatus() {
        return clockedInStatus;
    }

    public static void main(String[] args) {
        EmployeeInputController eic = new EmployeeInputController();

        eic.getEmployeeIDInput();
        System.out.println(eic.getEmployeeID() + " is a valid employee ID!");

        eic.getLoginPasswordInput();
        System.out.println(eic.getLoginPassword() + " is the correct password");
        System.out.println("Login is successful!!");

        eic.getClockedInStatusFromDB();

        System.out.println(eic.getEmployeeID());
        System.out.println(eic.getLoginPassword());
        System.out.println(eic.getClockedInStatus());

    }
}
