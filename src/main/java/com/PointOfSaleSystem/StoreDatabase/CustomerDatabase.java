package com.PointOfSaleSystem.StoreDatabase;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;

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

        boolean isInCustomerDB;

        Bson filter = Filters.and(Filters.eq("firstName", fName),
                Filters.eq("lastName", lName));

        FindIterable<Document> matchingDocs = customersCollection.find(filter);
        isInCustomerDB = matchingDocs.iterator().hasNext();

        setCustomerInfo(isInCustomerDB, matchingDocs);

        return isInCustomerDB;
    }

    // Check if customer is in database through their phone number
    public boolean isInCustomerDatabase(long phoneNumber) {

        boolean isInCustomerDB;

        Bson filter = Filters.eq("phoneNumber", phoneNumber);
        FindIterable<Document> matchingDocs = customersCollection.find(filter);
        isInCustomerDB = matchingDocs.iterator().hasNext();

        setCustomerInfo(isInCustomerDB, matchingDocs);

        return isInCustomerDB;
    }

    // Check if customer is in database through their email address
    public boolean isInCustomerDatabase(String emailAddress) {

        boolean isInCustomerDB;

        Bson filter = Filters.eq("emailAddress", emailAddress);
        FindIterable<Document> matchingDocs = customersCollection.find(filter);
        isInCustomerDB = matchingDocs.iterator().hasNext();

        setCustomerInfo(isInCustomerDB, matchingDocs);

        return isInCustomerDB;
    }

    public void setCustomerInfo(boolean isInDB, FindIterable<Document> iter) {

        if(isInDB) {
            this.customerFirstName = iter.first().getString("firstName");
            this.customerLastName = iter.first().getString("lastName");
            this.phoneNumber = iter.first().getLong("phoneNumber");
            this.emailAddress = iter.first().getString("emailAddress");
        }

    }

    // Define getter methods
    public long getPhoneNumber() {
        return phoneNumber;
    }

    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public static void main(String[] args) {
        CustomerDatabase customerDB = CustomerDatabase.getInstance();
        customerDB.initialiseCustomersCollection();

        System.out.println(customerDB.isInCustomerDatabase("Soila", "Pertet"));
        System.out.println(customerDB.isInCustomerDatabase("Ginny", "Weasley"));

        long phone1 = 8253659243L;
        long phone2 = 4032553653L;
        System.out.println(customerDB.isInCustomerDatabase(phone1));
        System.out.println(customerDB.isInCustomerDatabase(phone2));

        String email1 = "nicolepertet@gmail.com";
        String email2 = "king0fTheNorth@telus.net";
        String email3 = "theBoyWhoLived@gmail.com";

        System.out.println(customerDB.isInCustomerDatabase(email2));

        System.out.println(customerDB.isInCustomerDatabase(email3));
        System.out.println(customerDB.getCustomerFirstName());
        System.out.println(customerDB.getCustomerLastName());
        System.out.println(customerDB.getPhoneNumber());
        System.out.println(customerDB.getEmailAddress());

        long phone3 = 4037623008L;
        System.out.println(customerDB.isInCustomerDatabase(phone3));
        System.out.println(customerDB.getCustomerFirstName());
        System.out.println(customerDB.getCustomerLastName());
        System.out.println(customerDB.getPhoneNumber());
        System.out.println(customerDB.getEmailAddress());
    }
}
