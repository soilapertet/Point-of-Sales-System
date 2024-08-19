package com.PointOfSaleSystem.CentralPOSLogic;

import com.PointOfSaleSystem.Controllers.ClockInController;
import com.PointOfSaleSystem.Controllers.CustomerInputController;
import com.PointOfSaleSystem.Controllers.EmployeeInputController;
import com.PointOfSaleSystem.Controllers.ScanProductsController;
import com.PointOfSaleSystem.ProductLogic.BarcodedProductManagement;

public class CentralPointOfSalesController {

    // Define instance variables
    private CentralPointOfSalesController centralPOSController = null;
    private ClockInController clockInController;
    private CustomerInputController customerInputController;
    private EmployeeInputController employeeInputController;
    private ScanProductsController scanProductsController;
    private BarcodedProductManagement barcodedProductManagement;

    // Call the private class and initialise the class controllers
    public static CentralPointOfSalesController startSession() {
        return new CentralPointOfSalesController();
    }

    // Class constructor will be used by child classes
    public CentralPointOfSalesController(CentralPointOfSalesController centralPOSController) {
        this.centralPOSController = centralPOSController;
    }

    private CentralPointOfSalesController() {
        clockInController = new ClockInController(this);
        customerInputController = new CustomerInputController(this);
        employeeInputController = new EmployeeInputController(this);
        scanProductsController = new ScanProductsController(this);
        barcodedProductManagement = new BarcodedProductManagement(this);
    }

    // Define getter methods
    public ClockInController getClockInController() { return clockInController; }
    public CustomerInputController getCustomerInputController() { return customerInputController; }
    public EmployeeInputController getEmployeeInputController() { return employeeInputController; }
    public ScanProductsController getScanProductsController() { return scanProductsController; }
    public BarcodedProductManagement getBarcodedProductManagement() { return barcodedProductManagement; }
}


