package com.PointOfSaleSystem.StoreDatabase;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.List;

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

    // Chec if the provided sku is in the database
    public boolean isProductIDInDB(int productID) {

        // Create a filter using the provided sku
        Bson filter = Filters.eq("product_id", productID);

        // Find document with matching sku
        matchingProduct = inventoryCollection.find(filter).first();

        // Check if matchingProduct exists or is null
        return matchingProduct != null;
    }

    // Define getter methods
    public Document getMatchingProduct() {
        return matchingProduct;
    }

    public static void main(String[] args) {
        InventoryDatabase inventoryDB = InventoryDatabase.getInstance();
        inventoryDB.initialiseInventoryCollection();

        long upc = 8901030726912L;
        System.out.println(inventoryDB.isProductUPCInDB(upc));
        System.out.println(inventoryDB.getMatchingProduct());

        int sku = 25828179;
        System.out.println(inventoryDB.isProductIDInDB(sku));
        System.out.println(inventoryDB.getMatchingProduct());
    }
}
