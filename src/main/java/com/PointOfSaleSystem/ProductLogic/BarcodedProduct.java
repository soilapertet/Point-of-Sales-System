package com.PointOfSaleSystem.ProductLogic;

public class BarcodedProduct {

    // Define the instance variables
    private String productName;
    private long productUPC;
    private double price;
    private int quantity;
    private double shoeSize;
    private String clothingSize;
    private String colour;

    // Define the class constructor
    public BarcodedProduct(long productUPC) {
        this.productUPC = productUPC;
    }

    // Define the getter methods
    public long getProductUPC() { return productUPC; }

}
