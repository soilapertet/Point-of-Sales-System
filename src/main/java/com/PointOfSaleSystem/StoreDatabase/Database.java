package com.PointOfSaleSystem.StoreDatabase;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import javax.xml.crypto.Data;

public class Database {

    // Initialise instance variables
    private MongoClient mongoClient;
    protected Database baseDB;

    // Initialise private class constructor
    private Database() {

        // Initialise MongoDB connection
        MongoClientSettings settings = connectToStoreDatabase();
        mongoClient = MongoClients.create(settings);
    }

    public Database getInstance() {

        if(baseDB == null) {
            baseDB = new Database();
        }

        return baseDB;
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
}
