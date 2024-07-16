package com.PointOfSaleSystem.Controllers;

import com.PointOfSaleSystem.StoreDatabase.CustomerDatabase;

import java.util.Scanner;

public class CustomerInputController {

    // Define instance variables
    private CustomerDatabase customerDB;
    private String firstName;
    private String lastName;
    private long phoneNumber;
    private String emailAddress;

    private String inputEmail;
    private String firstNameInput;
    private String lastNameInput;
    private long inputPhoneNumber;
    private int inputAccountNo;

    // Define class constructor
    public CustomerInputController() {
        customerDB = CustomerDatabase.getInstance();
        customerDB.initialiseCustomersCollection();
    }

    // Prompt for customer details : Phone number, Email address, First name, Last name
    public void promptForCustomerDetails() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Getting customer information: ");

        // check if input is a phone number; else check if it's an email address or customer name+0
        if(scanner.hasNextLong()) {
            inputPhoneNumber = scanner.nextLong();
            System.out.println(inputPhoneNumber);
        } else {
            String input = scanner.nextLine();

            if(input.contains("@")) {
                inputEmail = input;
                System.out.println(inputEmail);
            } else {
                firstNameInput = input.split(" ")[0];
                lastNameInput = input.split(" ")[1];
                System.out.println(firstNameInput + " " + lastNameInput);
            }
        }
    }

    // Define a method to check if the user has a customer account
    private boolean hasACustomerAccount() {

        boolean isInCustomerDB;

        if(inputPhoneNumber != 0) {
            isInCustomerDB = customerDB.isInCustomerDatabase(inputPhoneNumber);
        } else if(inputEmail != null) {
            isInCustomerDB = customerDB.isInCustomerDatabase(inputEmail);
        } else {
            isInCustomerDB = customerDB.isInCustomerDatabase(firstNameInput, lastNameInput);
        }

        return isInCustomerDB;
    }

    public static void main(String[] args) {

        CustomerInputController cic = new CustomerInputController();
        System.out.println(cic.inputPhoneNumber);
        System.out.println(cic.inputEmail);
        System.out.println(cic.firstNameInput);
        System.out.println(cic.lastNameInput);
        cic.promptForCustomerDetails();

    }

}
