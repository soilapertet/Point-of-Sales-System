package com.PointOfSaleSystem.Controllers;

import com.PointOfSaleSystem.ProductLogic.BarcodedProduct;
import com.PointOfSaleSystem.StoreDatabase.InventoryDatabase;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ScanProductsController {

    // Define the instance variables
    private InventoryDatabase inventoryDB;
    private List<BarcodedProduct> scannedBarcodedProducts;
    private BarcodedProduct barcodedProduct;
    private long scannedUPC;
    private int scannedProductID;
    private String inputColour;
    private String inputSize;
    private double subtotalPrice;
    private double totalPrice;
    private final double GST;
    private final DecimalFormat DECIMALFORMAT;

    // Define the class constructor
    public ScanProductsController() {
        inventoryDB = InventoryDatabase.getInstance();
        inventoryDB.initialiseInventoryCollection();
        this.scannedBarcodedProducts = new ArrayList<>();
        this.subtotalPrice = 0.0;
        this.totalPrice = 0.0;
        this.GST = 0.05;
        this.DECIMALFORMAT  = new DecimalFormat("#.00");
    }

    // Scan the upc of the product
    public void scanProduct() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Scan item here: ");

        if(scanner.hasNextInt()) {
            this.scannedProductID = scanner.nextInt();
        } else if(scanner.hasNextLong()) {
            this.scannedUPC = scanner.nextLong();
        }

        System.out.println("Product UPC: " + scannedUPC);
        System.out.println("Product ID: " + scannedProductID);
    }

    // Create an instance of the scanned product and add it to the "cart" list
    private void addBarcodedProducts() {

        if(scannedUPC != 0) {
            barcodedProduct = new BarcodedProduct(this.scannedUPC);
        } else {
            barcodedProduct = new BarcodedProduct(this.scannedProductID, this.inputColour, this.inputSize);
        }

        this.scannedBarcodedProducts.add(barcodedProduct);

        // Testing purposes
        System.out.println(barcodedProduct.getProductName());
        System.out.println(barcodedProduct.getPrice());
        System.out.println(barcodedProduct.getColour());
        System.out.println(barcodedProduct.getClothingSize());
        System.out.println(barcodedProduct.getShoeSize());
    }

    // Update the subtotal price while scanning products
    private void updateSubtotalPrice() {
        this.subtotalPrice += barcodedProduct.getPrice();

        // Testing purposes
        System.out.println("Current subtotal: $ " + this.subtotalPrice);
    }

    // Calculate the total price with taxes
    private void calculateTotalPrice() {
        this.totalPrice = (this.subtotalPrice * this.GST) + this.subtotalPrice;
        this.totalPrice = Double.parseDouble(DECIMALFORMAT.format(totalPrice));
        System.out.println("Total amount: $" + this.totalPrice);
    }

    // Main method which deals with scanning products
    public void scanBarcodeProduct() {

        // 1. Scan the upc on the product
        // scanUPC();

        // 2. Check if upc is in database;
        // a. If yes, add product to scannedProducts arraylist
        // b. Else, display error message
        if(inventoryDB.isProductUPCInDB(scannedUPC)) {
            addBarcodedProducts();
            updateSubtotalPrice();
            calculateTotalPrice();
        } else {
            System.err.println("Scanned upc cannot be found in the inventory database.");
        }
    }

    // Define getter method
    public long getScannedUPC() { return scannedUPC; }
    public List<BarcodedProduct> getScannedProducts() { return  scannedBarcodedProducts; }
    public double getSubtotalPrice() { return subtotalPrice; }
    public double getTotalPrice() { return totalPrice; }

    public static void main(String[] args) {
        ScanProductsController scanProductsController = new ScanProductsController();
        scanProductsController.scanProduct();
    }
}
