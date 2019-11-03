package com.gojek.parking.apiImpl;

import com.gojek.parking.api.ServiceFactory;
import com.gojek.parking.exceptions.ServiceException;
import com.gojek.parking.model.Vehicle;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class VehicleServiceImplTest {

    @After
    public void cleanUp() throws Exception {
        ServiceFactory.getInstance().getVehicleService().removeAllVehicles();
    }

    @Test
    public void testAddVehicleTwice() {
        try {
            Vehicle vehicle = new Vehicle("vehicle1", "red", Vehicle.VehicleType.FOUR_WHEELER);
            ServiceFactory.getInstance().getVehicleService().addVehicle(vehicle);
            ServiceFactory.getInstance().getVehicleService().addVehicle(vehicle);
        } catch (ServiceException se) {
            Assert.assertTrue(se.getMessage().contains("Vehicle vehicle1 already parked"));
        }
    }

    @Test
    public void testRemoveUnparkedVehicle() {
        try {
            ServiceFactory.getInstance().getVehicleService().removeVehicle("vehicle1");
        } catch (ServiceException se) {
            Assert.assertTrue(se.getMessage().contains("Not found"));
        }
    }

    @Test
    public void testGetUnparkedVehicle() {
        try {
            ServiceFactory.getInstance().getVehicleService().getVehicle("vehicle1");
        } catch (ServiceException se) {
            Assert.assertTrue(se.getMessage().contains("Not found"));
        }
    }
}

