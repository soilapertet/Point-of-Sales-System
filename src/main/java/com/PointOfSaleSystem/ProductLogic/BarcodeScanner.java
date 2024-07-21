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
        BarcodedProduct barcodedProduct;

        // 1. Get the scanned upc
        barcodeScanner.scanBarcodeProduct();

        // 2a. Check if scanned UPC is in the database
        // 2b. If in database, create a new Barcode product
        // 2c. Else, print error message
        if(barcodeScanner.isScannedUPCInDB()) {
           barcodedProduct = new BarcodedProduct(barcodeScanner.getScannedUPC());
            System.out.println(barcodedProduct.getProductName());
            System.out.println(barcodedProduct.getPrice());
            System.out.println(barcodedProduct.getColour());
            System.out.println(barcodedProduct.getClothingSize());
            System.out.println(barcodedProduct.getShoeSize());
        } else {
            System.err.println("Scanned UPC could not be found in the inventory database");
        }
    }
}
