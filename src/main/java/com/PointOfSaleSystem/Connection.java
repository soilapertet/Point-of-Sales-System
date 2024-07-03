package com.PointOfSaleSystem;


import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.*;
import org.bson.Document;

import java.util.Iterator;

public class Connection {
    public static void main(String[] args) {
        String connectionString = "mongodb+srv://nicolepertet:vinsmoke.20.07@pos-cluster.wy7nnbe.mongodb.net/?retryWrites=true&w=majority&appName=POS-Cluster";

        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .serverApi(serverApi)
                .build();

        // Create a new client and connect to the server
        try (MongoClient mongoClient = MongoClients.create(settings)) {
            try {

                // Connect to the "Elite-Sports" database
                MongoDatabase database = mongoClient.getDatabase("Elite-Sports");

                // Connect to the "employees" collection
                MongoCollection collection = database.getCollection("employees");

                // Retrieve and print each employee's info
                FindIterable<Document> employeesDoc = collection.find();
                Iterator employees = employeesDoc.iterator();

                while(employees.hasNext()) {
                    System.out.println(employees.next());
                }

            } catch (MongoException e) {
                e.printStackTrace();
            }
        }
    }
}
