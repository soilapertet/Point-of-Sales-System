package com.PointOfSaleSystem.ProductLogic;

import com.PointOfSaleSystem.CentralPOSFacade.CentralPointOfSalesFacade;

import java.text.DecimalFormat;
import java.util.List;

public class BarcodedProductManagement  extends CentralPointOfSalesFacade {

    // Define instance variables
    private boolean discountApplied;
    private double discountedPrice;
    private final DecimalFormat DECIMALFORMAT;

    // Define class constructor
    public BarcodedProductManagement(CentralPointOfSalesFacade facade) {
        super(facade);
        DECIMALFORMAT = new DecimalFormat("#.00");
    }

    // Apply discount by percent
    public void applyDiscountByPercent(int percent, long upc) {

        // 1. Get the product associated with the provided upc
        List<BarcodedProduct> scannedProducts = this.getCentralPOSFacade().getScanProductsController().getScannedProducts();

        for(BarcodedProduct product : scannedProducts) {
            if(product.getProductUPC() == upc) {
                // 2. Get the current price of the product and apply the discount based on the percent provided
                discountedPrice = product.getPrice() * ((100 - percent) / 100.0);
                discountedPrice = Double.parseDouble(DECIMALFORMAT.format(discountedPrice));
                discountApplied = true;

                // 3. Update values of the product
                product.setPrice(discountedPrice);
                product.setDiscountApplied(discountApplied);

                System.out.println("Discount applied. New price: $ " + product.getProductID());
                break;
            }
        }
    }

    // Define getter method
    public double getDiscountedPrice() { return discountedPrice; }
    public boolean getDiscountApplied() { return discountApplied; }
}
