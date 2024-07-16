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

    // Define class constructor
    public CustomerInputController() {
        customerDB = CustomerDatabase.getInstance();
        customerDB.initialiseCustomersCollection();
    }

    // Prompt user for their phone number
    public long promptForPhoneNumber() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Add customer to sale");
        return scanner.nextLong();
    }

    // Prompt user for their email address
    public String promptForEmailAddress() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Add customer to sale");
        return scanner.next();
    }

    // Prompt user for their first name
    public String promptForFirstName() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Add customer to sale");
        return scanner.next();
    }

    // Prompt user for their last name
    public String promptForLastName() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Add customer to sale");
        return scanner.next();
    }

}
