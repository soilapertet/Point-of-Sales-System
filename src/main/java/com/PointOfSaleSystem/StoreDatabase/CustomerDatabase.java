package com.PointOfSaleSystem.StoreDatabase;

public class CustomerDatabase extends Database {

    // Instance variables
    private static CustomerDatabase customerDB = null;
    private String customerFirstName;
    private String customerLastName;
    private long phoneNumber;
    private String emailAddress;

    // Private constructor (Singleton Design Pattern)
    private CustomerDatabase() {
        super();
    }

    // getInstance method to access CustomerDatabase Class
    public static CustomerDatabase getInstance() {

        if(customerDB == null) {
            customerDB = new CustomerDatabase();
        }

        return customerDB;
    }



}
