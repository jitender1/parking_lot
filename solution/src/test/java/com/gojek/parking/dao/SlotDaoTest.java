package com.gojek.parking.dao;

import com.gojek.parking.exceptions.DaoException;
import com.gojek.parking.model.Slot;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SlotDaoTest {

    private ParkLocationDao parkLocationDao = ParkLocationDao.getInstance();

    @Before
    public void setUp(){
        parkLocationDao.createSlot(new Slot("2"));
    }

    @After
    public void cleanUp(){
        parkLocationDao.destroyParkLocation();
    }

    @Test
    public void testAllocateNearestSlot() throws Exception{
        Slot slot = new Slot("1");
        parkLocationDao.createSlot(slot);

        Slot unusedSlot = parkLocationDao.getFirstUnUsedSlot();
        Assert.assertEquals(slot.getSlotId(), unusedSlot.getSlotId());
        Assert.assertNull(unusedSlot.getVehicleId());
    }

    @Test
    public void testRemoveVehicleFromSlot() throws Exception{
        Slot slot = new Slot("1");
        parkLocationDao.createSlot(slot);
        slot.setVehicleId("vehicle1");

        parkLocationDao.allocateSlotToVehicle(slot);
        Assert.assertEquals(1, parkLocationDao.getAllAllocatedSlots().size());

        parkLocationDao.removeVehicleFromSlot(slot);
        Assert.assertEquals(0, parkLocationDao.getAllAllocatedSlots().size());
    }

    @Test
    public void testRemoveVehicleFromWrongSlot() throws Exception{
        try{
            Slot slot = new Slot("1");
            parkLocationDao.createSlot(slot);
            slot.setVehicleId("vehicle1");

            parkLocationDao.allocateSlotToVehicle(slot);
            Assert.assertEquals(1, parkLocationDao.getAllAllocatedSlots().size());

            Slot wrongSlot = new Slot("6");
            parkLocationDao.removeVehicleFromSlot(wrongSlot);
        }catch (DaoException de){
            Assert.assertTrue(de.getMessage().contains("Wrong Slot 6"));
        }
        Assert.assertEquals(1, parkLocationDao.getAllAllocatedSlots().size());
    }

    @Test
    public void testGetSlotForVehicle() throws Exception {
        Slot slot = new Slot("1");
        parkLocationDao.createSlot(slot);
        slot.setVehicleId("vehicle1");

        parkLocationDao.allocateSlotToVehicle(slot);
        Assert.assertEquals(1, parkLocationDao.getAllAllocatedSlots().size());

        slot = parkLocationDao.getSlotForVehicle("vehicle1");

        Assert.assertEquals("1", slot.getSlotId());
    }

    @Test
    public void testGetSlotForWrongVehicle(){
        try{
            Slot slot = new Slot("1");
            parkLocationDao.createSlot(slot);
            slot.setVehicleId("vehicle1");

            parkLocationDao.allocateSlotToVehicle(slot);

            parkLocationDao.getSlotForVehicle("vehicle2");
        }catch (DaoException de){
            Assert.assertTrue(de.getMessage().contains("Not found"));
        }
    }

    @Test
    public void testGetVehicleOnSlot() throws Exception {
        Slot slot = new Slot("1");
        parkLocationDao.createSlot(slot);
        slot.setVehicleId("vehicle1");

        parkLocationDao.allocateSlotToVehicle(slot);
        Assert.assertEquals(1, parkLocationDao.getAllAllocatedSlots().size());

        String vehicleId = parkLocationDao.getVehicleOnSlot("1");

        Assert.assertEquals("vehicle1", vehicleId);
    }

    @Test
    public void testGetVehicleOnWrongSlot(){
        try{
            Slot slot = new Slot("1");
            parkLocationDao.createSlot(slot);
            slot.setVehicleId("vehicle1");

            parkLocationDao.allocateSlotToVehicle(slot);

            parkLocationDao.getVehicleOnSlot("2");
        }catch (DaoException de){
            Assert.assertTrue(de.getMessage().contains("Not found"));
        }
    }
}