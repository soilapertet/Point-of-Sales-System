package com.PointOfSaleSystem.StoreDatabase;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.Iterator;

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

    // Check if customer is in database through their first and last name
    public boolean isInCustomerDatabase(String fName, String lName) {

        boolean isInCustomerDB;

        Bson filter = Filters.and(Filters.eq("firstName", fName),
                Filters.eq("lastName", lName));

        FindIterable<Document> matchingDocs = customersCollection.find(filter);
        isInCustomerDB = matchingDocs.iterator().hasNext();

        return isInCustomerDB;
    }


    public static void main(String[] args) {
        CustomerDatabase customerDB = CustomerDatabase.getInstance();
        customerDB.initialiseCustomersCollection();

        System.out.println(customerDB.isInCustomerDatabase("Soila", "Pertet"));
        System.out.println(customerDB.isInCustomerDatabase("Ginny", "Weasley"));
    }
}
