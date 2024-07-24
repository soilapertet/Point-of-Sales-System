package com.PointOfSaleSystem.Controllers;

import com.PointOfSaleSystem.CentralPOSFacade.CentralPointOfSalesFacade;
import com.PointOfSaleSystem.StoreDatabase.CustomerDatabase;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.Scanner;

public class CustomerInputController  {

    // Define instance variables
    private CentralPointOfSalesFacade centralPOSFacade;
    private CustomerDatabase customerDB;
    private ObjectId uniqueID;
    private String customerFirstName;
    private String customerLastName;
    private long phoneNumber;
    private String emailAddress;
    private boolean guestMode;

    private String inputEmail;
    private String firstNameInput;
    private String lastNameInput;
    private long inputPhoneNumber;

    // Define class constructor
    public CustomerInputController() {

        centralPOSFacade = CentralPointOfSalesFacade.getCentralPOSFacade();

        // Connect to store database and initialise "customer_account" collection
        customerDB = CustomerDatabase.getInstance();
        customerDB.initialiseCustomersCollection();
    }

    // Prompt for customer details : Phone number, Email address, First name, Last name
    public void promptForCustomerDetails() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter customer information here: ");

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

        Bson filter; FindIterable<Document> matchingDocs;

        if(inDB) {
            if(inputEmail != null) {
                filter = Filters.eq("emailAddress", inputEmail);
            } else if(inputPhoneNumber != 0) {
                filter = Filters.eq("phoneNumber", inputPhoneNumber);
            } else {
                filter = Filters.and(Filters.eq("firstName", firstNameInput),
                        Filters.eq("lastName", lastNameInput));
            }

            matchingDocs = customerDB.getCustomersCollection().find(filter);
            this.uniqueID = matchingDocs.first().getObjectId("_id");
            this.customerFirstName = matchingDocs.first().getString("firstName");
            this.customerLastName = matchingDocs.first().getString("lastName");
            this.phoneNumber = matchingDocs.first().getLong("phoneNumber");
            this.emailAddress = matchingDocs.first().getString("emailAddress");
            this.guestMode = false;
            System.out.println("Customer info has been set for transaction");

        } else {
            System.err.println("Customer account is not in database");
            promptToCreateAccount();
        }
    }

    // Check if user would like to create a customer account or continue as a guest
    private void promptToCreateAccount() {

        System.out.println("Would you like to create a Customer Rewards Account? ");

        Scanner scanner = new Scanner(System.in);
        String response = scanner.next();

        if(response.equalsIgnoreCase("Yes")) {
            createCustomerAccount();
        } else {
            System.out.println("Continuing in Guest Mode...");
            this.guestMode = true;
        }
    }

    // Create a customer account based on user input
    private void createCustomerAccount() {
        System.out.println("Creating customer account: ");

        Scanner scanner = new Scanner(System.in);

        System.out.println("First Name: ");
        this.customerFirstName =  scanner.next();

        System.out.println("Last Name: ");
        this.customerLastName = scanner.next();

        System.out.println("Phone Number: ");
        this.phoneNumber = scanner.nextLong();

        System.out.println("Email Address: ");
        this.emailAddress = scanner.next();

        this.guestMode = false;

        customerDB.addCustomerToDB(customerFirstName, customerLastName, phoneNumber, emailAddress);
    }

    // Update customer info
    public void updateCustomerInfo(String fName, String lName, long phoneNumber, String email) {

        // Define a filter for the document
        Bson filter = Filters.eq("_id", getUniqueID());

        // Define the updates to the documents (making changes to all the fields if there are any)
        Bson updates = Updates.combine(
                Updates.set("firstName", fName),
                Updates.set("lastName", lName),
                Updates.set("phoneNumber", phoneNumber),
                Updates.set("emailAddress", email)
        );

        // Update the first document that matches the filter
        UpdateResult result = customerDB.getCustomersCollection().updateOne(filter, updates);

        // Prints the number of updated documents and the upserted document ID, if an upsert was performed
        System.out.println("Modified document count: " + result.getModifiedCount());
    }

    // Define getter methods
    public long getPhoneNumber() {return phoneNumber;}

    public String getCustomerFirstName() {return customerFirstName;}

    public String getCustomerLastName() {return customerLastName;}

    public String getEmailAddress() {return emailAddress;}

    public boolean getGuestModeStatus() {return guestMode;}

    public ObjectId getUniqueID() { return uniqueID;}
}
