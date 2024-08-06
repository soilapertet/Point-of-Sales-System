package com.PointOfSaleSystem;

import com.PointOfSaleSystem.CentralPOSFacade.CentralPointOfSalesFacade;

public class Main {
    public static void main(String[] args) {
        CentralPointOfSalesFacade centralPOSFacade = CentralPointOfSalesFacade.startSession();

        centralPOSFacade.getScanProductsController().scanBarcodeProduct();
        centralPOSFacade.getScanProductsController().scanBarcodeProduct();
        centralPOSFacade.getScanProductsController().scanBarcodeProduct();
        centralPOSFacade.getScanProductsController().scanBarcodeProduct();

        centralPOSFacade.getBarcodedProductManagement().applyDiscountByPercent(15, 36000291454L);
        centralPOSFacade.getBarcodedProductManagement().applyDiscountByAmount(20, 5000112637922L);
    }
}