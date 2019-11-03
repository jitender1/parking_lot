package com.gojek.parking.dao;

import com.gojek.parking.exceptions.DaoException;
import com.gojek.parking.model.Vehicle;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class VehicleDaoTest {
    private VehicleDao vehicleDao = VehicleDao.getInstance();

    @After
    public void cleanUp(){
        vehicleDao.removeAllVehicles();
    }

    @Test
    public void testAddVehicle() throws Exception {
        try {
            Vehicle vehicle = new Vehicle("vehicle1", "green", Vehicle.VehicleType.FOUR_WHEELER);
            vehicleDao.addVehicle(vehicle);
            vehicle = vehicleDao.getVehicleById("vehicle1");
            Assert.assertEquals("vehicle1", vehicle.getRegistrationNumber());
            Assert.assertEquals("green", vehicle.getColor());
            Assert.assertEquals(Vehicle.VehicleType.FOUR_WHEELER, vehicle.getVehicleType());
        } finally {
            vehicleDao.removeVehicle("vehicle1");
        }
    }

    @Test
    public void testRemoveVehicle() throws Exception {
        try {
            Vehicle vehicle = new Vehicle("vehicle1", "green", Vehicle.VehicleType.FOUR_WHEELER);
            vehicleDao.addVehicle(vehicle);
            vehicle = vehicleDao.getVehicleById("vehicle1");
            Assert.assertEquals("vehicle1", vehicle.getRegistrationNumber());
            Assert.assertEquals("green", vehicle.getColor());
            Assert.assertEquals(Vehicle.VehicleType.FOUR_WHEELER, vehicle.getVehicleType());
            vehicleDao.removeVehicle("vehicle1");
            vehicleDao.getVehicleById("vehicle1");
        } catch (DaoException de) {
            Assert.assertTrue(de.getMessage().contains("Not found"));
        }
    }

    @Test
    public void testGetAllVehiclesByColor() throws Exception {
        try {
            Vehicle vehicle1 = new Vehicle("vehicle1", "green", Vehicle.VehicleType.FOUR_WHEELER);
            vehicleDao.addVehicle(vehicle1);

            Vehicle vehicle2 = new Vehicle("vehicle2", "green", Vehicle.VehicleType.FOUR_WHEELER);
            vehicleDao.addVehicle(vehicle2);

            Vehicle vehicle3 = new Vehicle("vehicle3", "red", Vehicle.VehicleType.FOUR_WHEELER);
            vehicleDao.addVehicle(vehicle3);

            Assert.assertEquals(2, vehicleDao.getAllVehiclesByColor("green").size());
            Assert.assertEquals(1, vehicleDao.getAllVehiclesByColor("red").size());
            Assert.assertEquals(0, vehicleDao.getAllVehiclesByColor("black").size());
        } finally {
            vehicleDao.removeVehicle("vehicle1");
            vehicleDao.removeVehicle("vehicle2");
            vehicleDao.removeVehicle("vehicle3");
        }
    }
}