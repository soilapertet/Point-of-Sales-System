package com.PointOfSaleSystem.Controllers;

import com.PointOfSaleSystem.ProductLogic.BarcodedProduct;

public class VoidController {

    // Define instance variables
    private BarcodedProduct productToVoid;

    // Define the class constructor
    public VoidController() { }

    // Define getter methods
    public BarcodedProduct getProductToVoid() {
        return productToVoid;
    }
}
