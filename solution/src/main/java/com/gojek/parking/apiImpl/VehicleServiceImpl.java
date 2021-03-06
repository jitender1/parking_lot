package com.gojek.parking.apiImpl;

import com.gojek.parking.api.VehicleService;
import com.gojek.parking.dao.DaoFactory;
import com.gojek.parking.exceptions.DaoException;
import com.gojek.parking.exceptions.ServiceException;
import com.gojek.parking.model.Vehicle;

import java.util.List;

public class VehicleServiceImpl implements VehicleService {
    public List<String> fetchVehiclesByColour(String color) {
        return DaoFactory.VEHICLE_DAO.getAllVehiclesByColor(color);
    }

    public void addVehicle(Vehicle vehicle) throws ServiceException {
        try {
            DaoFactory.VEHICLE_DAO.addVehicle(vehicle);
        } catch (DaoException de) {
            throw new ServiceException(de.getMessage());
        }
    }

    public void removeVehicle(String registrationNumber) throws ServiceException {
        try {
            DaoFactory.VEHICLE_DAO.removeVehicle(registrationNumber);
        } catch (DaoException de) {
            throw new ServiceException(de.getMessage());
        }
    }

    public Vehicle getVehicle(String registrationNumber) throws ServiceException {
        try {
            return DaoFactory.VEHICLE_DAO.getVehicleById(registrationNumber);
        } catch (DaoException de) {
            throw new ServiceException(de.getMessage());
        }
    }

    public void removeAllVehicles() throws ServiceException {
        DaoFactory.VEHICLE_DAO.removeAllVehicles();
    }
}