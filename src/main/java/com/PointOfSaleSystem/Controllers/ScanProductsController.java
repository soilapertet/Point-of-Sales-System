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
    private String inputProductColour;
    private String inputClothingSize;
    private int inputShoeSize;
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
            this.scannedUPC = 0;
        } else if(scanner.hasNextLong()) {
            this.scannedProductID = 0;
            this.scannedUPC = scanner.nextLong();
        }

        System.out.println("Product UPC: " + scannedUPC);
        System.out.println("Product ID: " + scannedProductID);
    }

    // Create an instance of the scanned product and add it to the "cart" list
    private void addBarcodedProductsViaUPC() {
        barcodedProduct = new BarcodedProduct(this.scannedUPC);
        this.scannedBarcodedProducts.add(barcodedProduct);

        // Testing purposes
        System.out.println(barcodedProduct.getProductName());
        System.out.println(barcodedProduct.getPrice());
        System.out.println(barcodedProduct.getColour());
        System.out.println(barcodedProduct.getClothingSize());
        System.out.println(barcodedProduct.getShoeSize());
    }

    private void addBarcodedProductsViaProductID() {

        getInputProductColour();;
        getInputProductSize();

        if(inventoryDB.getMatchingProduct().get("category").equals("Softgoods")) {
            barcodedProduct = new BarcodedProduct(this.scannedProductID, this.inputProductColour, this.inputClothingSize);
        } else if(inventoryDB.getMatchingProduct().get("category").equals("Footwear")) {
            // barcodedProduct = new BarcodedProduct(this.scannedProductID, this.inputProductColour, this.inputShoeSize);
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

        // 2a. Check if input is the product upc
        //  2b. If yes, check if the product upc is in the database
        //      2c. If yes, create an instance of the barcoded product using its upc
        //      2d. If no, display error message
        // 2e. Check if input is the product id
        //  2f. If yes, check if the product id is in the database
        //      2g. If yes, create an instance of the barcoded product using its product id
        //      2h. If no, display error message
        if(scannedUPC != 0) {
            if(inventoryDB.isProductUPCInDB(scannedUPC)) {
                addBarcodedProductsViaUPC();
                updateSubtotalPrice();
                calculateTotalPrice();
            } else {
                System.out.println("Scanned UPC cannot be found in the inventory database");
            }
        } else if(scannedProductID != 0) {
            if(inventoryDB.isProductIDInDB(scannedProductID)) {
                addBarcodedProductsViaProductID();
                updateSubtotalPrice();
                calculateTotalPrice();
            } else {
                System.out.println("Scanned Product ID cannot be found in the inventory database");
            }
        }
    }

    // Get the colour of the scanned product
    private void getInputProductColour() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter product colour: ");
        this.inputProductColour =  scanner.nextLine();
    }

    // Get the colour of the scanned product
    private void getInputProductSize() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter product size: ");

        if(scanner.hasNextInt()) {
            this.inputShoeSize = scanner.nextInt();
        } else {
            this.inputClothingSize = scanner.next();
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
        scanProductsController.scanBarcodeProduct();

        scanProductsController.scanProduct();
        scanProductsController.scanBarcodeProduct();

    }
}
