package com.hotel;

import com.hotels.domain.Booking;
import com.hotels.domain.Rooms;
import com.hotels.repository.BookingRepository;
import com.hotels.repository.RoomsRepository;
import com.hotels.servlet.jaxWSDL.BookingWebServiceImpl;
import com.hotels.servlet.jaxWSDL.RoomsWebServiceImpl;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class BookingTest {


    public static final String BOOKING_CHECK_IN = "05/20/2020";
    public static final String BOOKING_CHECK_OUT = "05/24/2020";


    public static final String ROOM_TYPE = "Single Bed";
    public static final double ROOM_PRICE_PER_NIGHT = 1500;
    public static final String ROOM_ROOM_DESCRIPTION = "With Balcony and Hot Water";

    @Test
    public void crudTest() throws Exception {


        Rooms rooms = new Rooms(null, ROOM_TYPE, ROOM_PRICE_PER_NIGHT, ROOM_ROOM_DESCRIPTION);
        RoomsRepository roomsRepository = RoomsRepository.getInstance();
        roomsRepository.save(rooms);
        BookingRepository bookingRepository = BookingRepository.getInstance();
        Assert.assertEquals(bookingRepository.getCounts() , 6);
        Booking booking = new Booking(null, rooms,BOOKING_CHECK_IN,BOOKING_CHECK_OUT);
        bookingRepository.save(booking);
        Assert.assertEquals(bookingRepository.getCounts(),7);

        Assert.assertNotNull(booking.getId());
        Assert.assertEquals(booking.getCheckInDate(),BOOKING_CHECK_IN);
        Assert.assertEquals(booking.getCheckOutDate(),BOOKING_CHECK_OUT);

        Booking booking1= bookingRepository.findById(booking.getId());
        Assert.assertEquals(booking1.getNoOfDay(),4);
        Assert.assertEquals(booking1.getTotalAmount(),6000,0);

        bookingRepository.delete(booking1.getId());
        Assert.assertEquals(bookingRepository.getCounts(),6);



    }


    @Test
    public void crudWebServiceTest() throws Exception {


        Rooms rooms = new Rooms(null, ROOM_TYPE, ROOM_PRICE_PER_NIGHT, ROOM_ROOM_DESCRIPTION);
        RoomsWebServiceImpl roomService = RoomsWebServiceImpl.getInstance();
        roomService.save(rooms);
        BookingWebServiceImpl bookingService = BookingWebServiceImpl.getInstance();
        Assert.assertEquals(bookingService.getCounts() , 6);
        Booking booking = new Booking(null, rooms,BOOKING_CHECK_IN,BOOKING_CHECK_OUT);
        bookingService.save(booking);
        Assert.assertEquals(bookingService.getCounts(),7);

        Assert.assertNotNull(booking.getId());
        Assert.assertEquals(booking.getCheckInDate(),BOOKING_CHECK_IN);
        Assert.assertEquals(booking.getCheckOutDate(),BOOKING_CHECK_OUT);

        Booking booking1= bookingService.findById(booking.getId());
        Assert.assertEquals(booking1.getNoOfDay(),4);
        Assert.assertEquals(booking1.getTotalAmount(),6000,0);

        bookingService.delete(booking1.getId());
        Assert.assertEquals(bookingService.getCounts(),6);


    }
}