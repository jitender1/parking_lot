package com.gojek.parking.api;

import com.gojek.parking.exceptions.ServiceException;
import com.gojek.parking.model.Vehicle;

import java.util.List;

/**
 * Service used to keep information about parked vehicles
 */
public interface VehicleService {

    List<String> fetchVehiclesByColour(String parkLocationId, String color) throws ServiceException;

    void addVehicle(String parkLocationId, Vehicle vehicle) throws ServiceException;

    void removeVehicle(String parkLocationId, String registrationNumber) throws ServiceException ;

    Vehicle getVehicle(String parkLocationId, String registrationNumber) throws ServiceException;
}