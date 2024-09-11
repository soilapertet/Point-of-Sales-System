package com.PointOfSaleSystem.CustomerLogic;

import com.PointOfSaleSystem.CentralPOSLogic.CentralPointOfSalesController;
import com.PointOfSaleSystem.StaffPurchase.EmployeeInfoController;
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
    private CustomerAccountInfoController customerAccountInfoController;
    private EmployeeInfoController employeeInfoController;

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

        // Get the instance of the CustomerAccountInfoController class
        customerAccountInfoController = this.getCentralPOSController().getCustomerAccountInfoController();
        employeeInfoController = this.getCentralPOSController().getEmployeeInfoController();
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

        Bson filter; Document matchingDoc;

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

        matchingDoc = customerDB.getCustomersCollection().find(filter).first();
        customerAccountInfoController.setUniqueID(matchingDoc.getObjectId("_id"));
        customerAccountInfoController.setMembershipID(matchingDoc.getInteger("membershipID"));
        customerAccountInfoController.setCustomerFirstName(matchingDoc.getString("firstName"));
        customerAccountInfoController.setCustomerLastName(matchingDoc.getString("lastName"));
        customerAccountInfoController.setPhoneNumber(matchingDoc.getLong("phoneNumber"));
        customerAccountInfoController.setEmailAddress(matchingDoc.getString("emailAddress"));
        customerAccountInfoController.setGuestMode(false);
    }

    private void setEmployeeInfo() {

        Bson filter = null; Document matchingDoc;

        // Checks if the input ID has been provided i.e. not empty
        if(inputID != 0) {
            filter = Filters.eq("employeeID", inputID);
        }

        // Return the first result
        matchingDoc = employeeDB.getEmployeesCollection().find(filter).first();

        // Set the employee info for the staff purchase
        employeeInfoController.setUniqueID(matchingDoc.getObjectId("_id"));
        employeeInfoController.setEmployeeID(matchingDoc.getInteger("employeeID"));
        employeeInfoController.setEmployeeName(matchingDoc.getString("employeeName"));
        employeeInfoController.setEmploymentType(matchingDoc.getString("employmentType"));
        employeeInfoController.setStaffPurchase(true);
        employeeInfoController.setGuestMode(false);

        initialiseDiscountLimit();
    }

    // Set staff purchase discount limit according to employment type
    private void initialiseDiscountLimit() {
        if(employeeInfoController.getEmploymentType().equals("Full time")) {
            employeeInfoController.setStaffDiscountLimit(4000.0);
        } else if(employeeInfoController.getEmploymentType().equals("Part time")) {
            employeeInfoController.setStaffDiscountLimit(2000.0);
        } else {
            employeeInfoController.setStaffDiscountLimit(1000.0);
        }
    }
}
