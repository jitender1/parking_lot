package com.gojek.parking.dao;

import com.gojek.parking.exceptions.DaoException;
import com.gojek.parking.model.Slot;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SlotDaoTest {

    String parkLocationId = "location1";

    private SlotDao slotDao = SlotDao.getInstance();

    @Before
    public void setUp(){
        slotDao.createSlot(parkLocationId, new Slot("2"));
    }

    @After
    public void tearDown() {
        SlotDao.getInstance().destroyParkLocation(parkLocationId);
    }

    @Test
    public void testAllocateNearestSlot() throws Exception{
        Slot slot = new Slot("1");
        slotDao.createSlot(parkLocationId, slot);

        Slot unusedSlot = slotDao.getFirstUnUsedSlot(parkLocationId);
        Assert.assertEquals(slot.getSlotId(), unusedSlot.getSlotId());
        Assert.assertNull(unusedSlot.getVehicleId());
    }

    @Test
    public void testRemoveVehicleFromSlot() throws Exception{
        Slot slot = new Slot("1");
        slotDao.createSlot(parkLocationId, slot);
        slot.setVehicleId("vehicle1");

        slotDao.allocateSlotToVehicle(parkLocationId, slot);
        Assert.assertEquals(1, slotDao.getAllAllocatedSlots(parkLocationId).size());

        slotDao.removeVehicleFromSlot(parkLocationId, slot);
        Assert.assertEquals(0, slotDao.getAllAllocatedSlots(parkLocationId).size());
    }

    @Test
    public void testRemoveVehicleFromWrongSlot() throws Exception{
        try{
            Slot slot = new Slot("1");
            slotDao.createSlot(parkLocationId, slot);
            slot.setVehicleId("vehicle1");

            slotDao.allocateSlotToVehicle(parkLocationId, slot);
            Assert.assertEquals(1, slotDao.getAllAllocatedSlots(parkLocationId).size());

            Slot wrongSlot = new Slot("6");
            slotDao.removeVehicleFromSlot(parkLocationId, wrongSlot);
        }catch (DaoException de){
            Assert.assertTrue(de.getMessage().contains("Wrong Slot 6"));
        }
        Assert.assertEquals(1, slotDao.getAllAllocatedSlots(parkLocationId).size());
    }

    @Test
    public void testGetSlotForVehicle() throws Exception {
        Slot slot = new Slot("1");
        slotDao.createSlot(parkLocationId, slot);
        slot.setVehicleId("vehicle1");

        slotDao.allocateSlotToVehicle(parkLocationId, slot);
        Assert.assertEquals(1, slotDao.getAllAllocatedSlots(parkLocationId).size());

        slot = slotDao.getSlotForVehicle(parkLocationId, "vehicle1");

        Assert.assertEquals("1", slot.getSlotId());
    }

    @Test
    public void testGetSlotForWrongVehicle(){
        try{
            Slot slot = new Slot("1");
            slotDao.createSlot(parkLocationId, slot);
            slot.setVehicleId("vehicle1");

            slotDao.allocateSlotToVehicle(parkLocationId, slot);

            slotDao.getSlotForVehicle(parkLocationId, "vehicle2");
        }catch (DaoException de){
            Assert.assertTrue(de.getMessage().contains("Not found"));
        }
    }

    @Test
    public void testGetVehicleOnSlot() throws Exception {
        Slot slot = new Slot("1");
        slotDao.createSlot(parkLocationId, slot);
        slot.setVehicleId("vehicle1");

        slotDao.allocateSlotToVehicle(parkLocationId, slot);
        Assert.assertEquals(1, slotDao.getAllAllocatedSlots(parkLocationId).size());

        String vehicleId = slotDao.getVehicleOnSlot(parkLocationId, "1");

        Assert.assertEquals("vehicle1", vehicleId);
    }

    @Test
    public void testGetVehicleOnWrongSlot(){
        try{
            Slot slot = new Slot("1");
            slotDao.createSlot(parkLocationId, slot);
            slot.setVehicleId("vehicle1");

            slotDao.allocateSlotToVehicle(parkLocationId, slot);

            slotDao.getVehicleOnSlot(parkLocationId, "2");
        }catch (DaoException de){
            Assert.assertTrue(de.getMessage().contains("Not found"));
        }
    }

    @Test
    public void testGetAllAllocatedSlotsForWrongParkLocation(){
        try{
            slotDao.getAllAllocatedSlots("wronglocationId");
        }catch (DaoException de){
            Assert.assertTrue(de.getMessage().contains("Wrong Park Location wronglocationId"));
        }
    }
}