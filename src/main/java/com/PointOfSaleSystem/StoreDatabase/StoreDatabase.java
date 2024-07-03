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

    private List storeEmployees = new ArrayList();
    private List storeManagers = new ArrayList();
    private List softGoodsAssociates = new ArrayList();
    private List hardGoodsAssociates = new ArrayList();
    private List storeCashiers = new ArrayList();
    private List ecomAssociates = new ArrayList();

    private StoreDatabase() { };

    public static StoreDatabase getInstance() {

        if(storeDB == null) {
            storeDB = new StoreDatabase();
        }

        return storeDB;
    }

    // Initialise a method to connect to our MongoDB database
    private void connectToDatabase() {
        String connectionString = "mongodb+srv://nicolepertet:vinsmoke.20.07@pos-cluster.wy7nnbe.mongodb.net/?retryWrites=true&w=majority&appName=POS-Cluster";

        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .serverApi(serverApi)
                .build();
    }
}

