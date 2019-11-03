package com.gojek.parking.model;

import java.util.Objects;

/*
 * Object Oriented representation of Vehicle
 * This can be extended to any specific vehicle type like 4 Wheeler, 2 wheeler etc.
 */
public class Vehicle {
    private String registrationNumber;
    private String color;
    private VehicleType vehicleType;

    public Vehicle(String registrationNo, String color, VehicleType vehicleType){
        this.registrationNumber = registrationNo;
        this.color = color;
        this.vehicleType = vehicleType;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public String getColor() {
        return color;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public enum VehicleType {
        TWO_WHEELER,
        FOUR_WHEELER
    }
}