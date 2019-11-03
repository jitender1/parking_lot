package com.gojek.parking.apiImpl;

import com.gojek.parking.api.ParkLocationService;
import com.gojek.parking.dao.DaoFactory;
import com.gojek.parking.exceptions.DaoException;
import com.gojek.parking.exceptions.Errors;
import com.gojek.parking.exceptions.ServiceException;
import com.gojek.parking.model.Slot;

import java.util.List;

public class ParkLocationServiceImpl implements ParkLocationService {

    public void initializeParkLocation(int numberOfSlots) throws ServiceException {
        if(DaoFactory.SLOT_DAO.getAllUnUsedSlots().size() == 0 && DaoFactory.SLOT_DAO.getAllAllocatedSlots().size() == 0){
            for (int slotNumber = 1; slotNumber <= numberOfSlots; slotNumber++) {
                DaoFactory.SLOT_DAO.createSlot(new Slot(String.valueOf(slotNumber)));
            }
            return;
        }
        throw new ServiceException(Errors.SERVICE_ERROR_PARK_LOT_ALREADY_INITIALIZED);
    }

    @Override
    public void destroyParkLocation() throws ServiceException {
        DaoFactory.SLOT_DAO.destroyParkLocation();
    }

    public String getSlotIdByVehicleId(String vehicleId) throws ServiceException {
        try {
            Slot slot = DaoFactory.SLOT_DAO.getSlotForVehicle(vehicleId);
            return slot.getSlotId();
        } catch (DaoException de) {
            throw new ServiceException(de.getMessage());
        }
    }

    public String getVehicleIdBySlotId(String slotId) throws ServiceException {
        try {
            return DaoFactory.SLOT_DAO.getVehicleOnSlot(slotId);
        } catch (DaoException de) {
            throw new ServiceException(de.getMessage());
        }
    }

    public Slot allocateFirstUnusedSlot(String vehicleId) throws ServiceException {
        try {
            Slot nearestSlot = DaoFactory.SLOT_DAO.getFirstUnUsedSlot();
            nearestSlot.setVehicleId(vehicleId);
            DaoFactory.SLOT_DAO.allocateSlotToVehicle(nearestSlot);
            return nearestSlot;
        } catch (DaoException de) {
            throw new ServiceException(de.getMessage());
        }
    }

    public List<Slot> getAllAllocatedSlots() throws ServiceException {
        return DaoFactory.SLOT_DAO.getAllAllocatedSlots();
    }

    public void unAllocateSlot(String slotId) throws ServiceException {
        try {
            DaoFactory.SLOT_DAO.removeVehicleFromSlot(new Slot(slotId));
        } catch (DaoException de) {
            throw new ServiceException(de.getMessage());
        }
    }

    public List<Slot> getAllUnallocateSlots() throws ServiceException {
        return DaoFactory.SLOT_DAO.getAllUnUsedSlots();
    }
}