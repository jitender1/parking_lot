package com.gojek.parking.api;

import com.gojek.parking.exceptions.ServiceException;
import com.gojek.parking.model.Slot;

import java.util.List;

/**
 * Service used to keep information about Park Location and Parking Slots
 */
public interface ParkLocationService {

    Slot allocateFirstUnusedSlot(String vehicleId) throws ServiceException;

    void unAllocateSlot(String slotId) throws ServiceException;

    void initializeParkLocation(int numberOfSlots) throws ServiceException;

    void destroyParkLocation() throws ServiceException;

    String getSlotIdByVehicleId(String vehicleId) throws ServiceException;

    String getVehicleIdBySlotId(String slotId) throws ServiceException;

    List<Slot> getAllAllocatedSlots() throws ServiceException;

    List<Slot> getAllUnallocateSlots() throws ServiceException;
}