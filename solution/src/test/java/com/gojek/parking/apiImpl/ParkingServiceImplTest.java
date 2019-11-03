package com.gojek.parking.apiImpl;

import com.gojek.parking.api.ParkLocationService;
import com.gojek.parking.api.ParkingService;
import com.gojek.parking.api.ServiceFactory;
import com.gojek.parking.exceptions.ServiceException;
import com.gojek.parking.model.Vehicle;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class ParkingServiceImplTest {

    private static final ParkLocationService parkLocationService = ServiceFactory.getInstance().getParkLocationService();
    private static final ParkingService parkingService = ServiceFactory.getInstance().getParkingService();

    @Test
    public void testVehicleParking() throws Exception {
        parkLocationService.initializeParkLocation("location1", 2);
        Vehicle vehicle = new Vehicle("vehicle1", "green", Vehicle.VehicleType.FOUR_WHEELER);
        String slotId = parkingService.parkVehicle("location1", vehicle);
        String vehicleId = parkLocationService.getVehicleIdBySlotId("location1", slotId);
        Assert.assertEquals("vehicle1", vehicleId);
    }

    @Test
    public void testVehicleParkingInWrongLocation() throws Exception {
        try {
            Vehicle vehicle = new Vehicle("vehicle1", "green", Vehicle.VehicleType.FOUR_WHEELER);
            parkingService.parkVehicle("location2", vehicle);
        } catch (ServiceException se) {
            Assert.assertTrue(se.getMessage().contains("Wrong Park Location"));
        }

    }

    @Test
    public void testVehicleParkingWhenParkingIsFull() throws Exception {
        try {
            parkLocationService.initializeParkLocation("location11", 1);
            Vehicle vehicle = new Vehicle("vehicle1", "green", Vehicle.VehicleType.FOUR_WHEELER);
            String slotId = parkingService.parkVehicle("location11", vehicle);

            Vehicle vehicle2 = new Vehicle("vehicle2", "green", Vehicle.VehicleType.FOUR_WHEELER);
            parkingService.parkVehicle("location11", vehicle);
        } catch (ServiceException se) {
            Assert.assertTrue(se.getMessage().contains("Sorry, parking lot is full"));
        }
    }

    @Test
    public void testUnParkingVehicle() throws Exception {
        try {
            parkLocationService.initializeParkLocation("location2", 2);
            Vehicle vehicle = new Vehicle("vehicle1", "green", Vehicle.VehicleType.FOUR_WHEELER);
            String slotId = parkingService.parkVehicle("location2", vehicle);
            parkingService.unParkVehicle("location2", slotId);
            parkingService.findVehicle("location2", "vehicle1");
        } catch (ServiceException se) {
            Assert.assertTrue(se.getMessage().contains("Not found"));
        }
    }

    @Test
    public void testParkUnParkVehicleMultipleTimes() throws Exception {
        parkLocationService.initializeParkLocation("location21", 3);
        Vehicle vehicle = new Vehicle("vehicle1", "green", Vehicle.VehicleType.FOUR_WHEELER);
        String slotId = parkingService.parkVehicle("location21", vehicle);

        Vehicle vehicle2 = new Vehicle("vehicle2", "green", Vehicle.VehicleType.FOUR_WHEELER);
        parkingService.parkVehicle("location21", vehicle2);

        parkingService.unParkVehicle("location21", slotId);

        slotId = parkingService.parkVehicle("location21", vehicle);

        String slotIdFound = parkingService.findVehicle("location21", "vehicle1");
        Assert.assertEquals(slotId, slotIdFound);
    }

    @Test
    public void testFindVehicle() throws Exception {
        parkLocationService.initializeParkLocation("location3", 2);
        Vehicle vehicle = new Vehicle("vehicle1", "green", Vehicle.VehicleType.FOUR_WHEELER);
        String slotId = parkingService.parkVehicle("location3", vehicle);
        String searchedSlotId = parkingService.findVehicle("location3", "vehicle1");
        Assert.assertEquals(slotId, searchedSlotId);
    }

    @Test
    public void testGetSlotNumbersByColor() throws Exception {
        parkLocationService.initializeParkLocation("location4", 2);
        Vehicle vehicle1 = new Vehicle("vehicle1", "green", Vehicle.VehicleType.FOUR_WHEELER);
        String slotId1 = parkingService.parkVehicle("location4", vehicle1);

        Vehicle vehicle2 = new Vehicle("vehicle2", "Green", Vehicle.VehicleType.FOUR_WHEELER);
        String slotId2 = parkingService.parkVehicle("location4", vehicle2);

        List<String> slotIds = parkingService.getSlotNumbersByColor("location4", "green");
        Assert.assertEquals(2, slotIds.size());
        Assert.assertTrue(slotIds.contains(slotId1));
        Assert.assertTrue(slotIds.contains(slotId2));
    }

    @Test
    public void testGetVehicleNumbersByColor() throws Exception {
        parkLocationService.initializeParkLocation("location5", 2);
        Vehicle vehicle1 = new Vehicle("vehicle1", "green", Vehicle.VehicleType.FOUR_WHEELER);
        String slotId1 = parkingService.parkVehicle("location5", vehicle1);

        Vehicle vehicle2 = new Vehicle("vehicle2", "Green", Vehicle.VehicleType.FOUR_WHEELER);
        String slotId2 = parkingService.parkVehicle("location5", vehicle2);

        List<String> vehicleIds = parkingService.getRegistrationNumbersOfCarsByColor("location5", "green");
        Assert.assertEquals(2, vehicleIds.size());
        Assert.assertTrue(vehicleIds.contains("vehicle1"));
        Assert.assertTrue(vehicleIds.contains("vehicle2"));
    }
}