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
    private final double GST;

    // Define class constructor
    public BarcodedProductManagement(CentralPointOfSalesFacade facade) {
        super(facade);
        this.DECIMALFORMAT = new DecimalFormat("#.00");
        this.GST = 0.05;
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
                updateTotalPrice();

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

                // 3. Get the current price of the product and apply the discount based on the amount provided
                discountedPrice =  originalProductPrice - amount;

                // 4. Update product details, subtotal price and total price
                updateProductDetails(discountedPrice, product);
                updateSubtotalPrice();
                updateTotalPrice();

                System.out.println("Discount applied. New price: $ " + discountedPrice);
                break;
            }
        }
    }

    // Override current price of product
    public void overrideProductPrice(double newPrice, long upc) {
        // 1. Get the product associated with the provided upc
        List<BarcodedProduct> scannedProducts = this.getCentralPOSFacade().getScanProductsController().getScannedProducts();

        for(BarcodedProduct product : scannedProducts) {
            if(product.getProductUPC() == upc) {

                // 2. Set the original price of the product
                originalProductPrice = product.getPrice();

                // 3. Get the current price of the product and set it to the new price provided
                discountedPrice =  newPrice;

                // 4. Update product details, subtotal price and total price
                updateProductDetails(discountedPrice, product);
                updateSubtotalPrice();
                updateTotalPrice();

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
        this.newSubtotalPrice = (currentSubtotalPrice - originalProductPrice) + discountedPrice;
        this.newSubtotalPrice = Double.parseDouble(DECIMALFORMAT.format(newSubtotalPrice));
        this.getCentralPOSFacade().getScanProductsController().setSubtotalPrice(newSubtotalPrice);

        System.out.println("New subtotal price: $ " + newSubtotalPrice);
    }


    // Update total price after applying discount
    private void updateTotalPrice()  {
        this.newTotalPrice = this.newSubtotalPrice + (this.newSubtotalPrice * this.GST);
        this.newTotalPrice = Double.parseDouble(DECIMALFORMAT.format(newTotalPrice));
        this.getCentralPOSFacade().getScanProductsController().setTotalPrice(newTotalPrice);

        System.out.println("New total price: $: " + newTotalPrice);
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
