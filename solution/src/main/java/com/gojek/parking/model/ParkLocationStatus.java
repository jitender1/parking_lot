package com.gojek.parking.model;

import java.util.ArrayList;
import java.util.List;

/*
 * Object Oriented representation of Parking Status at Location
 */
public class ParkLocationStatus {
    private List<ParkInfo> parkInfos;

    public ParkLocationStatus() {
        this.parkInfos = new ArrayList<ParkInfo>();
    }

    public List<ParkInfo> getParkInfos() {
        return parkInfos;
    }

    public static class ParkInfo {
        private String slotId;
        private String vehicleId;
        private String color;

        public ParkInfo(String slotId, String vehicleId, String color) {
            this.slotId = slotId;
            this.vehicleId = vehicleId;
            this.color = color;
        }

        public String getSlotId() {
            return slotId;
        }

        public String getVehicleId() {
            return vehicleId;
        }

        public String getColor() {
            return color;
        }
    }
}