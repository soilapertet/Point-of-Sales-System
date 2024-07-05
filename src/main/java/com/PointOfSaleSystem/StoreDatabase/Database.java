package com.PointOfSaleSystem.StoreDatabase;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

public class Database {

    // Initialise instance variables
    private MongoClient mongoClient;

    // Initialise private class constructor
    public Database() {

        // Initialise MongoDB connection
        MongoClientSettings settings = connectToStoreDatabase();
        mongoClient = MongoClients.create(settings);

    }

    // Initialise a method to connect to MongoDB database
    private MongoClientSettings connectToStoreDatabase() {

        String connectionString = "mongodb+srv://nicolepertet:vinsmoke.20.07@pos-cluster.wy7nnbe.mongodb.net/?retryWrites=true&w=majority&appName=POS-Cluster";

        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();

        return MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .serverApi(serverApi)
                .build();
    }

    // Getter method for MongoClient
    public MongoClient getMongoClient() {
        return mongoClient;
    }

}
