package com.PointOfSaleSystem.Controllers;

import com.PointOfSaleSystem.ProductLogic.BarcodedProduct;
import com.PointOfSaleSystem.StoreDatabase.InventoryDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ScanProductsController {

    // Define the instance variables
    private InventoryDatabase inventoryDB;
    private List<BarcodedProduct> scannedProducts;
    private BarcodedProduct scannedProduct;
    private double totalPrice;
    private double subtotalPrice;
    private long scannedUPC;

    // Define the class constructor
    public ScanProductsController() {
        inventoryDB = InventoryDatabase.getInstance();
        inventoryDB.initialiseInventoryCollection();
        this.scannedProducts = new ArrayList<>();
    }

    // Scan the upc of the product
    private void scanUPC() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Scan item here: ");
        this.scannedUPC = scanner.nextLong();
    }

    // Create an instance of the scanned product and add it to the "cart" list
    private void addBarcodedProducts() {
        BarcodedProduct barcodedProduct = new BarcodedProduct(this.scannedUPC);
        this.scannedProducts.add(barcodedProduct);

        // Testing purposes
        System.out.println(barcodedProduct.getProductName());
        System.out.println(barcodedProduct.getPrice());
        System.out.println(barcodedProduct.getColour());
        System.out.println(barcodedProduct.getClothingSize());
        System.out.println(barcodedProduct.getShoeSize());
    }

    // Main method which deals with scanning products
    public void scanBarcodeProduct() {

        // 1. Scan the upc on the product
        scanUPC();

        // 2. Check if upc is in database;
        // a. If yes, add product to scannedProducts arraylist
        // b. Else, display error message
        if(inventoryDB.isProductUPCInDB(scannedUPC)) {
            addBarcodedProducts();
        } else {
            System.err.println("Scanned upc cannot be found in the inventory database.");
        }
    }

    // Define getter method
    public long getScannedUPC() { return scannedUPC; }
    public List<BarcodedProduct> getScannedProducts() { return  scannedProducts; }

    public static void main(String[] args) {
        ScanProductsController scanProductsController = new ScanProductsController();
        scanProductsController.scanBarcodeProduct();
    }
}
