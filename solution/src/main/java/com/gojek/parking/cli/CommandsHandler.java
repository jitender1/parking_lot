package com.gojek.parking.cli;

import com.gojek.parking.api.ServiceFactory;
import com.gojek.parking.exceptions.CommandInputException;
import com.gojek.parking.exceptions.Errors;
import com.gojek.parking.exceptions.ServiceException;
import com.gojek.parking.messages.Messages;
import com.gojek.parking.model.ParkLocationStatus;
import com.gojek.parking.model.Vehicle;

import java.util.List;

public class CommandsHandler {

    public static void handleRequest(String action, String[] values) throws CommandInputException, ServiceException {

        switch (action) {
            case "create_parking_lot":
                if (values.length < 1) {
                    throw new CommandInputException(String.format(Errors.INPUT_ERROR_WRONG_COMMAND_FORMAT, "create_parking_lot <lot_size>"));
                }
                try {
                    int numOfSlots = Integer.valueOf(values[0]);
                    ServiceFactory.getInstance().getParkLocationService().initializeParkLocation(numOfSlots);
                    System.out.println(String.format(Messages.PARKING_LOT_CREATED, numOfSlots));
                } catch (NumberFormatException ne) {
                    throw new ServiceException(Errors.SERVICE_ERROR_INVALID_PARK_LOT_SIZE);
                }
                break;

            case "park":
                if (values.length < 2) {
                    throw new CommandInputException(String.format(Errors.INPUT_ERROR_WRONG_COMMAND_FORMAT, "park <vehiclenumber> <colors>"));
                }
                String vehicleRegNumber = values[0];
                String color = values[1];
                Vehicle vehicle = new Vehicle(vehicleRegNumber, color, Vehicle.VehicleType.FOUR_WHEELER);
                String vehicleId = ServiceFactory.getInstance().getParkingService().parkVehicle(vehicle);
                System.out.println(String.format(Messages.PARKING_SPACE_BLOCKED, vehicleId));
                break;

            case "leave":
                if (values.length < 1) {
                    throw new CommandInputException(String.format(Errors.INPUT_ERROR_WRONG_COMMAND_FORMAT, "leave <slotId>"));
                }
                String slotId = values[0];
                ServiceFactory.getInstance().getParkingService().unParkVehicle(slotId);
                System.out.println(String.format(Messages.SLOT_FREE, slotId));
                break;

            case "status":
                ParkLocationStatus status = ServiceFactory.getInstance().getParkingService().getParkingStatus();
                List<ParkLocationStatus.ParkInfo> parkInfoList = status.getParkInfos();
                System.out.println(("Slot No.    Registration No    Colour"));
                for (ParkLocationStatus.ParkInfo parkInfo : parkInfoList) {
                    System.out.println(String.format("%s           %s      %s", parkInfo.getSlotId(), parkInfo.getVehicleId(), parkInfo.getColor()));
                }
                break;

            case "registration_numbers_for_cars_with_colour":
                if (values.length < 1) {
                    throw new CommandInputException(String.format(Errors.INPUT_ERROR_WRONG_COMMAND_FORMAT, "registration_numbers_for_cars_with_colour <color>"));
                }
                List<String> vehicleIds = ServiceFactory.getInstance().getParkingService().getRegistrationNumbersOfCarsByColor(values[0]);
                System.out.println(String.join(", ", vehicleIds));
                break;

            case "slot_numbers_for_cars_with_colour":
                if (values.length < 1) {
                    throw new CommandInputException(String.format(Errors.INPUT_ERROR_WRONG_COMMAND_FORMAT, "slot_numbers_for_cars_with_colour <color>"));
                }
                List<String> slots = ServiceFactory.getInstance().getParkingService().getSlotNumbersByColor(values[0]);
                System.out.println(String.join(", ", slots));
                break;

            case "slot_number_for_registration_number":
                if (values.length < 1) {
                    throw new CommandInputException(String.format(Errors.INPUT_ERROR_WRONG_COMMAND_FORMAT, "slot_number_for_registration_number <vehicle_reg_number>"));
                }
                String slotNumber = ServiceFactory.getInstance().getParkingService().findVehicle(values[0]);
                System.out.println(slotNumber);
                break;
            case "destroy_parking_lot":
                ServiceFactory.getInstance().getParkingService().destroyParkLocation();
                break;

            default:
                throw new CommandInputException(Errors.INPUT_ERROR_UNSUPPORT_COMMAND);
        }
    }
}