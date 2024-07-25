package com.PointOfSaleSystem;

import com.PointOfSaleSystem.CentralPOSFacade.CentralPointOfSalesFacade;

public class Main {
    public static void main(String[] args) {
        CentralPointOfSalesFacade centralPOSFacade = CentralPointOfSalesFacade.startSession();
        centralPOSFacade.getClockInController().clockInEmployee();
    }
}
