package com.PointOfSaleSystem.Controllers;

import com.PointOfSaleSystem.CentralPOSLogic.CentralPointOfSalesController;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CustomerInputControllerTest {

    CentralPointOfSalesController centralPointOfSalesController;
    CustomerInputController customerInputController;

    @Before
    public void initialiseSession() {
        centralPointOfSalesController = CentralPointOfSalesController.startSession();
        customerInputController = centralPointOfSalesController.getCustomerInputController();
    }

    @Test
    public void checkForCustomerAccountWithPhoneNumberInDBTest() {

        long inputPhoneNum = 4032553653L;
        customerInputController.accessCustomerAccount(inputPhoneNum);

        String expectedFName = "Taylor";
        String expectedLName = "Patel";

        assertEquals(expectedFName, customerInputController.getCustomerFirstName());
        assertEquals(expectedLName, customerInputController.getCustomerLastName());
    }

    @Test
    public void checkForCustomerAccountWithEmailAddressInDBTest() {

        String inputEmail = "heWhoShallNotBeNamed@telus.net";
        customerInputController.checkForCustomerAccount(inputEmail);

        String expectedFName = "Alex";
        String expectedLName = "Nguyen";
        long expectedPhoneNum = 4032401144L;

        assertEquals(expectedFName, customerInputController.getCustomerFirstName());
        assertEquals(expectedLName, customerInputController.getCustomerLastName());
        assertEquals(expectedPhoneNum, customerInputController.getPhoneNumber());
    }

    @Test
    public void checkForCustomerAccountWithNamesInDBTest() {

        String inputFName = "Nate";
        String inputLName = "Archibald";
        customerInputController.checkForCustomerAccount(inputFName, inputLName);

        String expectedEmail = "goldenboy3000@gmail.com";
        long expectedPhoneNum = 4034574464L;

        assertEquals(expectedEmail, customerInputController.getEmailAddress());
        assertEquals(expectedPhoneNum, customerInputController.getPhoneNumber());
    }
}