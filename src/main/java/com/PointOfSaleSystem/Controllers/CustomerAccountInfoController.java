package com.PointOfSaleSystem.Controllers;

import com.PointOfSaleSystem.CentralPOSLogic.CentralPointOfSalesController;
import org.bson.types.ObjectId;

public class CustomerAccountInfoController extends CentralPointOfSalesController  {

    // Define instance variables
    private String customerFirstName;
    private String customerLastName;
    private String employeeName;
    private long phoneNumber;
    private String emailAddress;
    private int membershipID;
    private boolean guestMode;
    private boolean staffPurchase;

    // Initialise class constructor
    public CustomerAccountInfoController(CentralPointOfSalesController controller) {
        super(controller);
    }

    
}
