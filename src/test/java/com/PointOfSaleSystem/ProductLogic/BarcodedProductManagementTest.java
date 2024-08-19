package com.PointOfSaleSystem.ProductLogic;

import com.PointOfSaleSystem.CentralPOSLogic.CentralPointOfSalesController;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BarcodedProductManagementTest {

    CentralPointOfSalesController centralPOSController;

    @Before
    public void initialise() {
        // Start a new session
        centralPOSController = CentralPointOfSalesController.startSession();

        // Scan 4 product items
        centralPOSController.getScanProductsController().scanBarcodeProduct(5000112637921L); // $ 104.99
        centralPOSController.getScanProductsController().scanBarcodeProduct(4006381333931L); // $ 199.99
        centralPOSController.getScanProductsController().scanBarcodeProduct(12345678907L); // $ 79.99
        centralPOSController.getScanProductsController().scanBarcodeProduct(29000037984L); // 44.99
    }

    // Test: Update the product quantity of the second scanned product
    @Test
    public void updateProductQuantityTest() {

        long productUPC = 4006381333931L;
        centralPOSController.getBarcodedProductManagement().updateProductQuantity(5, productUPC);

        double expectedSubtotalPrice = 1229.92;
        double expectedTotalPrice = 1291.42;

        Assert.assertEquals(expectedSubtotalPrice, centralPOSController.getScanProductsController().getSubtotalPrice(), 0.0);
        Assert.assertEquals(expectedTotalPrice, centralPOSController.getScanProductsController().getTotalPrice(), 0.0);
    }

    @Test
    public void updateProductQuantityWithDiscountedProductTest() {

        long productUPC = 12345678907L;
        int percentDiscount = 25;

        centralPOSController.getBarcodedProductManagement().applyDiscountByPercent(percentDiscount, productUPC);
        centralPOSController.getBarcodedProductManagement().updateProductQuantity(3, productUPC);

        double expectedSubtotalPrice = 529.94;
        double expectedTotalPrice = 556.44;

        Assert.assertEquals(expectedSubtotalPrice, centralPOSController.getScanProductsController().getSubtotalPrice(), 0.0);
        Assert.assertEquals(expectedTotalPrice, centralPOSController.getScanProductsController().getTotalPrice(), 0.0);

    }

    @Test
    public void applyPercentDiscountToProductTest() {

        long productUPC = 5000112637921L;
        int percentDiscount = 15;

        centralPOSController.getBarcodedProductManagement().applyDiscountByPercent(percentDiscount, productUPC);

        double expectedProductPrice = 89.24;
        double actualProductPrice = centralPOSController.getBarcodedProductManagement().getScannedProduct().getPrice();
        Assert.assertEquals(expectedProductPrice, actualProductPrice, 0.0 );
    }

    @Test
    public void applyAmountDiscountToProductTest() {

        long productUPC = 29000037984L;
        int amountDiscount = 10;

        centralPOSController.getBarcodedProductManagement().applyDiscountByAmount(amountDiscount, productUPC);

        double expectedProductPrice = 34.99;
        double actualProductPrice = centralPOSController.getBarcodedProductManagement().getScannedProduct().getPrice();
        Assert.assertEquals(expectedProductPrice, actualProductPrice, 0.0 );
    }

}