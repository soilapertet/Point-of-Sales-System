package com.PointOfSaleSystem.Controllers;

import com.PointOfSaleSystem.CentralPOSLogic.CentralPointOfSalesController;
import com.PointOfSaleSystem.StoreDatabase.CustomerDatabase;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.Scanner;

public class CustomerInputController extends CentralPointOfSalesController {

    // Define instance variables
    private CustomerDatabase customerDB;
    private ObjectId uniqueID;
    private String customerFirstName;
    private String customerLastName;
    private long phoneNumber;
    private String emailAddress;
    private int membershipID;
    private boolean guestMode;

    private String inputEmail;
    private String firstNameInput;
    private String lastNameInput;
    private long inputPhoneNumber;
    private int inputMembershipID;

    // Define class constructor
    public CustomerInputController(CentralPointOfSalesController controller) {

        super(controller);

        // Connect to store database and initialise "customer_account" collection
        customerDB = CustomerDatabase.getInstance();
        customerDB.initialiseCustomersCollection();
        this.guestMode = true;
    }

    public void checkForCustomerAccount(long inputPhoneNumber) throws Exception {

        boolean isCustomerAMember;

        // Initialise input details
        this.inputPhoneNumber = inputPhoneNumber;

        // Check if customer has an account
        isCustomerAMember = hasACustomerAccount();

        // Set customer details if they have a membership
        if(isCustomerAMember) {
            setCustomerInfo();
        } else {
            throw new Exception ("Customer account could not be found with the provided phone number.");
        }
    }

    public void checkForCustomerAccount(int inputMembershipID) throws Exception {

        boolean isCustomerAMember;

        // Initialise input details
        this.inputMembershipID = inputMembershipID;

        // Check if customer has an account
        isCustomerAMember = hasACustomerAccount();

        // Set customer details if they have a membership
        if(isCustomerAMember) {
            setCustomerInfo();
        } else {
            throw new Exception("Customer account could not be found with the provided membership ID.");
        }
    }

    public void checkForCustomerAccount(String inputEmail) throws Exception {

        boolean isCustomerAMember;

        // Initialise input details
        this.inputEmail = inputEmail;

        // Check if customer has an account
        isCustomerAMember = hasACustomerAccount();

        // Set customer details if they have a membership
        if(isCustomerAMember) {
            setCustomerInfo();
        } else {
            throw new Exception("Customer account could not be found with the provided email address.");
        }
    }

    public void checkForCustomerAccount(String fNameInput, String lNameInput) throws Exception {

        boolean isCustomerAMember;

        // Initialise input details
        this.firstNameInput = fNameInput;
        this.lastNameInput = lNameInput;

        // Check if customer has an account
        isCustomerAMember = hasACustomerAccount();

        // Set customer details if they have a membership
        if(isCustomerAMember) {
            setCustomerInfo();
        } else {
            throw new Exception("Customer account could not be found with the provided customer name.");
        }
    }

    // Define a method to check if the user has a customer account
    private boolean hasACustomerAccount() {

        if(inputPhoneNumber != 0) {
            return customerDB.isInCustomerDatabase(inputPhoneNumber);
        } else if(inputEmail != null) {
            return customerDB.isInCustomerDatabase(inputEmail);
        } else if(inputMembershipID != 0) {
            return customerDB.isInCustomerDatabase(inputMembershipID);
        } else {
            return customerDB.isInCustomerDatabase(firstNameInput, lastNameInput);
        }
    }

    // Set the customer info based on customer details in customer DB
    private void setCustomerInfo() {

        Bson filter; FindIterable<Document> matchingDocs;

        if(inputEmail != null) {
            filter = Filters.eq("emailAddress", inputEmail);
        } else if(inputPhoneNumber != 0) {
            filter = Filters.eq("phoneNumber", inputPhoneNumber);
        } else if(inputMembershipID != 0) {
            filter = Filters.eq("membershipID", inputMembershipID);
        } else {
            filter = Filters.and(Filters.eq("firstName", firstNameInput),
                    Filters.eq("lastName", lastNameInput));
        }

        matchingDocs = customerDB.getCustomersCollection().find(filter);
        this.uniqueID = matchingDocs.first().getObjectId("_id");
        this.membershipID = matchingDocs.first().getInteger("membershipID");
        this.customerFirstName = matchingDocs.first().getString("firstName");
        this.customerLastName = matchingDocs.first().getString("lastName");
        this.phoneNumber = matchingDocs.first().getLong("phoneNumber");
        this.emailAddress = matchingDocs.first().getString("emailAddress");
        this.guestMode = false;

        System.out.println("Customer info has been set for transaction");

    }

    // Create a customer account based on user input
    public void createCustomerAccount(
            String customerFirstName, String customerLastName,
            long phoneNumber, String emailAddress
    ) {
        System.out.println("Creating customer account: ");
        this.customerFirstName =  customerFirstName;
        this.customerLastName = customerLastName;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
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

    public ObjectId getUniqueID() { return uniqueID; }
}
