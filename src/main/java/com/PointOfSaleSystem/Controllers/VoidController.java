package com.PointOfSaleSystem.Controllers;

import com.PointOfSaleSystem.CentralPOSFacade.CentralPointOfSalesFacade;
import com.PointOfSaleSystem.ProductLogic.BarcodedProduct;

import java.util.List;

public class VoidController extends CentralPointOfSalesFacade {

    // Define instance variables
    private long productUPCToVoid;
    private BarcodedProduct voidedProduct;
    private List<BarcodedProduct> scannedProducts;

    // Define the class constructor
    public VoidController(CentralPointOfSalesFacade facade) {
        super(facade);
    }

    // Remove specified product from scanned products ("cart") using its upc
    public void voidItem(long upc) {

        // initialise the product we want to void
        this.productUPCToVoid = upc;

        // initialise the already scanned products
        scannedProducts = this.getCentralPOSFacade().getScanProductsController().getScannedProducts();

        // remove barcoded product with matching upc
        scannedProducts.removeIf(barcodedProduct -> upc == barcodedProduct.getProductUPC());
    }

    // Define getter methods
    public BarcodedProduct getVoidedProduct() {
        return voidedProduct;
    }
}
