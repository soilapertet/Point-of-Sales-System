package com.PointOfSaleSystem.Controllers;

import com.PointOfSaleSystem.StoreDatabase.StoreDatabase;

import java.util.Scanner;

public class EmployeeInputController {

    private int employeeID;
    private String loginPassword;
    private boolean clockedIn;
    private StoreDatabase storeDb;

    // Define class constructor
    public EmployeeInputController() {
        storeDb = StoreDatabase.getInstance();
        storeDb.initialiseEmployeesCollection();
    }

    // Prompt user for their employee ID
    private int promptForEmployeeID() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your Employee ID here: ");
        int idInput = scanner.nextInt();

        return idInput;
    }

    // Validate user's employee ID
    private boolean validateEmployeeID(int id) {

        boolean validEmployeeID;
        validEmployeeID = storeDb.isInEmployeeDatabase(id);
        return storeDb.isInEmployeeDatabase(id);
    }

    // Get user's employee ID
    public void getEmployeeIDInput() {

        int idInput = promptForEmployeeID();

        while(!validateEmployeeID(idInput)) {
            System.err.println("Invalid Employee ID. Please try again.\n");
            idInput = promptForEmployeeID();
        }

        employeeID = idInput;
    }

    // Define getter methods
    public int getEmployeeID() {
        return employeeID;
    }

    public static void main(String[] args) {
        EmployeeInputController eic = new EmployeeInputController();
        eic.getEmployeeIDInput();
        System.out.println(eic.getEmployeeID() + " is a valid employee ID!");
    }
}
