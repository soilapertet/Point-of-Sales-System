package com.PointOfSaleSystem.Controllers;

import com.PointOfSaleSystem.CentralPOSLogic.CentralPointOfSalesController;
import com.PointOfSaleSystem.StoreDatabase.CustomerDatabase;
import com.PointOfSaleSystem.StoreDatabase.EmployeeDatabase;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

public class CustomerInputController extends CentralPointOfSalesController {

    // Define instance variables
    private CustomerDatabase customerDB;
    private EmployeeDatabase employeeDB;
    private ObjectId uniqueID;
    private String customerFirstName;
    private String customerLastName;
    private String employeeName;
    private long phoneNumber;
    private String emailAddress;
    private int membershipID;
    private boolean guestMode;
    private boolean staffPurchase;

    private String inputEmail;
    private String firstNameInput;
    private String lastNameInput;
    private long inputPhoneNumber;
    private int inputID;

    // Define class constructor
    public CustomerInputController(CentralPointOfSalesController controller) {

        super(controller);

        // Connect to store database and initialise "customer_account" collection
        customerDB = CustomerDatabase.getInstance();
        customerDB.initialiseCustomersCollection();

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
    public long getPhoneNumber() { return phoneNumber; }

    public String getCustomerFirstName() { return customerFirstName; }

    public String getCustomerLastName() { return customerLastName; }

    public String getEmailAddress() { return emailAddress; }

    public String getEmployeeName() { return employeeName; }

    public int getMembershipID() { return membershipID; }

    public ObjectId getUniqueID() { return uniqueID; }
}
