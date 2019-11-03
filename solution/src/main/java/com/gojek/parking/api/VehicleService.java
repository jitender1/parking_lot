package com.gojek.parking.api;

import com.gojek.parking.exceptions.ServiceException;
import com.gojek.parking.model.Vehicle;

import java.util.List;

/**
 * Service used to keep information about parked vehicles
 */
public interface VehicleService {

    List<String> fetchVehiclesByColour(String color) throws ServiceException;

    void addVehicle(Vehicle vehicle) throws ServiceException;

    void removeVehicle(String registrationNumber) throws ServiceException;

    Vehicle getVehicle(String registrationNumber) throws ServiceException;

    void removeAllVehicles() throws ServiceException;
}