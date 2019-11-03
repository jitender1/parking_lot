package com.gojek.parking.api;

import com.gojek.parking.apiImpl.ParkLocationServiceImpl;
import com.gojek.parking.apiImpl.ParkingServiceImpl;
import com.gojek.parking.apiImpl.VehicleServiceImpl;

public class ServiceFactory implements Services {
    private static ServiceFactory serviceFactory;
    private ParkingService parkingService;
    private ParkLocationService parkLocationService;
    private VehicleService vehicleService;

    private ServiceFactory() {
    }

    public static ServiceFactory getInstance() {
        if (serviceFactory == null) {
            serviceFactory = new ServiceFactory();
        }
        return serviceFactory;
    }

    public ParkingService getParkingService() {
        if (parkingService == null) {
            parkingService = new ParkingServiceImpl();
        }
        return parkingService;
    }

    public ParkLocationService getParkLocationService() {
        if (parkLocationService == null) {
            parkLocationService = new ParkLocationServiceImpl();
        }
        return parkLocationService;
    }

    public VehicleService getVehicleService() {
        if (vehicleService == null) {
            vehicleService = new VehicleServiceImpl();
        }
        return vehicleService;
    }
}
