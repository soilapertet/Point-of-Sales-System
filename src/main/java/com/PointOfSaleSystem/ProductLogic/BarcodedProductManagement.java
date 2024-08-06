package com.PointOfSaleSystem.ProductLogic;

import com.PointOfSaleSystem.CentralPOSFacade.CentralPointOfSalesFacade;

import java.text.DecimalFormat;
import java.util.List;

public class BarcodedProductManagement  extends CentralPointOfSalesFacade {

    // Define instance variables
    private boolean discountApplied;
    private double discountedPrice;
    private double originalProductPrice;
    private double newSubtotalPrice;
    private double newTotalPrice;
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

                // 2. Set the original price of the product
                originalProductPrice = product.getPrice();

                // 3. Get the current price of the product and apply the discount based on the percent provided
                discountedPrice =  originalProductPrice * ((100 - percent) / 100.0);

                updateProductDetails(discountedPrice, product);
                updateSubtotalPrice();

                System.out.println("Discount applied. New price: $ " + discountedPrice);
                break;
            }
        }
    }

    // Apply discount by amount
    public void applyDiscountByAmount(int amount, long upc) {

        // 1. Get the product associated with the provided upc
        List<BarcodedProduct> scannedProducts = this.getCentralPOSFacade().getScanProductsController().getScannedProducts();

        for(BarcodedProduct product : scannedProducts) {
            if(product.getProductUPC() == upc) {

                // 2. Set the original price of the product
                originalProductPrice = product.getPrice();

                // 3. Get the current price of the product and apply the discount based on the percent provided
                discountedPrice =  originalProductPrice - amount;

                // 4. Update product details and subtotal price
                updateProductDetails(discountedPrice, product);
                updateSubtotalPrice();

                System.out.println("Discount applied. New price: $ " + discountedPrice);
                break;
            }
        }
    }

    // Update subtotal price after applying discount
    private void updateSubtotalPrice() {

        // Get the current subtotal price
        double currentSubtotalPrice = this.getCentralPOSFacade().getScanProductsController().getSubtotalPrice();
        System.out.println("Current subtotal price: $ " + currentSubtotalPrice);

        // Update the subtotal price
        newSubtotalPrice = (currentSubtotalPrice - originalProductPrice) + discountedPrice;
        newSubtotalPrice = Double.parseDouble(DECIMALFORMAT.format(newSubtotalPrice));
        this.getCentralPOSFacade().getScanProductsController().setSubtotalPrice(newSubtotalPrice);

        System.out.println("New subtotal price: $ " + newSubtotalPrice);
    }

    // Update details of product
    private void updateProductDetails(double price, BarcodedProduct product) {

        // Update values of the product
        this.discountedPrice = Double.parseDouble(DECIMALFORMAT.format(price));
        this.discountApplied = true;

        product.setPrice(discountedPrice);
        product.setDiscountApplied(discountApplied);

    }

    // Define getter method
    public double getDiscountedPrice() { return discountedPrice; }
    public boolean getDiscountApplied() { return discountApplied; }
}
