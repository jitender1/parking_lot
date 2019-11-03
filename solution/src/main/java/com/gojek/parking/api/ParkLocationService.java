package com.gojek.parking.api;

import com.gojek.parking.exceptions.ServiceException;
import com.gojek.parking.model.Slot;

import java.util.List;

/**
 * Service used to keep information about Park Location and Parking Slots
 */
public interface ParkLocationService {

    Slot allocateFirstUnusedSlot(String parkLocationId, String vehicleId) throws ServiceException;

    void unAllocateSlot(String parkLocationId, String slotId) throws ServiceException;

    void initializeParkLocation(String parkLocationId, int numberOfSlots) throws ServiceException;

    String getSlotIdByVehicleId(String parkLocationId, String vehicleId) throws ServiceException;

    String getVehicleIdBySlotId(String parkLocationId, String slotId) throws ServiceException;

    List<Slot> getAllAllocatedSlots(String parkLocationId) throws ServiceException;
}