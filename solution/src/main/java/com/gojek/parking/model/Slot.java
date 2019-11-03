package com.gojek.parking.model;

/*
 * Object Orientated representation of Slot where vehicle would be parked.
 */
public class Slot  implements Comparable<Slot>{
    private String slotId;
    private String vehicleId;

    public Slot(String slotId){
        this.slotId = slotId;
    }

    public String getSlotId() {
        return slotId;
    }

    public void setSlotId(String slotId) {
        this.slotId = slotId;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Slot slot = (Slot) o;
        return slot.slotId.equals(slotId);
    }

    @Override
    public int hashCode() {

        return slotId.hashCode();
    }

    public int compareTo(Slot o) {
        return slotId.compareTo(o.slotId);
    }
}