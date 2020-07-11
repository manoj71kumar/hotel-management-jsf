package com.hotels.servlet.jaxws;

import javax.jws.WebService;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@WebService
public interface HotelBookingService {
    public boolean createBooking(Date checkInDate, Date checkOutDate, int noOfDay, double totalAmount,String roomType);
    public boolean createRoom(String roomType, double pricePerNight, String description);
    public boolean createUser(String firstName, String lastName, String phoneNumber, String address, String emailAddress, String password) throws IOException;


    }
