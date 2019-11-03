package com.gojek.parking.exceptions;


public class Errors {
    public static String DAO_ERROR_VEHICLE_EXISTS = "Vehicle %s already parked";

    public static String DAO_ERROR_NO_VEHICLE = "Not found";

    public static String DAO_ERROR_NO_EMPTY_SLOT = "Sorry, parking lot is full";

    public static String DAO_ERROR_WRONG_SLOT = "Wrong Slot %s";

    public static String INPUT_ERROR_WRONG_COMMAND_FORMAT = "Wrong command format valid format is: s%s";

    public static String INPUT_ERROR_UNSUPPORT_COMMAND= "Unsupported command";

    public static String SERVICE_ERROR_PARK_LOT_ALREADY_INITIALIZED = "Parking Lot already initialized";

}