package com.gojek.parking.apiImpl;

import com.gojek.parking.api.ServiceFactory;
import com.gojek.parking.exceptions.ServiceException;
import org.junit.Assert;
import org.junit.Test;

public class ParkLocationServiceImplTest {

    @Test
    public void testGetVehicleIdByInvalidSlotId() throws Exception{
        try{
            ServiceFactory.getInstance().getParkLocationService().getVehicleIdBySlotId("5");
        }catch (ServiceException se){
            Assert.assertTrue(se.getMessage().contains("Not found"));
        }
    }

    @Test
    public void testUnallocateInvalidSlot() throws Exception{
        try{
            ServiceFactory.getInstance().getParkLocationService().unAllocateSlot("5");
        }catch (ServiceException se){
            System.out.println(se.getMessage());
            Assert.assertTrue(se.getMessage().contains("Wrong Slot 5"));
        }
    }
}
