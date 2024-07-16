package com.PointOfSaleSystem.Controllers;

import com.PointOfSaleSystem.StoreDatabase.CustomerDatabase;

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


}
