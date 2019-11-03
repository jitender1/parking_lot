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
    Map<String, VehicleInfo> locationToVehicleStoreMap;

    private VehicleDao() {
        locationToVehicleStoreMap = new HashMap<>();
    }

    public static VehicleDao getInstance() {
        if (vehicleDao == null) {
            vehicleDao = new VehicleDao();
        }
        return vehicleDao;
    }

    public void addVehicle(String parkLocationId, Vehicle vehicle) throws DaoException {
        final String vehicleId = vehicle.getRegistrationNumber();
        final String color = vehicle.getColor().toLowerCase();
        if (locationToVehicleStoreMap.containsKey(parkLocationId)) {
            VehicleInfo store = locationToVehicleStoreMap.get(parkLocationId);
            Map<String, List<String>> colorToVehiclesMap = store.getColorToVehiclesMap();
            Map<String, Vehicle> vehicleNumberToVehicleMap = store.getVehicleNumberToVehicleMap();
            if (!vehicleNumberToVehicleMap.containsKey(vehicleId)) {
                vehicleNumberToVehicleMap.put(vehicleId, vehicle);
                if (colorToVehiclesMap.containsKey(color)) {
                    colorToVehiclesMap.get(color).add(vehicle.getRegistrationNumber());
                } else {
                    List<String> vehicles = new ArrayList<>();
                    vehicles.add(vehicle.getRegistrationNumber());
                    colorToVehiclesMap.put(color, vehicles);
                }
                return;
            }
            throw new DaoException(String.format(Errors.DAO_ERROR_VEHICLE_EXISTS, vehicleId));
        } else {
            VehicleInfo vehiclesInfo = new VehicleInfo();
            List<String> vehicles = new ArrayList<>();
            vehicles.add(vehicle.getRegistrationNumber());
            vehiclesInfo.getColorToVehiclesMap().put(vehicle.getColor().toLowerCase(), vehicles);
            vehiclesInfo.getVehicleNumberToVehicleMap().put(vehicle.getRegistrationNumber(), vehicle);
            locationToVehicleStoreMap.put(parkLocationId, vehiclesInfo);
        }
    }

    public void removeVehicle(String parkLocationId, String vehicleId) throws DaoException {
        if (locationToVehicleStoreMap.containsKey(parkLocationId)) {
            VehicleInfo vehiclesInfo = locationToVehicleStoreMap.get(parkLocationId);
            Map<String, List<String>> colorToVehiclesMap = vehiclesInfo.getColorToVehiclesMap();
            Map<String, Vehicle> vehicleNumberToVehicleMap = vehiclesInfo.getVehicleNumberToVehicleMap();
            if (vehicleNumberToVehicleMap.containsKey(vehicleId)) {
                Vehicle vehicle = vehicleNumberToVehicleMap.get(vehicleId);
                String color = vehicle.getColor().toLowerCase();
                vehicleNumberToVehicleMap.remove(vehicleId);
                if (colorToVehiclesMap.containsKey(color)) {
                    colorToVehiclesMap.get(color).remove(vehicle);
                    return;
                }
            }
            throw new DaoException(String.format(Errors.DAO_ERROR_NO_VEHICLE, vehicleId));
        }
        throw new DaoException(String.format(Errors.DAO_ERROR_WRONG_PARK_LOCATION, parkLocationId));
    }

    public Vehicle getVehicleById(String parkLocationId, String vehicleId) throws DaoException {
        if (locationToVehicleStoreMap.containsKey(parkLocationId)) {
            VehicleInfo store = locationToVehicleStoreMap.get(parkLocationId);
            Map<String, Vehicle> vehicleNumberToVehicleMap = store.getVehicleNumberToVehicleMap();
            if (vehicleNumberToVehicleMap.containsKey(vehicleId)) {
                return vehicleNumberToVehicleMap.get(vehicleId);
            }
            throw new DaoException(Errors.DAO_ERROR_NO_VEHICLE);
        }
        throw new DaoException(String.format(Errors.DAO_ERROR_WRONG_PARK_LOCATION, parkLocationId));
    }

    public List<String> getAllVehiclesByColor(String parkLocationId, String color) {
        String colorLowerCase = color.toLowerCase();
        if (locationToVehicleStoreMap.containsKey(parkLocationId)) {
            VehicleInfo store = locationToVehicleStoreMap.get(parkLocationId);
            Map<String, List<String>> colorToVehiclesMap = store.getColorToVehiclesMap();
            if (colorToVehiclesMap.containsKey(colorLowerCase)) {
                return colorToVehiclesMap.get(colorLowerCase);
            }
            return Collections.EMPTY_LIST;
        }
        return Collections.EMPTY_LIST;
    }

    public void removeAllVehicles(String parkLocationId) {
        if (locationToVehicleStoreMap.containsKey(parkLocationId)) {
            locationToVehicleStoreMap.remove(parkLocationId);
        }
    }

    class VehicleInfo {
        Map<String, List<String>> colorToVehiclesMap;
        Map<String, Vehicle> vehicleNumberToVehicleMap;

        VehicleInfo() {
            colorToVehiclesMap = new HashMap<String, List<String>>();
            vehicleNumberToVehicleMap = new HashMap<String, Vehicle>();
        }

        public Map<String, List<String>> getColorToVehiclesMap() {
            return colorToVehiclesMap;
        }

        public Map<String, Vehicle> getVehicleNumberToVehicleMap() {
            return vehicleNumberToVehicleMap;
        }
    }
}