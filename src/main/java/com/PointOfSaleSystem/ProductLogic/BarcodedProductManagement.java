package com.PointOfSaleSystem.ProductLogic;

import com.PointOfSaleSystem.CentralPOSFacade.CentralPointOfSalesFacade;

public class BarcodedProductManagement  extends CentralPointOfSalesFacade {

    // Define instance variables
    private boolean discountApplied;
    private double discountedPrice;

    public BarcodedProductManagement(CentralPointOfSalesFacade facade) {
        super(facade);
    }

    // Define getter method
    public double getDiscountedPrice() { return discountedPrice; }
    public boolean getDiscountApplied() { return discountApplied; }
}
