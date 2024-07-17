package com.PointOfSaleSystem.StoreDatabase;

public class InventoryDatabase extends Database {

    // Define instance variables
    private static InventoryDatabase inventoryDB =  null;

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
}
