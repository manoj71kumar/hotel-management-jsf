package com.hotel;

import com.hotels.domain.Rooms;
import com.hotels.repository.RoomsRepository;
import com.hotels.servlet.jaxWSDL.RoomsWebServiceImpl;
import org.junit.Assert;
import org.junit.Test;

public class RoomsTest {

    public static final String ROOM_TYPE="Single Bed";
    public static final double ROOM_PRICE_PER_NIGHT = 1500;
    public static final String ROOM_ROOM_DESCRIPTION="With Balcony and Hot Water";

    public static final String ROOMS_TYPE="Double Bed";
    public static final double ROOMS_PRICE_PER_NIGHT = 3500;
    public static final String ROOMS_ROOM_DESCRIPTION="With Balcony and Hot Water and Free Parking and BreakFast";


    @Test
    public void crudTest() throws Exception {

        Rooms rooms = new Rooms(null,ROOM_TYPE,ROOM_PRICE_PER_NIGHT,ROOM_ROOM_DESCRIPTION);
        RoomsRepository roomsRepository = RoomsRepository.getInstance();
        roomsRepository.save(rooms);
        Assert.assertEquals(roomsRepository.getCounts() , 7);

        Assert.assertNotNull(rooms.getId());
        Assert.assertEquals(rooms.getRoomType(),ROOM_TYPE);
        Assert.assertEquals(rooms.getPricePerNight(),ROOM_PRICE_PER_NIGHT,0);
        Assert.assertEquals(rooms.getDescription(),ROOM_ROOM_DESCRIPTION);


        rooms.setRoomType(ROOMS_TYPE);
        rooms.setPricePerNight(ROOMS_PRICE_PER_NIGHT);
        rooms.setDescription(ROOMS_ROOM_DESCRIPTION);
        roomsRepository.save(rooms);

        Rooms rooms1= roomsRepository.findById(rooms.getId());
        compare(rooms, rooms1);

        Rooms byId= roomsRepository.findById(rooms.getId());
        compare(byId, rooms);

        roomsRepository.delete(rooms1.getId());
        Assert.assertEquals(roomsRepository.getCounts(),7);

    }
    private void compare(Rooms rooms , Rooms rooms1){
        Assert.assertEquals(rooms.getRoomType(),rooms1.getRoomType());
        Assert.assertEquals(rooms.getPricePerNight(),rooms1.getPricePerNight(),0);
        Assert.assertEquals(rooms.getDescription(),rooms.getDescription());

    }

    @Test
    public void crudJaxWebServiceTest() throws Exception {

        Rooms rooms = new Rooms(null,ROOM_TYPE,ROOM_PRICE_PER_NIGHT,ROOM_ROOM_DESCRIPTION);
        RoomsWebServiceImpl roomService = RoomsWebServiceImpl.getInstance();
        roomService.save(rooms);
        Assert.assertEquals(roomService.getCounts() , 7);

        Assert.assertNotNull(rooms.getId());
        Assert.assertEquals(rooms.getRoomType(),ROOM_TYPE);
        Assert.assertEquals(rooms.getPricePerNight(),ROOM_PRICE_PER_NIGHT,0);
        Assert.assertEquals(rooms.getDescription(),ROOM_ROOM_DESCRIPTION);


        rooms.setRoomType(ROOMS_TYPE);
        rooms.setPricePerNight(ROOMS_PRICE_PER_NIGHT);
        rooms.setDescription(ROOMS_ROOM_DESCRIPTION);
        roomService.save(rooms);

        Rooms rooms1= roomService.findById(rooms.getId());
        compare(rooms, rooms1);

        Rooms byId= roomService.findById(rooms.getId());
        compare(byId, rooms);

        roomService.delete(rooms1.getId());
        Assert.assertEquals(roomService.getCounts(),7);

    }

}
