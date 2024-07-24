package com.PointOfSaleSystem.CentralPOSFacade;

import com.PointOfSaleSystem.Controllers.ClockInController;
import com.PointOfSaleSystem.Controllers.CustomerInputController;
import com.PointOfSaleSystem.Controllers.EmployeeInputController;
import com.PointOfSaleSystem.Controllers.ScanProductsController;

public class CentralPointOfSalesFacade {

    // Define instance variables
    private static CentralPointOfSalesFacade centralPOSFacade;
    private ClockInController clockInController;
    private CustomerInputController customerInputController;
    private EmployeeInputController employeeInputController;
    private ScanProductsController scanProductsController;

    // Define the class constructors
    private CentralPointOfSalesFacade() {
        clockInController = new ClockInController();
        customerInputController = new CustomerInputController();
        employeeInputController = new EmployeeInputController();
        scanProductsController = new ScanProductsController();
    }

    public static CentralPointOfSalesFacade getCentralPOSFacade() {
        if(centralPOSFacade == null) {
            centralPOSFacade = new CentralPointOfSalesFacade();
        }

        return centralPOSFacade;
    }


    // Define getter methods
    public ClockInController getClockInController() { return clockInController; }
    public CustomerInputController getCustomerInputController() { return customerInputController; }
    public EmployeeInputController getEmployeeInputController() { return employeeInputController; }
    public ScanProductsController getScanProductsController() { return scanProductsController; }
}
