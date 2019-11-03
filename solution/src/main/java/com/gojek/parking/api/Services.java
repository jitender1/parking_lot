package com.gojek.parking.api;

public interface Services {
    ParkingService getParkingService();

    ParkLocationService getParkLocationService();

    VehicleService getVehicleService();
}