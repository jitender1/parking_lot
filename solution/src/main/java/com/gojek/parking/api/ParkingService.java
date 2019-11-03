package com.gojek.parking.api;

import com.gojek.parking.exceptions.ServiceException;
import com.gojek.parking.model.ParkLocationStatus;
import com.gojek.parking.model.Vehicle;

import java.util.List;

/**
 * Service used to park and unpark vehicle at specific parking location
 */
public interface ParkingService {

    String parkVehicle(Vehicle vehicle) throws ServiceException;

    void unParkVehicle(String slotId) throws ServiceException;

    String findVehicle(String vehicleId) throws ServiceException;

    List<String> getRegistrationNumbersOfCarsByColor(String color) throws ServiceException;

    List<String> getSlotNumbersByColor(String color) throws ServiceException;

    ParkLocationStatus getParkingStatus() throws ServiceException;

    void initializeParkLocation(int numberOfSlots) throws ServiceException;

    void destroyParkLocation() throws ServiceException;
}