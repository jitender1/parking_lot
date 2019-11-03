package com.gojek.parking.dao;

public class DaoFactory {

    public static final VehicleDao VEHICLE_DAO = VehicleDao.getInstance();

    public static final SlotDao SLOT_DAO = SlotDao.getInstance();
}