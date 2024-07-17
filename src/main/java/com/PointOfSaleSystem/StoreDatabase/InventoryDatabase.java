package com.PointOfSaleSystem.StoreDatabase;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;

public class InventoryDatabase extends Database {

    // Define instance variables
    private static InventoryDatabase inventoryDB =  null;
    private MongoCollection<Document> inventoryCollection = null;

    // Define the class constructor
    private InventoryDatabase() {
        super();
    }

    // Define the getInstance() method for the inventoryDB class
    public static InventoryDatabase getInstance() {

        if(inventoryDB == null) {
            inventoryDB = new InventoryDatabase();
        }

        return inventoryDB;
    }

    // Get the "inventory" collection from MongoDB
    public void initialiseInventoryCollection() {

        if(inventoryCollection == null) {
            try{

                // Connect to the "Elite-Sports" database
                MongoDatabase database = super.getMongoClient().getDatabase("Elite-Sports");

                // Connect to the "inventory" collection
                inventoryCollection = database.getCollection("inventory");
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}
