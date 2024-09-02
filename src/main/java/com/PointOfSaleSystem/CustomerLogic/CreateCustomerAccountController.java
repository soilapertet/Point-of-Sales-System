package com.PointOfSaleSystem.CustomerLogic;

import com.PointOfSaleSystem.CentralPOSLogic.CentralPointOfSalesController;
import com.PointOfSaleSystem.StoreDatabase.CustomerDatabase;
import com.PointOfSaleSystem.StoreDatabase.EmployeeDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import org.bson.conversions.Bson;

public class CreateCustomerAccountController extends CentralPointOfSalesController {

    // Define instance variables
    private CustomerDatabase customerDB;
    private EmployeeDatabase employeeDB;
    private CentralPointOfSalesController centralPointOfSalesController;
    private CustomerAccountInfoController customerAccountInfoController;

    // Define class constructor
    public CreateCustomerAccountController(CentralPointOfSalesController controller) {

        super(controller);

        // Connect to store database and initialise "customer_account" collection
        customerDB = CustomerDatabase.getInstance();
        customerDB.initialiseCustomersCollection();

        // Get the CustomerAccountInfoController
        customerAccountInfoController = centralPointOfSalesController.getCustomerAccountInfoController();

    }

    // Create a customer account based on user input
    public void createCustomerAccount(
            String customerFirstName, String customerLastName,
            long phoneNumber, String emailAddress
    ) {
        System.out.println("Creating customer account: ");
        customerAccountInfoController.setCustomerFirstName(customerFirstName);
        customerAccountInfoController.setCustomerLastName(customerLastName);
        customerAccountInfoController.setPhoneNumber(phoneNumber);
        customerAccountInfoController.setEmailAddress(emailAddress);
        customerAccountInfoController.setGuestMode(false);
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
}
