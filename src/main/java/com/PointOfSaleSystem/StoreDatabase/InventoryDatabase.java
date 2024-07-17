package com.PointOfSaleSystem.StoreDatabase;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class InventoryDatabase extends Database {

    // Define instance variables
    private static InventoryDatabase inventoryDB =  null;
    private MongoCollection<Document> inventoryCollection = null;
    private Document matchingProduct;

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

    // Check if the scanned upc is in the database
    public boolean isProductUPCInDB(long upc) {

        // Create a filter for the scanned product
        Document filter = new Document("variants.upc", upc);

        // Get the iterables which contains the documents matching the filter
        matchingProduct = inventoryCollection.find(filter).first();

        // Return true if productUPC is in DB; else it returns false
        return matchingProduct != null;
    }

    public static void main(String[] args) {
        InventoryDatabase inventoryDB = InventoryDatabase.getInstance();
        inventoryDB.initialiseInventoryCollection();
        long upc = 4006381333932L;
        System.out.println(inventoryDB.isProductUPCInDB(upc));
    }
}
