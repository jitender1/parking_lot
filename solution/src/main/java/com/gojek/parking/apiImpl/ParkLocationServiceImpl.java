package com.gojek.parking.apiImpl;

import com.gojek.parking.api.ParkLocationService;
import com.gojek.parking.dao.DaoFactory;
import com.gojek.parking.exceptions.DaoException;
import com.gojek.parking.exceptions.ServiceException;
import com.gojek.parking.model.Slot;

import java.util.List;

public class ParkLocationServiceImpl implements ParkLocationService {

    public void initializeParkLocation(String locationId, int numberOfSlots) throws ServiceException {
        for (int slotNumber = 1; slotNumber <= numberOfSlots; slotNumber++) {
            DaoFactory.SLOT_DAO.createSlot(locationId, new Slot(String.valueOf(slotNumber)));
        }
    }

    public String getSlotIdByVehicleId(String parkLocationId, String vehicleId) throws ServiceException {
        try {
            Slot slot = DaoFactory.SLOT_DAO.getSlotForVehicle(parkLocationId, vehicleId);
            return slot.getSlotId();
        } catch (DaoException de) {
            throw new ServiceException(de.getMessage());
        }
    }

    public String getVehicleIdBySlotId(String parkLocationId, String slotId) throws ServiceException {
        try {
            return DaoFactory.SLOT_DAO.getVehicleOnSlot(parkLocationId, slotId);
        } catch (DaoException de) {
            throw new ServiceException(de.getMessage());
        }
    }

    public Slot allocateFirstUnusedSlot(String parkLocationId, String vehicleId) throws ServiceException {
        try {
            Slot nearestSlot = DaoFactory.SLOT_DAO.getFirstUnUsedSlot(parkLocationId);
            nearestSlot.setVehicleId(vehicleId);
            DaoFactory.SLOT_DAO.allocateSlotToVehicle(parkLocationId, nearestSlot);
            return nearestSlot;
        } catch (DaoException de) {
            throw new ServiceException(de.getMessage());
        }
    }

    public List<Slot> getAllAllocatedSlots(String parkLocationId) throws ServiceException {
        try {
            return DaoFactory.SLOT_DAO.getAllAllocatedSlots(parkLocationId);
        } catch (DaoException de) {
            throw new ServiceException(de.getMessage());
        }
    }

    public void unAllocateSlot(String parkLocationId, String slotId) throws ServiceException {
        try {
            DaoFactory.SLOT_DAO.removeVehicleFromSlot(parkLocationId, new Slot(slotId));
        } catch (DaoException de) {
            throw new ServiceException(de.getMessage());
        }
    }
}