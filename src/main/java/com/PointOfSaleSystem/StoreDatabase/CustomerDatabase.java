package com.PointOfSaleSystem.StoreDatabase;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;
import java.util.Iterator;
import java.util.Random;

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

                // Connect to the "customer_accounts" collection
                customersCollection = database.getCollection("customer_accounts");

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    // Check if customer is in database through their first and last name
    public boolean isInCustomerDatabase(String fName, String lName) {
        Bson filter = Filters.and(Filters.eq("firstName", fName),
                Filters.eq("lastName", lName));
        FindIterable<Document> matchingDocs = customersCollection.find(filter);
        return matchingDocs.iterator().hasNext();
    }

    // Check if customer is in database through their phone number
    public boolean isInCustomerDatabase(long phoneNumber) {
        Bson filter = Filters.eq("phoneNumber", phoneNumber);
        FindIterable<Document> matchingDocs = customersCollection.find(filter);
        return matchingDocs.iterator().hasNext();
    }

    // Check if customer is in database through their email address
    public boolean isInCustomerDatabase(String emailAddress) {
        Bson filter = Filters.eq("emailAddress", emailAddress);
        FindIterable<Document> matchingDocs = customersCollection.find(filter);
        return matchingDocs.iterator().hasNext();
    }

    // Check if customer is in database through their membership ID
    public boolean isInCustomerDatabase(int membershipID) {
        Bson filter = Filters.eq("membershipID", membershipID);
        FindIterable<Document> matchingDocs = customersCollection.find(filter);
        return matchingDocs.iterator().hasNext();
    }


    // Define a method to create a new customer and add them to the database
    public void addCustomerToDB(String fName, String lName, long phoneNumber, String email) {

        if(!isInCustomerDatabase(fName, lName) || !isInCustomerDatabase(phoneNumber) ||
        !isInCustomerDatabase(email)) {
            Document newCustomerAcc = new Document()
                    .append("firstName", fName)
                    .append("lastName", lName)
                    .append("phoneNumber", phoneNumber)
                    .append("emailAddress", email)
                    .append("membershipID", generateMembershipID());

            System.out.println("Adding customer to database ...");
            customersCollection.insertOne(newCustomerAcc);
            System.out.println("Customer has been added to database successfully!!");
        } else {
            System.err.println("Customer is already in database.");
        }
    }

    // Generate a membershipID when creating a new customer
    private int generateMembershipID() {

        // Generate a random 7-digit number to serve as the membershipID
        Random random = new Random();
        int sampleMembershipID = 8000000 + (random.nextInt(900000) + 100000);

        // Check if the number generated has already been assigned
        FindIterable<Document> documents = customerDB.customersCollection.find();
        Iterator<Document> documentsIterator = documents.iterator();

        // Loop through the customer_accounts collection
        while(documentsIterator.hasNext()) {

            // If the membershipID generated already exists, generate a new one
            if(documentsIterator.next().getInteger(("membershipID")) == sampleMembershipID) {
                sampleMembershipID = 8000000 + (random.nextInt(900000) + 100000);
            }
        }

        return sampleMembershipID;
    }
    // Define getter methods
    public MongoCollection<Document> getCustomersCollection() { return customersCollection; }
}
