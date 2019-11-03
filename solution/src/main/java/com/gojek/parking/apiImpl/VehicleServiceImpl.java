package com.gojek.parking.apiImpl;

import com.gojek.parking.api.VehicleService;
import com.gojek.parking.dao.DaoFactory;
import com.gojek.parking.exceptions.DaoException;
import com.gojek.parking.exceptions.ServiceException;
import com.gojek.parking.model.Vehicle;

import java.util.List;

public class VehicleServiceImpl implements VehicleService {
    public List<String> fetchVehiclesByColour(String parkLocationId, String color) {
        return DaoFactory.VEHICLE_DAO.getAllVehiclesByColor(parkLocationId, color);
    }

    public void addVehicle(String parkLocationId, Vehicle vehicle) throws ServiceException {
        try {
            DaoFactory.VEHICLE_DAO.addVehicle(parkLocationId, vehicle);
        } catch (DaoException de) {
            throw new ServiceException(de.getMessage());
        }
    }

    public void removeVehicle(String parkLocationId, String registrationNumber) throws ServiceException {
        try {
            DaoFactory.VEHICLE_DAO.removeVehicle(parkLocationId, registrationNumber);
        } catch (DaoException de) {
            throw new ServiceException(de.getMessage());
        }
    }

    public Vehicle getVehicle(String parkLocationId, String registrationNumber) throws ServiceException {
        try {
            return DaoFactory.VEHICLE_DAO.getVehicleById(parkLocationId, registrationNumber);
        } catch (DaoException de) {
            throw new ServiceException(de.getMessage());
        }
    }
}