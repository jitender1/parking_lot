package com.gojek.parking.dao;

public class DaoFactory {

    public static final VehicleDao VEHICLE_DAO = VehicleDao.getInstance();

    public static final ParkLocationDao PARK_LOCATION_DAO = ParkLocationDao.getInstance();
}