package com.PointOfSaleSystem.StoreDatabase;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.Iterator;

// Singleton Design pattern
public class EmployeeDatabase extends Database {

    // Initialise instance variables
    private static EmployeeDatabase employeeDB;
    private MongoClient mongoClient;
    private MongoCollection<Document> employeesCollection;

    private static Iterator<Document> storeEmployees;
    private static Iterator<Document> storeManagers;
    private static Iterator<Document> softGoodsAssociates;
    private static Iterator<Document> hardGoodsAssociates;
    private static Iterator<Document> footwearAssociates;
    private static Iterator<Document> storeCashiers;
    private static Iterator<Document> ecomAssociates;

    private EmployeeDatabase() {
        super();
    };

    public static EmployeeDatabase getInstance() {

        if(employeeDB == null) {
            employeeDB = new EmployeeDatabase();
        }

        return employeeDB;
    }

    // Get the "employees" collection from the database
    public void initialiseEmployeesCollection() {

        if(employeesCollection == null) {
            try {
                // Connect to the "Elite-Sports" database
                MongoDatabase database = super.getMongoClient().getDatabase("Elite-Sports");

                // Connect to the "employees" collection
                employeesCollection = database.getCollection("employees");

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    // Define getter methods
    public Iterator<Document> getStoreEmployees() {

        FindIterable<Document> employeesDocs = employeesCollection.find();
        storeEmployees = employeesDocs.iterator();
        return storeEmployees;
    }

    public Iterator<Document> getStoreManagers() {

        Bson filter = Filters.eq("title", "Manager");
        FindIterable<Document> managersDocs = employeesCollection.find(filter);
        storeManagers = managersDocs.iterator();

        return storeManagers;
    }

    public Iterator<Document> getSoftGoodsAssociates() {

        Bson filter = Filters.eq("department", "Softgoods");
        FindIterable<Document> softGoodsEmployeeDocs = employeesCollection.find(filter);
        softGoodsAssociates = softGoodsEmployeeDocs.iterator();

        return softGoodsAssociates;
    }

    public Iterator<Document> getHardGoodsAssociates() {

        Bson filter = Filters.eq("department", "Hardgoods");
        FindIterable<Document> hardGoodsEmployeeDocs = employeesCollection.find(filter);
        hardGoodsAssociates = hardGoodsEmployeeDocs.iterator();

        return hardGoodsAssociates;
    }

    public Iterator<Document> getFootwearAssociates() {

        Bson filter = Filters.eq("department", "Footwear");
        FindIterable<Document> footwearEmployeeDocs = employeesCollection.find(filter);
        footwearAssociates = footwearEmployeeDocs.iterator();

        return footwearAssociates;
    }

    public Iterator<Document> getEcomAssociates() {

        Bson filter = Filters.eq("department", "E-Commerce");
        FindIterable<Document> ecomAssociatesDocs = employeesCollection.find(filter);
        ecomAssociates = ecomAssociatesDocs.iterator();

        return ecomAssociates;
    }

    public Iterator<Document> getStoreCashiers() {

        Bson filter = Filters.or(Filters.eq("department", "Cash"),
                Filters.eq("title", "Manager"));
        FindIterable<Document> cashiersDocs = employeesCollection.find(filter);
        storeCashiers = cashiersDocs.iterator();

        return storeCashiers;
    }

    public MongoCollection<Document> getEmployeesCollection() {
        return employeesCollection;
    }

    // Check if employee ID is in store database
    public boolean isInEmployeeDatabase(int id) {

        Bson filter = Filters.eq("employeeID", id);
        FindIterable<Document> matchingDocs = employeesCollection.find(filter);

        return matchingDocs.first() != null;
    }

    // Check if employee is a manager
    public boolean isAManager(int id) {

        Bson filter = Filters.and(Filters.eq("employeeID", id),
                Filters.eq("title", "Manager"));
        FindIterable<Document> matchingDocs = employeesCollection.find(filter);
        return matchingDocs.first() != null;

    }

    // Check if employee is in the Softgoods department
    public boolean isInSoftgoodsDep(int id) {

        Bson filter = Filters.and(Filters.eq("employeeID", id),
                Filters.eq("department", "Softgoods"));
        FindIterable<Document> matchingDocs = employeesCollection.find(filter);
        return matchingDocs.first() != null;

    }

    // Check if employee is in the Hardgoods department
    public boolean isInHardgoodsDep(int id) {

        Bson filter = Filters.and(Filters.eq("employeeID", id),
                Filters.eq("department", "Hardgoods"));
        FindIterable<Document> matchingDocs = employeesCollection.find(filter);
        return matchingDocs.first() != null;

    }

    // Check if employee is in the Footwear department
    public boolean isInFootwearDep(int id) {

        Bson filter = Filters.and(Filters.eq("employeeID", id),
                Filters.eq("department", "Footwear"));
        FindIterable<Document> matchingDocs = employeesCollection.find(filter);
        return matchingDocs.first() != null;

    }

    // Check if employee is in the Cash department
    public boolean isInCashDep(int id) {

        Bson filter = Filters.or(
                Filters.and(Filters.eq("employeeID", id), Filters.eq("title", "Manager")),
                Filters.and(Filters.eq("employeeID", id), Filters.eq("department", "Cash"))
        );

        FindIterable<Document> matchingDocs = employeesCollection.find(filter);
        return matchingDocs.first() != null;

    }

/*    Main method for testing and debugging purposes */
    public static void main(String[] args) {
        EmployeeDatabase employeeDB = EmployeeDatabase.getInstance();
        employeeDB.initialiseEmployeesCollection();
        System.out.println(employeeDB.isAManager(112240));
    }

}

