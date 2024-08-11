package com.PointOfSaleSystem;

import com.PointOfSaleSystem.CentralPOSLogic.CentralPointOfSalesController;

public class Main {
    public static void main(String[] args) {
        CentralPointOfSalesController centralPOSFacade = CentralPointOfSalesController.startSession();

        centralPOSFacade.getScanProductsController().scanBarcodeProduct();
        centralPOSFacade.getScanProductsController().scanBarcodeProduct();
        centralPOSFacade.getScanProductsController().scanBarcodeProduct();
        centralPOSFacade.getScanProductsController().scanBarcodeProduct();

        centralPOSFacade.getBarcodedProductManagement().applyDiscountByPercent(15, 36000291454L);
        centralPOSFacade.getBarcodedProductManagement().applyDiscountByAmount(20, 5000112637922L);
        centralPOSFacade.getBarcodedProductManagement().overrideProductPrice(24.94, 763000040440L);
    }
}