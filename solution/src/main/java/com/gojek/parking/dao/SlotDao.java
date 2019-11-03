package com.gojek.parking.dao;

import com.gojek.parking.exceptions.DaoException;
import com.gojek.parking.exceptions.Errors;
import com.gojek.parking.model.Slot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public class SlotDao {

    private static SlotDao slotDao;
    private Map<String, SlotInfo> parkLocationToSlotMap;

    private SlotDao() {
        parkLocationToSlotMap = new HashMap<String, SlotInfo>();
    }

    public static SlotDao getInstance() {
        if (slotDao == null) {
            slotDao = new SlotDao();
        }
        return slotDao;
    }

    public void createSlot(String parkLocationId, Slot slot) {
        if (parkLocationToSlotMap.containsKey(parkLocationId)) {
            parkLocationToSlotMap.get(parkLocationId).getUnUsedSlots().add(slot);
        } else {
            SlotInfo slotInfo = new SlotInfo();
            slotInfo.getUnUsedSlots().add(slot);
            parkLocationToSlotMap.put(parkLocationId, slotInfo);
        }
    }

    public Slot getFirstUnUsedSlot(String parkLocationId) throws DaoException {
        if (parkLocationToSlotMap.containsKey(parkLocationId)) {
            TreeSet<Slot> unUsedSlots = parkLocationToSlotMap.get(parkLocationId).getUnUsedSlots();
            if (unUsedSlots.size() > 0) {
                return unUsedSlots.first();
            }
            throw new DaoException(Errors.DAO_ERROR_NO_EMPTY_SLOT);
        }
        throw new DaoException(String.format(Errors.DAO_ERROR_WRONG_PARK_LOCATION, parkLocationId));
    }

    public void allocateSlotToVehicle(String parkLocationId, Slot slot) throws DaoException {
        if (parkLocationToSlotMap.containsKey(parkLocationId)) {
            SlotInfo slotInfo = parkLocationToSlotMap.get(parkLocationId);
            if (slotInfo.getUnUsedSlots().contains(slot)) {
                slotInfo.getUsedSlots().add(slot);
                slotInfo.getUnUsedSlots().remove(slot);
                return;
            }
            throw new DaoException(String.format(Errors.DAO_ERROR_WRONG_SLOT, slot.getSlotId()));
        }
        throw new DaoException(String.format(Errors.DAO_ERROR_WRONG_PARK_LOCATION, parkLocationId));
    }

    public void removeVehicleFromSlot(String parkLocationId, Slot slot) throws DaoException {
        if (parkLocationToSlotMap.containsKey(parkLocationId)) {
            slot.setVehicleId(null);
            SlotInfo slotInfo = parkLocationToSlotMap.get(parkLocationId);
            List<Slot> usedSlots = slotInfo.getUsedSlots();
            TreeSet<Slot> unUsedSlots = slotInfo.getUnUsedSlots();

            if (usedSlots.contains(slot) && !unUsedSlots.contains(slot)) {
                usedSlots.remove(slot);
                unUsedSlots.add(slot);
                return;
            }
            throw new DaoException(String.format(Errors.DAO_ERROR_WRONG_SLOT, slot.getSlotId()));
        }
        throw new DaoException(String.format(Errors.DAO_ERROR_WRONG_PARK_LOCATION, parkLocationId));
    }

    public Slot getSlotForVehicle(String parkLocationId, String vehicleId) throws DaoException {
        if (parkLocationToSlotMap.containsKey(parkLocationId)) {
            List<Slot> slots = parkLocationToSlotMap.get(parkLocationId).getUsedSlots();
            for (Slot slot : slots) {
                if (vehicleId.equals(slot.getVehicleId())) {
                    return slot;
                }
            }
            throw new DaoException(Errors.DAO_ERROR_NO_VEHICLE);
        }
        throw new DaoException(String.format(Errors.DAO_ERROR_WRONG_PARK_LOCATION, parkLocationId));
    }

    public String getVehicleOnSlot(String parkLocationId, String slotId) throws DaoException {
        if (parkLocationToSlotMap.containsKey(parkLocationId)) {
            List<Slot> slots = parkLocationToSlotMap.get(parkLocationId).getUsedSlots();
            for (Slot slot : slots) {
                if (slot.getSlotId().equals(slotId)) {
                    return slot.getVehicleId();
                }
            }
            throw new DaoException(String.format(Errors.DAO_ERROR_NO_VEHICLE, slotId));
        }
        throw new DaoException(String.format(Errors.DAO_ERROR_WRONG_PARK_LOCATION, parkLocationId));
    }

    public List<Slot> getAllAllocatedSlots(String parkLocationId) throws DaoException {
        if (parkLocationToSlotMap.containsKey(parkLocationId)) {
            return parkLocationToSlotMap.get(parkLocationId).getUsedSlots();
        }
        throw new DaoException(String.format(Errors.DAO_ERROR_WRONG_PARK_LOCATION, parkLocationId));
    }

    public void destroyParkLocation(String parkLocationId) {
        parkLocationToSlotMap.remove(parkLocationId);
    }

    class SlotInfo {
        private TreeSet<Slot> unUsedSlots;
        private List<Slot> usedSlots;

        public SlotInfo() {
            unUsedSlots = new TreeSet<Slot>();
            usedSlots = new ArrayList<Slot>();
        }

        public TreeSet<Slot> getUnUsedSlots() {
            return unUsedSlots;
        }

        public List<Slot> getUsedSlots() {
            return usedSlots;
        }
    }
}