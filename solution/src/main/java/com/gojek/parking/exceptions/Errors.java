package com.gojek.parking.exceptions;


public class Errors {
    public static String DAO_ERROR_VEHICLE_EXISTS = "Vehicle %s already parked";
    public static String DAO_ERROR_NO_VEHICLE = "Not found";
    public static String DAO_ERROR_NO_EMPTY_SLOT = "Sorry, parking lot is full";
    public static String DAO_ERROR_WRONG_SLOT = "Wrong Slot %s";
    public static String DAO_ERROR_WRONG_PARK_LOCATION = "Wrong Park Location %s";
}