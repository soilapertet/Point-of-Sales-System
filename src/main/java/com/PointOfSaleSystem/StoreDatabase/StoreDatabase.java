package com.PointOfSaleSystem.StoreDatabase;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.*;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// Singleton Design pattern
public class StoreDatabase {

    // Initialise instance variables
    private static StoreDatabase storeDB;

    private static Iterator<Document> storeEmployees;
    private List<Document> storeManagers = new ArrayList<>();
    private List<Document> softGoodsAssociates = new ArrayList<>();
    private List<Document> hardGoodsAssociates = new ArrayList<>();
    private List<Document> storeCashiers = new ArrayList<>();
    private List<Document> ecomAssociates = new ArrayList<>();

    private StoreDatabase() { };

    public static StoreDatabase getInstance() {

        if(storeDB == null) {
            storeDB = new StoreDatabase();
        }

        return storeDB;
    }

    // Initialise a methods to connect to MongoDB database
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

    private static void getStoreEmployeesFromDB() {

        MongoClientSettings settings = connectToStoreDatabase();

        // Create a new client and connect to the server
        try (MongoClient mongoClient = MongoClients.create(settings)) {
            try {

                // Connect to the "Elite-Sports" database
                MongoDatabase database = mongoClient.getDatabase("Elite-Sports");

                // Connect to the "employees" collection
                MongoCollection<Document> employees = database.getCollection("employees");

                // Retrieve all store employees data
                FindIterable<Document> employeesDocs = employees.find();
                storeEmployees = employeesDocs.iterator();

            } catch (MongoException e) {
                e.printStackTrace();
            }
        }
    }

    // Define getter methods
    public static Iterator<Document> getStoreEmployees() {
        getStoreEmployeesFromDB();
        return storeEmployees;
    }
}

