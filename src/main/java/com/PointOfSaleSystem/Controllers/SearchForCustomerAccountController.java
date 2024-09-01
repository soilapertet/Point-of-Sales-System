package com.PointOfSaleSystem.Controllers;

import com.PointOfSaleSystem.CentralPOSLogic.CentralPointOfSalesController;
import com.PointOfSaleSystem.StoreDatabase.CustomerDatabase;
import com.PointOfSaleSystem.StoreDatabase.EmployeeDatabase;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

public class SearchForCustomerAccountController extends CentralPointOfSalesController {

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
    public SearchForCustomerAccountController(CentralPointOfSalesController controller) {

        super(controller);

        // Connect to store database and initialise "customer_account" collection
        customerDB = CustomerDatabase.getInstance();
        customerDB.initialiseCustomersCollection();

        // Connect to store database and initialise "employees" collection
        employeeDB = EmployeeDatabase.getInstance();
        employeeDB.initialiseEmployeesCollection();

        this.guestMode = true;
        this.staffPurchase = false;
    }

    // Search for customer by phone number
    public void searchForCustomerAccount(long inputPhoneNumber) throws Exception {

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

    // Search for the customer by email
    public void searchForCustomerAccount(String inputEmail) throws Exception {

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

    // Search for the customer by first and last name
    public void searchForCustomerAccount(String fNameInput, String lNameInput) throws Exception {

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

    // Search for the customer by the provided ID
    public void searchForCustomerAccount(int inputID) throws Exception {

        boolean isAStaffMember;

        // Initialise input details
        this.inputID = inputID;

        // Check if provided membershipID is an employeeID
        isAStaffMember = employeeDB.isInEmployeeDatabase(this.inputID);

        // Check if customer is a staff member
        if(isAStaffMember) {
            setEmployeeInfo();
        } else {
            initialiseNonStaffPurchaseSession();
        }
    }

    // Define a method to check if the user has a customer account
    private boolean hasACustomerAccount() {

        if(inputPhoneNumber != 0) {
            return customerDB.isInCustomerDatabase(inputPhoneNumber);
        } else if(inputEmail != null) {
            return customerDB.isInCustomerDatabase(inputEmail);
        } else if(inputID != 0) {
            return customerDB.isInCustomerDatabase(inputID);
        } else {
            return customerDB.isInCustomerDatabase(firstNameInput, lastNameInput);
        }
    }

    // Initialise a non-staff purchase session
    private void initialiseNonStaffPurchaseSession() throws Exception {

        boolean isCustomerAMember;

        isCustomerAMember = hasACustomerAccount();

        // Set customer details if they have a membership
        if(isCustomerAMember) {
            setCustomerInfo();
        } else {
            throw new Exception("Customer account could not be found with the provided ID.");
        }

    }

    // Set the customer info based on customer details in customer DB
    private void setCustomerInfo() {

        Bson filter; FindIterable<Document> matchingDocs;

        if(inputEmail != null) {
            filter = Filters.eq("emailAddress", inputEmail);
        } else if(inputPhoneNumber != 0) {
            filter = Filters.eq("phoneNumber", inputPhoneNumber);
        } else if(inputID != 0) {
            filter = Filters.eq("membershipID", inputID);
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
    }

    private void setEmployeeInfo() {

        Bson filter = null;
        FindIterable<Document> matchingDocs;

        if(inputID != 0) {
            filter = Filters.eq("employeeID", inputID);
        }

        matchingDocs = employeeDB.getEmployeesCollection().find(filter);
        this.uniqueID = matchingDocs.first().getObjectId("_id");
        this.membershipID = matchingDocs.first().getInteger("employeeID");
        this.employeeName = matchingDocs.first().getString("employeeName");

        this.staffPurchase = true;
        this.guestMode = false;
    }

    // Define getter methods
    public long getPhoneNumber() { return phoneNumber; }

    public String getCustomerFirstName() { return customerFirstName; }

    public String getCustomerLastName() { return customerLastName; }

    public String getEmailAddress() { return emailAddress; }

    public String getEmployeeName() { return employeeName; }

    public int getMembershipID() { return membershipID; }

    public ObjectId getUniqueID() { return uniqueID; }

    public boolean isStaffPurchase() { return staffPurchase; }

    public boolean isGuestMode() { return guestMode; }
}
