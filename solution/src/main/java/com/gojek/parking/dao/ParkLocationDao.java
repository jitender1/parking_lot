package com.gojek.parking.dao;

import com.gojek.parking.exceptions.DaoException;
import com.gojek.parking.exceptions.Errors;
import com.gojek.parking.model.Slot;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class ParkLocationDao {

    private static ParkLocationDao parkLocation;

    private TreeSet<Slot> unUsedSlots;

    private List<Slot> usedSlots;

    private ParkLocationDao() {
        usedSlots = new ArrayList<>();
        unUsedSlots = new TreeSet<>();
    }

    public static ParkLocationDao getInstance() {
        if (parkLocation == null) {
            parkLocation = new ParkLocationDao();
        }
        return parkLocation;
    }

    public void createSlot(Slot slot) {
        unUsedSlots.add(slot);
    }

    public Slot getFirstUnUsedSlot() throws DaoException {
        if (unUsedSlots.size() > 0) {
            return unUsedSlots.first();
        }
        throw new DaoException(Errors.DAO_ERROR_NO_EMPTY_SLOT);
    }

    public List<Slot> getAllUnUsedSlots() {
        return new ArrayList<>(unUsedSlots);
    }

    public void allocateSlotToVehicle(Slot slot) throws DaoException {
        if (unUsedSlots.contains(slot)) {
            usedSlots.add(slot);
            unUsedSlots.remove(slot);
            return;
        }
        throw new DaoException(String.format(Errors.DAO_ERROR_WRONG_SLOT, slot.getSlotId()));
    }

    public void removeVehicleFromSlot(Slot slot) throws DaoException {
        slot.setVehicleId(null);
        if (usedSlots.contains(slot) && !unUsedSlots.contains(slot)) {
            usedSlots.remove(slot);
            unUsedSlots.add(slot);
            return;
        }
        throw new DaoException(String.format(Errors.DAO_ERROR_WRONG_SLOT, slot.getSlotId()));
    }

    public Slot getSlotForVehicle(String vehicleId) throws DaoException {

        for (Slot slot : usedSlots) {
            if (vehicleId.equals(slot.getVehicleId())) {
                return slot;
            }
        }
        throw new DaoException(Errors.DAO_ERROR_NO_VEHICLE);
    }

    public String getVehicleOnSlot(String slotId) throws DaoException {
        for (Slot slot : usedSlots) {
            if (slot.getSlotId().equals(slotId)) {
                return slot.getVehicleId();
            }
        }
        throw new DaoException(String.format(Errors.DAO_ERROR_NO_VEHICLE, slotId));
    }

    public List<Slot> getAllAllocatedSlots() {
        return usedSlots;
    }

    public void destroyParkLocation(){
        usedSlots = new ArrayList<>();
        unUsedSlots = new TreeSet<>();
    }
}