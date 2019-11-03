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

    public ParkingServiceImpl(ParkLocationService plService, VehicleService vService) {
        this.plService = plService;
        this.vService = vService;
    }

    public ParkingServiceImpl() {
        plService = ServiceFactory.getInstance().getParkLocationService();
        vService = ServiceFactory.getInstance().getVehicleService();
    }

    public String parkVehicle(String parkLocationId, Vehicle vehicle) throws ServiceException {
        Slot slot = plService.allocateFirstUnusedSlot(parkLocationId, vehicle.getRegistrationNumber());
        vService.addVehicle(parkLocationId, vehicle);
        return slot.getSlotId();
    }

    public void unParkVehicle(String parkLocationId, String slotId) throws ServiceException {
        String vehicleId  = plService.getVehicleIdBySlotId(parkLocationId, slotId);
        plService.unAllocateSlot(parkLocationId, slotId);
        vService.removeVehicle(parkLocationId, vehicleId);
    }

    public List<String> getRegistrationNumbersOfCarsByColor(String parkLocationId, String color) throws ServiceException {
        return vService.fetchVehiclesByColour(parkLocationId, color);
    }

    public String findVehicle(String parkLocationId, String vehicleRegistrationNumber) throws ServiceException {
        return plService.getSlotIdByVehicleId(parkLocationId, vehicleRegistrationNumber);
    }

    public List<String> getSlotNumbersByColor(String parkLocationId, String color) throws ServiceException {
        List<String> vehicleIds = getRegistrationNumbersOfCarsByColor(parkLocationId, color);
        List<String> slotIds = new ArrayList<>();
        for (String vehicleId : vehicleIds) {
            slotIds.add(plService.getSlotIdByVehicleId(parkLocationId, vehicleId));
        }
        return slotIds;
    }

    public ParkLocationStatus getParkingStatus(String parkLocationId) throws ServiceException {
        ParkLocationStatus status = new ParkLocationStatus();
        status.setParkLocationId(parkLocationId);
        List<Slot> slots = plService.getAllAllocatedSlots(parkLocationId);

        for (Slot slot : slots) {
            Vehicle vehicle = vService.getVehicle(parkLocationId, slot.getVehicleId());
            status.getParkInfos().add(new ParkLocationStatus.ParkInfo(slot.getSlotId(), vehicle.getRegistrationNumber(), vehicle.getColor()));
        }
        return status;
    }
}