package com.PointOfSaleSystem.Controllers;

import com.PointOfSaleSystem.StoreDatabase.EmployeeDatabase;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.Scanner;

public class EmployeeInputController {

    private int employeeID;
    private String loginPassword;
    private EmployeeDatabase employeeDB;

    // Define class constructor
    public EmployeeInputController() {
        employeeDB = EmployeeDatabase.getInstance();
        employeeDB.initialiseEmployeesCollection();
    }

    // Prompt user for their employee ID
    private int promptForEmployeeID() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your Employee ID here: ");
        return scanner.nextInt();
    }

    // Prompt user for their login password
    private String promptForLoginPassword() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your login password here: ");
        return scanner.nextLine();
    }

    // Check if user's employee ID is valid
    private boolean checkIfEmployeeIDIsValid(int id) {

        boolean isValidEmployeeID;
        isValidEmployeeID = employeeDB.isInEmployeeDatabase(id);
        return isValidEmployeeID;
    }

    // Check if user's login password is valid
    private boolean checkIfLoginPasswordIsValid(int employeeID, String password) {

        boolean isValidLoginPassword;

        Bson filter = Filters.and(Filters.eq("employeeID", employeeID),
                Filters.eq("loginPassword", password));

        isValidLoginPassword = employeeDB.getEmployeesCollection().find(filter).iterator().hasNext();

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

    // Define getter methods
    public int getEmployeeID() {
        return employeeID;
    }

    public String getLoginPassword() {
        return loginPassword;
    }
}
