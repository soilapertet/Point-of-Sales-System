package com.PointOfSaleSystem;

import com.PointOfSaleSystem.CentralPOSFacade.CentralPointOfSalesFacade;

public class Main {
    public static void main(String[] args) {
        CentralPointOfSalesFacade centralPOSFacade = CentralPointOfSalesFacade.startSession();

        centralPOSFacade.getScanProductsController().scanProduct();
        centralPOSFacade.getScanProductsController().scanBarcodeProduct();

        centralPOSFacade.getScanProductsController().scanProduct();
        centralPOSFacade.getScanProductsController().scanBarcodeProduct();

        System.out.println(centralPOSFacade.getScanProductsController().getScannedProducts());

        long upc = 5000112637920L;
        centralPOSFacade.getVoidController().voidItem(upc);

        System.out.println(centralPOSFacade.getScanProductsController().getScannedProducts());
    }
}
