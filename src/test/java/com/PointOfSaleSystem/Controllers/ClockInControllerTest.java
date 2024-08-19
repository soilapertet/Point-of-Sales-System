package com.PointOfSaleSystem.Controllers;

import com.PointOfSaleSystem.CentralPOSLogic.CentralPointOfSalesController;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class ClockInControllerTest {

    CentralPointOfSalesController centralPointOfSalesController;

    @Before
    public void initialiseSession() {
        centralPointOfSalesController = CentralPointOfSalesController.startSession();
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void clockInCashEmployeeWithCorrectPasswordTest() throws Exception {

        int associateID = 112240;
        String associatePassword = "luffy.05.05";

        centralPointOfSalesController.getClockInController().clockInEmployee(associateID, associatePassword);
        assertTrue(centralPointOfSalesController.getClockInController().getClockedIn());

    }

    @Test
    public void clockInCashEmployeeWithIncorrectPasswordTest() throws Exception {

        int associateID = 112288;
        String associatePassword = "sun.g0d.nik@";

        exception.expect(Exception.class);
        exception.expectMessage("Incorrect password. Please try again.");

        centralPointOfSalesController.getClockInController().clockInEmployee(associateID, associatePassword);
        assertFalse(centralPointOfSalesController.getClockInController().getClockedIn());
    }

    @Test
    public void clockInManagerWithCorrectPasswordTest() throws Exception {

        int associateID = 111880;
        String associatePassword = "d0nquixote.d0fl@mingo";

        centralPointOfSalesController.getClockInController().clockInEmployee(associateID, associatePassword);
        assertTrue(centralPointOfSalesController.getClockInController().getClockedIn());
    }

    @Test
    public void clockInManagerWithIncorrectPasswordTest() throws Exception {

        int associateID = 111911;
        String associatePassword = "Dress.r0s@";

        exception.expect(Exception.class);
        exception.expectMessage("Incorrect password. Please try again.");

        centralPointOfSalesController.getClockInController().clockInEmployee(associateID, associatePassword);
        assertFalse(centralPointOfSalesController.getClockInController().getClockedIn());
    }

    @Test
    public void clockInFootwearEmployeeTest() throws Exception {

        int associateID = 112281;
        String associatePassword = "";

        exception.expect(Exception.class);
        exception.expectMessage("Error: You are not authorised to clock into the cash register.");

        centralPointOfSalesController.getClockInController().clockInEmployee(associateID, associatePassword);
    }

    @Test
    public void clockInSoftgoodsEmployeeTest() throws Exception {

        int associateID = 111888;
        String associatePassword = "";

        exception.expect(Exception.class);
        exception.expectMessage("Error: You are not authorised to clock into the cash register.");

        centralPointOfSalesController.getClockInController().clockInEmployee(associateID, associatePassword);
    }

    @Test
    public void clockInHardgoodsEmployeeTest() throws Exception {

        int associateID = 109999;
        String associatePassword = "";

        exception.expect(Exception.class);
        exception.expectMessage("Error: You are not authorised to clock into the cash register.");

        centralPointOfSalesController.getClockInController().clockInEmployee(associateID, associatePassword);
    }

    @Test
    public void clockInECommEmployeeTest() throws Exception {

        int associateID = 115783;
        String associatePassword = "";

        exception.expect(Exception.class);
        exception.expectMessage("Error: You are not authorised to clock into the cash register.");

        centralPointOfSalesController.getClockInController().clockInEmployee(associateID, associatePassword);
    }

    @Test
    public void clockInNonEmployeeIDTest() throws Exception {

        int associateID = 213448;
        String associatePassword = "";

        exception.expect(Exception.class);
        exception.expectMessage("Employee ID could not be found in database. Please try again.");

        centralPointOfSalesController.getClockInController().clockInEmployee(associateID, associatePassword);
    }


}