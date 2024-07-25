package com.PointOfSaleSystem.ProductLogic;

import com.PointOfSaleSystem.CentralPOSFacade.CentralPointOfSalesFacade;

public class BarcodedProductManagement  extends CentralPointOfSalesFacade {

    public BarcodedProductManagement(CentralPointOfSalesFacade facade) {
        super(facade);
    }
}
