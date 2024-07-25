package com.PointOfSaleSystem.CentralPOSFacade;

import com.PointOfSaleSystem.Controllers.ClockInController;
import com.PointOfSaleSystem.Controllers.CustomerInputController;
import com.PointOfSaleSystem.Controllers.EmployeeInputController;
import com.PointOfSaleSystem.Controllers.ScanProductsController;
import com.PointOfSaleSystem.ProductLogic.BarcodedProductManagement;

public class CentralPointOfSalesFacade {

    // Define instance variables
    private CentralPointOfSalesFacade centralPOSFacade = null;
    private ClockInController clockInController;
    private CustomerInputController customerInputController;
    private EmployeeInputController employeeInputController;
    private ScanProductsController scanProductsController;
    private BarcodedProductManagement barcodedProductManagement;

    // Call the private class and initialise the class controllers
    public static CentralPointOfSalesFacade startSession() {
        return new CentralPointOfSalesFacade();
    }

    // Class constructor will be used by child classes
    public CentralPointOfSalesFacade(CentralPointOfSalesFacade centralPOSFacade) {
        this.centralPOSFacade = centralPOSFacade;
    }

    private CentralPointOfSalesFacade() {
        clockInController = new ClockInController(this);
        customerInputController = new CustomerInputController(this);
        employeeInputController = new EmployeeInputController(this);
        scanProductsController = new ScanProductsController(this);
        barcodedProductManagement = new BarcodedProductManagement(this);
    }

    // Define getter methods
    public CentralPointOfSalesFacade getCentralPOSFacade() { return centralPOSFacade; }
    public ClockInController getClockInController() { return clockInController; }
    public CustomerInputController getCustomerInputController() { return customerInputController; }
    public EmployeeInputController getEmployeeInputController() { return employeeInputController; }
    public ScanProductsController getScanProductsController() { return scanProductsController; }
    public BarcodedProductManagement getBarcodedProductManagement() { return barcodedProductManagement; }
}


