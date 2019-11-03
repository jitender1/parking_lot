package com.gojek.parking.dao;

import com.gojek.parking.exceptions.DaoException;
import com.gojek.parking.model.Vehicle;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class VehicleDaoTest {
    String parkLocationId = "location1";

    private VehicleDao vehicleDao = VehicleDao.getInstance();

    @After
    public void tearDown() {
        vehicleDao.removeAllVehicles(parkLocationId);
    }

    @Test
    public void testAddVehicle() throws Exception {
        Vehicle vehicle = new Vehicle("vehicle1", "green", Vehicle.VehicleType.FOUR_WHEELER);
        vehicleDao.addVehicle(parkLocationId, vehicle);
        vehicle = vehicleDao.getVehicleById(parkLocationId, "vehicle1");
        Assert.assertEquals("vehicle1", vehicle.getRegistrationNumber());
        Assert.assertEquals("green", vehicle.getColor());
        Assert.assertEquals(Vehicle.VehicleType.FOUR_WHEELER, vehicle.getVehicleType());
    }

    @Test
    public void testRemoveVehicle() throws Exception {
        try {
            Vehicle vehicle = new Vehicle("vehicle1", "green", Vehicle.VehicleType.FOUR_WHEELER);
            vehicleDao.addVehicle(parkLocationId, vehicle);
            vehicle = vehicleDao.getVehicleById(parkLocationId, "vehicle1");
            Assert.assertEquals("vehicle1", vehicle.getRegistrationNumber());
            Assert.assertEquals("green", vehicle.getColor());
            Assert.assertEquals(Vehicle.VehicleType.FOUR_WHEELER, vehicle.getVehicleType());
            vehicleDao.removeVehicle(parkLocationId, "vehicle1");

            vehicleDao.getVehicleById(parkLocationId, "vehicle1");
        } catch (DaoException de) {
            Assert.assertTrue(de.getMessage().contains("Not found"));
        }
    }

    @Test
    public void testGetAllVehiclesByColor() throws Exception {
        Vehicle vehicle1 = new Vehicle("vehicle1", "green", Vehicle.VehicleType.FOUR_WHEELER);
        vehicleDao.addVehicle(parkLocationId, vehicle1);

        Vehicle vehicle2 = new Vehicle("vehicle2", "green", Vehicle.VehicleType.FOUR_WHEELER);
        vehicleDao.addVehicle(parkLocationId, vehicle2);

        Vehicle vehicle3 = new Vehicle("vehicle3", "red", Vehicle.VehicleType.FOUR_WHEELER);
        vehicleDao.addVehicle(parkLocationId, vehicle3);


        Assert.assertEquals(2, vehicleDao.getAllVehiclesByColor(parkLocationId, "green").size());
        Assert.assertEquals(1, vehicleDao.getAllVehiclesByColor(parkLocationId, "red").size());
        Assert.assertEquals(0, vehicleDao.getAllVehiclesByColor(parkLocationId, "black").size());
    }
}