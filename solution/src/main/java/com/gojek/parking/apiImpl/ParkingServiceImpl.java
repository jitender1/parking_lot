package com.gojek.parking.apiImpl;

import com.gojek.parking.api.ParkLocationService;
import com.gojek.parking.api.ParkingService;
import com.gojek.parking.api.ServiceFactory;
import com.gojek.parking.api.VehicleService;
import com.gojek.parking.exceptions.ServiceException;
import com.gojek.parking.model.ParkLocationStatus;
import com.gojek.parking.model.Slot;
import com.gojek.parking.model.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class ParkingServiceImpl implements ParkingService {

    private ParkLocationService plService;
    private VehicleService vService;

    public ParkingServiceImpl() {
        plService = ServiceFactory.getInstance().getParkLocationService();
        vService = ServiceFactory.getInstance().getVehicleService();
    }

    public String parkVehicle(Vehicle vehicle) throws ServiceException {
        Slot slot = plService.allocateFirstUnusedSlot(vehicle.getRegistrationNumber());
        vService.addVehicle(vehicle);
        return slot.getSlotId();
    }

    public void unParkVehicle(String slotId) throws ServiceException {
        String vehicleId  = plService.getVehicleIdBySlotId(slotId);
        vService.removeVehicle(vehicleId);
        plService.unAllocateSlot(slotId);
    }

    public List<String> getRegistrationNumbersOfCarsByColor(String color) throws ServiceException {
        return vService.fetchVehiclesByColour(color);
    }

    public String findVehicle(String vehicleRegistrationNumber) throws ServiceException {
        return plService.getSlotIdByVehicleId(vehicleRegistrationNumber);
    }

    public List<String> getSlotNumbersByColor(String color) throws ServiceException {
        List<String> vehicleIds = getRegistrationNumbersOfCarsByColor(color);
        List<String> slotIds = new ArrayList<>();
        for (String vehicleId : vehicleIds) {
            slotIds.add(plService.getSlotIdByVehicleId(vehicleId));
        }
        return slotIds;
    }

    public ParkLocationStatus getParkingStatus() throws ServiceException {
        ParkLocationStatus status = new ParkLocationStatus();
        List<Slot> slots = plService.getAllAllocatedSlots();

        for (Slot slot : slots) {
            Vehicle vehicle = vService.getVehicle(slot.getVehicleId());
            status.getParkInfos().add(new ParkLocationStatus.ParkInfo(slot.getSlotId(), vehicle.getRegistrationNumber(), vehicle.getColor()));
        }
        return status;
    }

    @Override
    public void initializeParkLocation(int numberOfSlots) throws ServiceException {
        plService.initializeParkLocation(numberOfSlots);
    }

    @Override
    public void destroyParkLocation() throws ServiceException {
        vService.removeAllVehicles();
        plService.destroyParkLocation();
    }
}