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
    public void searchForCustomerAccountWithMembershipIDInDBTest() throws Exception {

        int inputMembershipID = 827491;
        customerInputController.searchForCustomerAccount(inputMembershipID);

        String expectedFName = "Ginny";
        String expectedLName = "Weasley";

        assertEquals(expectedFName, customerInputController.getCustomerFirstName());
        assertEquals(expectedLName, customerInputController.getCustomerLastName());
    }

    @Test
    public void searchForCustomerAccountWithPhoneNumberInDBTest() throws Exception {

        long inputPhoneNum = 4032553653L;
        customerInputController.searchForCustomerAccount(inputPhoneNum);

        String expectedFName = "Taylor";
        String expectedLName = "Patel";

        assertEquals(expectedFName, customerInputController.getCustomerFirstName());
        assertEquals(expectedLName, customerInputController.getCustomerLastName());
    }

    @Test
    public void searchForCustomerAccountWithEmailAddressInDBTest() throws Exception {

        String inputEmail = "heWhoShallNotBeNamed@telus.net";
        customerInputController.searchForCustomerAccount(inputEmail);

        String expectedFName = "Alex";
        String expectedLName = "Nguyen";
        long expectedPhoneNum = 4032401144L;

        assertEquals(expectedFName, customerInputController.getCustomerFirstName());
        assertEquals(expectedLName, customerInputController.getCustomerLastName());
        assertEquals(expectedPhoneNum, customerInputController.getPhoneNumber());
    }

    @Test
    public void searchForCustomerAccountWithNamesInDBTest() throws Exception {

        String inputFName = "Nate";
        String inputLName = "Archibald";
        customerInputController.searchForCustomerAccount(inputFName, inputLName);

        String expectedEmail = "goldenboy3000@gmail.com";
        long expectedPhoneNum = 4034574464L;

        assertEquals(expectedEmail, customerInputController.getEmailAddress());
        assertEquals(expectedPhoneNum, customerInputController.getPhoneNumber());
    }

    @Test
    public void searchForCustomerAccountWithEmailNotInDbTest() throws Exception {

        exception.expect(Exception.class);
        exception.expectMessage("Customer account could not be found with the provided email address.");

        String inputEmail = "theattacktitanrules@me.ca";
        customerInputController.searchForCustomerAccount(inputEmail);
        assertTrue(customerInputController.isGuestMode());
    }

    @Test
    public void searchForCustomerAccountWithPhoneNumNotInDbTest() throws Exception {

        exception.expect(Exception.class);
        exception.expectMessage("Customer account could not be found with the provided phone number.");

        long inputPhoneNum = 3063685363L;
        customerInputController.searchForCustomerAccount(inputPhoneNum);
        assertTrue(customerInputController.isGuestMode());
    }

    @Test
    public void searchForCustomerAccountWithNameNotInDbTest() throws Exception {

        exception.expect(Exception.class);
        exception.expectMessage("Customer account could not be found with the provided customer name.");

        String inputFName = "Rob";
        String inputLName = "Lucci";
        customerInputController.searchForCustomerAccount(inputFName, inputLName);
        assertTrue(customerInputController.isGuestMode());
    }

    @Test
    public void searchForCustomerAccountWithMembershipIDNotInDbTest() throws Exception {

        exception.expect(Exception.class);
        exception.expectMessage("Customer account could not be found with the provided ID.");

        int inputMembershipID = 270820;
        customerInputController.searchForCustomerAccount(inputMembershipID);
        assertTrue(customerInputController.isGuestMode());
    }

    @Test
    public void staffPurchaseWithValidEmployeeIDTest() throws Exception {

        int inputMembershipID = 113542;
        customerInputController.searchForCustomerAccount(inputMembershipID);

        String expected = "Ellison Harbich";
        String actual = customerInputController.getEmployeeName();
        assertEquals(expected, actual);
        assertTrue(customerInputController.isStaffPurchase());
    }

    @Test
    public void staffPurchaseWithInvalidEmployeeIDTest() throws Exception {

        exception.expect(Exception.class);
        exception.expectMessage("Customer account could not be found with the provided ID.");

        int inputMembershipID = 111881;
        customerInputController.searchForCustomerAccount(inputMembershipID);

        customerInputController.searchForCustomerAccount(inputMembershipID);
        assertTrue(customerInputController.isGuestMode());
        assertFalse(customerInputController.isStaffPurchase());

    }
}