package com.gojek.parking.api;

import com.gojek.parking.exceptions.ServiceException;
import com.gojek.parking.model.ParkLocationStatus;
import com.gojek.parking.model.Vehicle;

import java.util.List;

/**
 * Service used to park and unpark vehicle at specific parking location
 */
public interface ParkingService {

    String parkVehicle(String parkLocationId, Vehicle vehicle) throws ServiceException;

    void unParkVehicle(String parkLocationId, String slotId) throws ServiceException;

    String findVehicle(String parkLocationid, String vehicleId) throws ServiceException;

    List<String> getRegistrationNumbersOfCarsByColor(String parkLocationId, String color) throws ServiceException;

    List<String> getSlotNumbersByColor(String parkLocationId, String color) throws ServiceException;

    ParkLocationStatus getParkingStatus(String parkLocationId) throws ServiceException;
}