package com.PointOfSaleSystem.StoreDatabase;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class CustomerDatabase extends Database {

    // Instance variables
    private static CustomerDatabase customerDB = null;
    private MongoCollection<Document> customersCollection = null;

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

    // Get the "customer_accounts" collection from the database
    public void initialiseCustomersCollection() {

        if(customersCollection == null) {
            try {
                // Connect to the "Elite-Sports" database
                MongoDatabase database = super.getMongoClient().getDatabase("Elite-Sports");

                // Connect to the "employees" collection
                customersCollection = database.getCollection("customer_accounts");

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


}
