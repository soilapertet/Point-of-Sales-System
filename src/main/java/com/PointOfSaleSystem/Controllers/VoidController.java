package com.PointOfSaleSystem.Controllers;

import com.PointOfSaleSystem.CentralPOSFacade.CentralPointOfSalesFacade;
import com.PointOfSaleSystem.ProductLogic.BarcodedProduct;

import java.util.List;

public class VoidController {

    // Define instance variables
    private CentralPointOfSalesFacade centralPOSFacade;
    private long productUPCToVoid;
    private BarcodedProduct voidedProduct;
    private List<BarcodedProduct> scannedProducts;

    // Define the class constructor
    public VoidController() {
        centralPOSFacade = CentralPointOfSalesFacade.getCentralPOSFacade();
    }

    // Remove specified product from scanned products ("cart") using its upc
    public void voidItem(long upc) {

        // initialise the product we want to void
        this.productUPCToVoid = upc;

        // initialise the already scanned products
        scannedProducts = centralPOSFacade.getScanProductsController().getScannedProducts();

        // loop through scanned products, look for and remove the product
        for(BarcodedProduct scannedProduct : scannedProducts) {
            if(productUPCToVoid == scannedProduct.getProductUPC()) {
                this.voidedProduct = scannedProduct;
                scannedProducts.remove(scannedProduct);
            }
        }
    }

    // Define getter methods
    public BarcodedProduct getVoidedProduct() {
        return voidedProduct;
    }
}
