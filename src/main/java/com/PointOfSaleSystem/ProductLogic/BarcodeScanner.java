package com.PointOfSaleSystem.ProductLogic;

import com.PointOfSaleSystem.StoreDatabase.InventoryDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BarcodeScanner {

    // Define the instance variables
    private InventoryDatabase inventoryDB;
    private List<BarcodedProduct> scannedProducts;
    private BarcodedProduct scannedProduct;
    private double totalPrice;
    private double subtotalPrice;
    private long scannedUPC;

    // Define the class constructor
    public BarcodeScanner() {
        inventoryDB = InventoryDatabase.getInstance();
        inventoryDB.initialiseInventoryCollection();
    }

    // Scan the upc of the product
    public void scanBarcodeProduct() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Scan item here: ");
        scannedUPC = scanner.nextLong();
    }

    // Check if the upc is in the "inventory" database
    public boolean isScannedUPCInDB() {
        return inventoryDB.isProductUPCInDB(scannedUPC);
    }


    // Define getter method
    public long getScannedUPC() { return scannedUPC; }

    public static void main(String[] args) {
        BarcodeScanner barcodeScanner = new BarcodeScanner();

        // 1. Get the scanned upc
        barcodeScanner.scanBarcodeProduct();
        System.out.println(barcodeScanner.getScannedUPC());

        // 2. Check if scanned UPC is in the database
        System.out.println(barcodeScanner.isScannedUPCInDB());
    }
}
