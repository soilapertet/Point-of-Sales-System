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
        centralPOSController.getScanProductsController().scanBarcodeProduct(5000112637921L);
        centralPOSController.getScanProductsController().scanBarcodeProduct(4006381333931L);
        centralPOSController.getScanProductsController().scanBarcodeProduct(12345678907L);
        centralPOSController.getScanProductsController().scanBarcodeProduct(29000037984L);
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
    public void updateProductQuantityWithDiscountedProduct() {

        long productUPC = 12345678907L;
        int percentDiscount = 25;

        centralPOSController.getBarcodedProductManagement().applyDiscountByPercent(percentDiscount, productUPC);
        centralPOSController.getBarcodedProductManagement().updateProductQuantity(3, productUPC);

        double expectedSubtotalPrice = 529.94;
        double expectedTotalPrice = 556.44;

        Assert.assertEquals(expectedSubtotalPrice, centralPOSController.getScanProductsController().getSubtotalPrice(), 0.0);
        Assert.assertEquals(expectedTotalPrice, centralPOSController.getScanProductsController().getTotalPrice(), 0.0);

    }

}