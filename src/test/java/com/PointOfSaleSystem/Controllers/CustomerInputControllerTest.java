package com.PointOfSaleSystem.Controllers;

import com.PointOfSaleSystem.CentralPOSLogic.CentralPointOfSalesController;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class CustomerInputControllerTest {

    CentralPointOfSalesController centralPointOfSalesController;
    CustomerInputController customerInputController;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void initialiseSession() {
        centralPointOfSalesController = CentralPointOfSalesController.startSession();
        customerInputController = centralPointOfSalesController.getCustomerInputController();
    }

    @Test
    public void checkForCustomerAccountWithMembershipIDInDBTest() throws Exception {

        int inputMembershipID = 827491;
        customerInputController.checkForCustomerAccount(inputMembershipID);

        String expectedFName = "Ginny";
        String expectedLName = "Weasley";

        assertEquals(expectedFName, customerInputController.getCustomerFirstName());
        assertEquals(expectedLName, customerInputController.getCustomerLastName());
    }

    @Test
    public void checkForCustomerAccountWithPhoneNumberInDBTest() throws Exception {

        long inputPhoneNum = 4032553653L;
        customerInputController.checkForCustomerAccount(inputPhoneNum);

        String expectedFName = "Taylor";
        String expectedLName = "Patel";

        assertEquals(expectedFName, customerInputController.getCustomerFirstName());
        assertEquals(expectedLName, customerInputController.getCustomerLastName());
    }

    @Test
    public void checkForCustomerAccountWithEmailAddressInDBTest() throws Exception {

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
    public void checkForCustomerAccountWithNamesInDBTest() throws Exception {

        String inputFName = "Nate";
        String inputLName = "Archibald";
        customerInputController.checkForCustomerAccount(inputFName, inputLName);

        String expectedEmail = "goldenboy3000@gmail.com";
        long expectedPhoneNum = 4034574464L;

        assertEquals(expectedEmail, customerInputController.getEmailAddress());
        assertEquals(expectedPhoneNum, customerInputController.getPhoneNumber());
    }

    @Test
    public void checkForCustomerAccountWithEmailNotInDbTest() throws Exception {

        exception.expect(Exception.class);
        exception.expectMessage("Customer account could not be found with the provided email address.");

        String inputEmail = "theattacktitanrules@me.ca";
        customerInputController.checkForCustomerAccount(inputEmail);
        assertTrue(customerInputController.getGuestModeStatus());
    }

    @Test
    public void checkForCustomerAccountWithPhoneNumNotInDbTest() throws Exception {

        exception.expect(Exception.class);
        exception.expectMessage("Customer account could not be found with the provided phone number.");

        long inputPhoneNum = 3063685363L;
        customerInputController.checkForCustomerAccount(inputPhoneNum);
        assertTrue(customerInputController.getGuestModeStatus());
    }

    @Test
    public void checkForCustomerAccountWithNameNotInDbTest() throws Exception {

        exception.expect(Exception.class);
        exception.expectMessage("Customer account could not be found with the provided customer name.");

        String inputFName = "Rob";
        String inputLName = "Lucci";
        customerInputController.checkForCustomerAccount(inputFName, inputLName);
        assertTrue(customerInputController.getGuestModeStatus());
    }

    @Test
    public void checkForCustomerAccountWithMembershipIDNotInDbTest() throws Exception {

        exception.expect(Exception.class);
        exception.expectMessage("Customer account could not be found with the provided membership ID.");

        int inputMembershipID = 270820;
        customerInputController.checkForCustomerAccount(inputMembershipID);
        assertTrue(customerInputController.getGuestModeStatus());
    }
}