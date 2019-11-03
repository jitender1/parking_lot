package com.gojek.parking.dao;

import com.gojek.parking.exceptions.DaoException;
import com.gojek.parking.exceptions.Errors;
import com.gojek.parking.model.Vehicle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VehicleDao {
    private static VehicleDao vehicleDao;
    Map<String, List<String>> colorToVehiclesMap;
    Map<String, Vehicle> vehicleNumberToVehicleMap;

    private VehicleDao() {
        colorToVehiclesMap = new HashMap<>();
        vehicleNumberToVehicleMap = new HashMap<>();
    }

    public static VehicleDao getInstance() {
        if (vehicleDao == null) {
            vehicleDao = new VehicleDao();
        }
        return vehicleDao;
    }

    public void addVehicle(Vehicle vehicle) throws DaoException {

        if (vehicleNumberToVehicleMap.containsKey(vehicle.getRegistrationNumber())) {
            throw new DaoException(String.format(Errors.DAO_ERROR_VEHICLE_EXISTS, vehicle.getRegistrationNumber()));
        } else {
            vehicleNumberToVehicleMap.put(vehicle.getRegistrationNumber(), vehicle);
        }

        String colorLowerCase = vehicle.getColor().toLowerCase();
        if (colorToVehiclesMap.containsKey(colorLowerCase)) {
            colorToVehiclesMap.get(colorLowerCase).add(vehicle.getRegistrationNumber());
        } else {
            List<String> vehicles = new ArrayList<>();
            vehicles.add(vehicle.getRegistrationNumber());
            colorToVehiclesMap.put(vehicle.getColor().toLowerCase(), vehicles);
        }
    }

    public void removeVehicle(String vehicleId) throws DaoException {
        if (vehicleNumberToVehicleMap.containsKey(vehicleId)) {
            Vehicle vehicle = vehicleNumberToVehicleMap.get(vehicleId);
            String color = vehicle.getColor().toLowerCase();
            vehicleNumberToVehicleMap.remove(vehicleId);
            if (colorToVehiclesMap.containsKey(color)) {
                boolean st = colorToVehiclesMap.get(color).remove(vehicle.getRegistrationNumber());
                return;
            }
        }
        throw new DaoException(String.format(Errors.DAO_ERROR_NO_VEHICLE, vehicleId));
    }

    public Vehicle getVehicleById(String vehicleId) throws DaoException {
        if (vehicleNumberToVehicleMap.containsKey(vehicleId)) {
            return vehicleNumberToVehicleMap.get(vehicleId);
        }
        throw new DaoException(Errors.DAO_ERROR_NO_VEHICLE);
    }

    public List<String> getAllVehiclesByColor(String color) {
        String colorLowerCase = color.toLowerCase();
        if (colorToVehiclesMap.containsKey(colorLowerCase)) {
            return colorToVehiclesMap.get(colorLowerCase);
        }
        return Collections.EMPTY_LIST;
    }

    public void removeAllVehicles() {
        colorToVehiclesMap = new HashMap<>();
        vehicleNumberToVehicleMap = new HashMap<>();
    }
}