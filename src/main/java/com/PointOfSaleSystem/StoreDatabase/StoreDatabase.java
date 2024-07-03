package com.PointOfSaleSystem.StoreDatabase;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.Iterator;

// Singleton Design pattern
public class StoreDatabase {

    // Initialise instance variables
    private static StoreDatabase storeDB;
    private MongoClient mongoClient;
    private MongoCollection<Document> employeesCollection;

    private static Iterator<Document> storeEmployees;
    private static Iterator<Document> storeManagers;
    private static Iterator<Document> softGoodsAssociates;
    private static Iterator<Document> hardGoodsAssociates;
    private static Iterator<Document> storeCashiers;
    private static Iterator<Document> ecomAssociates;

    private StoreDatabase() {

        // Initialise MongoDB connection
        MongoClientSettings settings = connectToStoreDatabase();
        mongoClient = MongoClients.create(settings);
    };

    public static StoreDatabase getInstance() {

        if(storeDB == null) {
            storeDB = new StoreDatabase();
        }

        return storeDB;
    }

    // Initialise a method to connect to MongoDB database
    private static MongoClientSettings connectToStoreDatabase() {

        String connectionString = "mongodb+srv://nicolepertet:vinsmoke.20.07@pos-cluster.wy7nnbe.mongodb.net/?retryWrites=true&w=majority&appName=POS-Cluster";

        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();

        return MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .serverApi(serverApi)
                .build();


    }

    // Get the "employees" collection from the database
    private void initialiseEmployeesCollection() {

        if(employeesCollection == null) {
            try {
                // Connect to the "Elite-Sports" database
                MongoDatabase database = mongoClient.getDatabase("Elite-Sports");

                // Connect to the "employees" collection
                employeesCollection = database.getCollection("employees");

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    // Define getter methods
    public Iterator<Document> getStoreEmployees() {

        initialiseEmployeesCollection();

        FindIterable<Document> employeesDocs = employeesCollection.find();
        storeEmployees = employeesDocs.iterator();
        return storeEmployees;
    }


    public static void main(String[] args) {
        StoreDatabase storeDB = StoreDatabase.getInstance();
        storeDB.getStoreEmployees();
    }

}

