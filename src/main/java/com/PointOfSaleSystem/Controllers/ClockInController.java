package com.PointOfSaleSystem.Controllers;

import com.PointOfSaleSystem.StoreDatabase.EmployeeDatabase;

public class ClockInController {

    EmployeeDatabase employeeDB;
    private boolean clockedIn;
    private int cashEmployeeID;
    private String clockInPassword;

    public ClockInController() {

        // Connect to store database and initialise "employees" collection
        employeeDB = EmployeeDatabase.getInstance();
        employeeDB.initialiseEmployeesCollection();
    }

    public void clockInEmployee() {

        EmployeeInputController eic = new EmployeeInputController();
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

    public static void main(String[] args) {
        ClockInController cic = new ClockInController();
        cic.clockInEmployee();
    }
}
