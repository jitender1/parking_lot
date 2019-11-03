package com.gojek.parking.apiImpl;

import com.gojek.parking.api.ParkLocationService;
import com.gojek.parking.api.ParkingService;
import com.gojek.parking.api.ServiceFactory;
import com.gojek.parking.exceptions.ServiceException;
import com.gojek.parking.model.Vehicle;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class ParkingServiceImplTest {

    private static final ParkLocationService parkLocationService = ServiceFactory.getInstance().getParkLocationService();
    private static final ParkingService parkingService = ServiceFactory.getInstance().getParkingService();

    @After
    public void cleanUp() throws  Exception{
        parkingService.destroyParkLocation();
    }

    @Test
    public void testVehicleParking() throws Exception {
        parkingService.initializeParkLocation(2);
        Vehicle vehicle = new Vehicle("vehicle1", "green", Vehicle.VehicleType.FOUR_WHEELER);
        String slotId = parkingService.parkVehicle(vehicle);
        String vehicleId = parkLocationService.getVehicleIdBySlotId(slotId);
        Assert.assertEquals("vehicle1", vehicleId);
    }

    @Test
    public void testVehicleParkingWhenParkingIsFull() throws Exception {
        try {
            parkingService.initializeParkLocation(1);
            Vehicle vehicle = new Vehicle("vehicle1", "green", Vehicle.VehicleType.FOUR_WHEELER);
            String slotId = parkingService.parkVehicle(vehicle);

            Vehicle vehicle2 = new Vehicle("vehicle2", "green", Vehicle.VehicleType.FOUR_WHEELER);
            parkingService.parkVehicle(vehicle2);
        } catch (ServiceException se) {
            System.out.println(se.getMessage());
            Assert.assertTrue(se.getMessage().contains("Sorry, parking lot is full"));
        }
    }

    @Test
    public void testUnParkingVehicle() throws Exception {
        try {
            parkingService.initializeParkLocation(2);
            Vehicle vehicle = new Vehicle("vehicle1", "green", Vehicle.VehicleType.FOUR_WHEELER);
            String slotId = parkingService.parkVehicle(vehicle);
            parkingService.unParkVehicle(slotId);
            parkingService.findVehicle("vehicle1");
        } catch (ServiceException se) {
            Assert.assertTrue(se.getMessage().contains("Not found"));
        }
    }

    @Test
    public void testParkUnParkVehicleMultipleTimes() throws Exception {
        parkingService.initializeParkLocation(3);
        Vehicle vehicle = new Vehicle("vehicle1", "green", Vehicle.VehicleType.FOUR_WHEELER);
        String slotId = parkingService.parkVehicle(vehicle);

        Vehicle vehicle2 = new Vehicle("vehicle2", "green", Vehicle.VehicleType.FOUR_WHEELER);
        parkingService.parkVehicle(vehicle2);

        parkingService.unParkVehicle(slotId);

        slotId = parkingService.parkVehicle(vehicle);

        String slotIdFound = parkingService.findVehicle("vehicle1");
        Assert.assertEquals(slotId, slotIdFound);
    }

    @Test
    public void testFindVehicle() throws Exception {
        parkLocationService.initializeParkLocation(2);
        Vehicle vehicle = new Vehicle("vehicle1", "green", Vehicle.VehicleType.FOUR_WHEELER);
        String slotId = parkingService.parkVehicle(vehicle);
        String searchedSlotId = parkingService.findVehicle("vehicle1");
        Assert.assertEquals(slotId, searchedSlotId);
    }

    @Test
    public void testGetSlotNumbersByColor() throws Exception {
        parkingService.initializeParkLocation(2);
        Vehicle vehicle1 = new Vehicle("vehicle1", "green", Vehicle.VehicleType.FOUR_WHEELER);
        String slotId1 = parkingService.parkVehicle(vehicle1);

        Vehicle vehicle2 = new Vehicle("vehicle2", "Green", Vehicle.VehicleType.FOUR_WHEELER);
        String slotId2 = parkingService.parkVehicle(vehicle2);

        List<String> slotIds = parkingService.getSlotNumbersByColor("green");
        Assert.assertEquals(2, slotIds.size());
        Assert.assertTrue(slotIds.contains(slotId1));
        Assert.assertTrue(slotIds.contains(slotId2));
    }

    @Test
    public void testGetVehicleNumbersByColor() throws Exception {
        parkingService.initializeParkLocation(2);
        Vehicle vehicle1 = new Vehicle("vehicle1", "green", Vehicle.VehicleType.FOUR_WHEELER);
        String slotId1 = parkingService.parkVehicle(vehicle1);

        Vehicle vehicle2 = new Vehicle("vehicle2", "Green", Vehicle.VehicleType.FOUR_WHEELER);
        String slotId2 = parkingService.parkVehicle(vehicle2);

        List<String> vehicleIds = parkingService.getRegistrationNumbersOfCarsByColor("green");
        Assert.assertEquals(2, vehicleIds.size());
        Assert.assertTrue(vehicleIds.contains("vehicle1"));
        Assert.assertTrue(vehicleIds.contains("vehicle2"));
    }
}