package com.gojek.parking.cli;

import com.gojek.parking.api.ServiceFactory;
import com.gojek.parking.exceptions.CommandInputException;
import com.gojek.parking.model.Slot;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class CommandsHandlerTest {

    @After
    public void cleanUp() throws Exception {
        CommandsHandler.handleRequest("destroy_parking_lot", new String[0]);
    }

    @Test
    public void testCreateParkingLotCommand() throws Exception {
        String[] values = {"2"};
        CommandsHandler.handleRequest("create_parking_lot", values);
        List<Slot> slots = ServiceFactory.getInstance().getParkLocationService().getAllUnallocateSlots();
        Assert.assertEquals(2, slots.size());
    }

    @Test
    public void testCreateParkingLotCommandWithoutArguments() throws Exception {
        try {
            String[] values = {};
            CommandsHandler.handleRequest("create_parking_lot", values);
        } catch (CommandInputException cie) {
            Assert.assertTrue(cie.getMessage().contains("Wrong command format"));
        }
    }

    @Test
    public void testParkVehicleCommand() throws Exception {
        String[] createCommandArgs = {"2"};
        CommandsHandler.handleRequest("create_parking_lot", createCommandArgs);
        String[] parkCommandArgs = {"HR-1234", "green"};
        CommandsHandler.handleRequest("park", parkCommandArgs);
        List<Slot> slots = ServiceFactory.getInstance().getParkLocationService().getAllUnallocateSlots();
        Assert.assertEquals(1, slots.size());
    }

    @Test
    public void testParkVehicleCommandWithoutArgument() throws Exception {
        try {
            String[] createCommandArgs = {"2"};
            CommandsHandler.handleRequest("create_parking_lot", createCommandArgs);
            String[] parkCommandArgs = {};
            CommandsHandler.handleRequest("park", parkCommandArgs);
            List<Slot> slotIds = ServiceFactory.getInstance().getParkLocationService().getAllUnallocateSlots();
            Assert.assertEquals(1, slotIds.size());
        } catch (CommandInputException cie) {
            Assert.assertTrue(cie.getMessage().contains("Wrong command format"));
        }
    }

    @Test
    public void testLeaveVehicleCommand() throws Exception {
        String[] createCommandArgs = {"2"};
        CommandsHandler.handleRequest("create_parking_lot", createCommandArgs);

        String[] parkCommandArgs = {"HR-1234", "green"};
        CommandsHandler.handleRequest("park", parkCommandArgs);

        List<Slot> slotIds = ServiceFactory.getInstance().getParkLocationService().getAllAllocatedSlots();

        String[] leaveCommandArgs = {slotIds.get(0).getSlotId()};
        CommandsHandler.handleRequest("leave", leaveCommandArgs);

        Assert.assertEquals(2, ServiceFactory.getInstance().getParkLocationService().getAllUnallocateSlots().size());
    }

    @Test
    public void testLeaveVehicleCommandWithoutArgument() throws Exception {

        try {
            String[] createCommandArgs = {"2"};
            CommandsHandler.handleRequest("create_parking_lot", createCommandArgs);

            String[] parkCommandArgs = {"HR-1234", "green"};
            CommandsHandler.handleRequest("park", parkCommandArgs);

            String[] leaveCommandArgs = {};
            CommandsHandler.handleRequest("leave", leaveCommandArgs);
        } catch (CommandInputException cie) {
            Assert.assertTrue(cie.getMessage().contains("Wrong command format"));
        }
    }

    @Test
    public void testStatusCommand() throws Exception {

        String[] createCommandArgs = {"2"};
        CommandsHandler.handleRequest("create_parking_lot", createCommandArgs);

        String[] parkCommandArgs = {"HR-1234", "green"};
        CommandsHandler.handleRequest("park", parkCommandArgs);

        String[] statusCommandArgs = {};
        CommandsHandler.handleRequest("status", statusCommandArgs);
    }

    @Test
    public void testCarsByColorCommand() throws Exception {

        String[] createCommandArgs = {"2"};
        CommandsHandler.handleRequest("create_parking_lot", createCommandArgs);

        String[] parkCommandArgs = {"HR-1234", "green"};
        CommandsHandler.handleRequest("park", parkCommandArgs);

        String[] carsCommandArgs = {"green"};
        CommandsHandler.handleRequest("registration_numbers_for_cars_with_colour", carsCommandArgs);
    }

    @Test
    public void testCarsByColorCommandWithOutArgument() throws Exception {
        try {
            String[] createCommandArgs = {"2"};
            CommandsHandler.handleRequest("create_parking_lot", createCommandArgs);

            String[] parkCommandArgs = {"HR-1234", "green"};
            CommandsHandler.handleRequest("park", parkCommandArgs);

            String[] carsCommandArgs = {};
            CommandsHandler.handleRequest("registration_numbers_for_cars_with_colour", carsCommandArgs);
        }catch (CommandInputException cie){
            Assert.assertTrue(cie.getMessage().contains("Wrong command format"));
        }
    }

    @Test
    public void testSlotsByColorCommand() throws Exception {

        String[] createCommandArgs = {"2"};
        CommandsHandler.handleRequest("create_parking_lot", createCommandArgs);

        String[] parkCommandArgs = {"HR-1234", "green"};
        CommandsHandler.handleRequest("park", parkCommandArgs);

        String[] slotsCommandArgs = {"green"};
        CommandsHandler.handleRequest("slot_numbers_for_cars_with_colour", slotsCommandArgs);
    }

    @Test
    public void testSlotsByColorCommandWithOutArgument() throws Exception {
        try {
            String[] createCommandArgs = {"2"};
            CommandsHandler.handleRequest("create_parking_lot", createCommandArgs);

            String[] parkCommandArgs = {"HR-1234", "green"};
            CommandsHandler.handleRequest("park", parkCommandArgs);

            String[] slotsCommandArgs = {};
            CommandsHandler.handleRequest("slot_numbers_for_cars_with_colour", slotsCommandArgs);
        }catch (CommandInputException cie){
            Assert.assertTrue(cie.getMessage().contains("Wrong command format"));
        }
    }

    @Test
    public void testSlotForVehicleCommand() throws Exception {

        String[] createCommandArgs = {"2"};
        CommandsHandler.handleRequest("create_parking_lot", createCommandArgs);

        String[] parkCommandArgs = {"HR-1234", "green"};
        CommandsHandler.handleRequest("park", parkCommandArgs);

        String[] slotCommandArgs = {"HR-1234"};
        CommandsHandler.handleRequest("slot_number_for_registration_number", slotCommandArgs);
    }

    @Test
    public void testSlotForVehicleCommandWithOutArgument() throws Exception {
        try {
            String[] createCommandArgs = {"2"};
            CommandsHandler.handleRequest("create_parking_lot", createCommandArgs);

            String[] parkCommandArgs = {"HR-1234", "green"};
            CommandsHandler.handleRequest("park", parkCommandArgs);

            String[] slotCommandArgs = {};
            CommandsHandler.handleRequest("slot_number_for_registration_number", slotCommandArgs);
        }catch (CommandInputException cie){
            Assert.assertTrue(cie.getMessage().contains("Wrong command format"));
        }
    }

    @Test
    public void testInvalidCommand() throws Exception {
        try {
            String[] slotCommandArgs = {};
            CommandsHandler.handleRequest("invalid_command", slotCommandArgs);
        }catch (CommandInputException cie){
            Assert.assertTrue(cie.getMessage().contains("Unsupported command"));
        }
    }
}
