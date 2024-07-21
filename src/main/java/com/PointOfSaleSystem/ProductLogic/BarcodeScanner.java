package com.PointOfSaleSystem.ProductLogic;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BarcodeScanner {

    // Define the instance variables
    private List<BarcodedProduct> scannedProducts;
    private BarcodedProduct scannedProduct;
    private double totalPrice;
    private double subtotalPrice;
    private long scannedUPC;

    // Define the class constructor
    public BarcodeScanner() { }

    // Scan the upc on the product
    public void scanBarcodeProduct() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Scan item here: ");
        scannedUPC = scanner.nextLong();
    }

    // Define getter method
    public long getScannedUPC() { return scannedUPC; }

    public static void main(String[] args) {
        BarcodeScanner barcodeScanner = new BarcodeScanner();
        barcodeScanner.scanBarcodeProduct();
        System.out.println(barcodeScanner.getScannedUPC());
    }
}
