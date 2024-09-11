package com.PointOfSaleSystem.CentralPOSLogic;

import com.PointOfSaleSystem.Controllers.*;
import com.PointOfSaleSystem.CustomerLogic.CreateCustomerAccountController;
import com.PointOfSaleSystem.CustomerLogic.CustomerAccountInfoController;
import com.PointOfSaleSystem.ProductLogic.BarcodedProductManagement;
import com.PointOfSaleSystem.StaffPurchase.EmployeeInfoController;

public class CentralPointOfSalesController {

    // Define instance variables
    private CentralPointOfSalesController centralPOSController = null;
    private EmployeeLoginController employeeLoginController;
    private EmployeeInfoController employeeInfoController;
    private CustomerAccountInfoController customerAccountInfoController;
    private CreateCustomerAccountController createCustomerAccountController;
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
        employeeLoginController = new EmployeeLoginController(this);
        employeeInputController = new EmployeeInputController(this);
        employeeInfoController = new EmployeeInfoController(this);
        scanProductsController = new ScanProductsController(this);
        barcodedProductManagement = new BarcodedProductManagement(this);
        customerAccountInfoController = new CustomerAccountInfoController(this);
        createCustomerAccountController = new CreateCustomerAccountController(this);
    }

    // Define getter methods
    public CentralPointOfSalesController getCentralPOSController() { return this.centralPOSController; }
    public EmployeeLoginController getClockInController() { return this.employeeLoginController; }
    public EmployeeInputController getEmployeeInputController() { return this.employeeInputController; }
    public ScanProductsController getScanProductsController() { return this.scanProductsController; }
    public BarcodedProductManagement getBarcodedProductManagement() { return this.barcodedProductManagement; }
    public CustomerAccountInfoController getCustomerAccountInfoController() { return customerAccountInfoController; }
    public CreateCustomerAccountController getCreateCustomerAccountController() { return createCustomerAccountController; }
    public EmployeeInfoController getEmployeeInfoController() { return employeeInfoController; }
}


