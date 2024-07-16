package com.PointOfSaleSystem.Controllers;

import com.PointOfSaleSystem.StoreDatabase.CustomerDatabase;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.Scanner;

public class CustomerInputController {

    // Define instance variables
    private CustomerDatabase customerDB;
    private String customerFirstName;
    private String customerLastName;
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
        } else {
            String input = scanner.nextLine();

            if(input.contains("@")) {
                inputEmail = input;
            } else {
                firstNameInput = input.split(" ")[0];
                lastNameInput = input.split(" ")[1];
            }
        }
    }

    // Define a method to check if the user has a customer account
    private boolean hasACustomerAccount() {

        if(inputPhoneNumber != 0) {
            return customerDB.isInCustomerDatabase(inputPhoneNumber);
        } else if(inputEmail != null) {
            return customerDB.isInCustomerDatabase(inputEmail);
        } else {
            return customerDB.isInCustomerDatabase(firstNameInput, lastNameInput);
        }
    }

    // Set the customer info based on customer details in customer DB
    private void setCustomerInfo(boolean inDB) {

        Bson filter;
        FindIterable<Document> matchingDocs;

        if(inDB) {
            if(inputEmail != null) {
                filter = Filters.eq("emailAddress", inputEmail);
                matchingDocs = customerDB.getCustomersCollection().find(filter);
            } else if(inputPhoneNumber != 0) {
                filter = Filters.eq("phoneNumber", inputPhoneNumber);
                matchingDocs = customerDB.getCustomersCollection().find(filter);
            } else {
                filter = Filters.and(Filters.eq("firstName", firstNameInput),
                        Filters.eq("lastName", lastNameInput));
                matchingDocs = customerDB.getCustomersCollection().find(filter);
            }

            this.customerFirstName = matchingDocs.first().getString("firstName");
            this.customerLastName = matchingDocs.first().getString("lastName");
            this.phoneNumber = matchingDocs.first().getLong("phoneNumber");
            this.emailAddress = matchingDocs.first().getString("emailAddress");

            System.out.println("Customer info has been set for the transaction");
        } else {
            System.err.println("Customer account is not in database");
        }
    }

    // Define getter methods
    public long getPhoneNumber() {
        return phoneNumber;
    }

    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public String getEmailAddress() {return emailAddress;}

    public static void main(String[] args) {

        CustomerInputController cic = new CustomerInputController();

        // 1. Get customer details: email, phone number, first name and last name
        cic.promptForCustomerDetails();

        // 2. Check if customer is in the database
        boolean isInCustomerDB = cic.hasACustomerAccount();
        System.out.println(isInCustomerDB);
        cic.setCustomerInfo(isInCustomerDB);

        System.out.println(cic.getCustomerFirstName());
        System.out.println(cic.getCustomerLastName());
        System.out.println(cic.getEmailAddress());
        System.out.println(cic.getPhoneNumber());
    }

}
