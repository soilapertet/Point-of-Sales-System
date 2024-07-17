package com.PointOfSaleSystem.ItemLogic;

public class BarcodedProduct {

    // Define the instance variables
    private String productName;
    private String brandName;
    private int productSKU;
    private long productUPC;
    private double price;
    private int quantity;

    // Define the class constructor
    public BarcodedProduct(long productUPC) {
        this.productUPC = productUPC;
    }


}
