package com.hotels;

import com.hotels.domain.Booking;
import com.hotels.domain.Rooms;
import com.hotels.domain.Users;
import com.hotels.repository.BookingRepository;
import com.hotels.repository.RoomsRepository;
import com.hotels.repository.UserRepository;

public class Main {
    public static void main(String[] args) throws Exception {
        Users users = new Users(null,"farhan", "khalid", "0620232737", "Ferencesek" , "fkhalid1991@gmail.com" , "12345"  );
        Rooms rooms = new Rooms(null, "Single Bed", 1500 ,"With big Balconay");
        UserRepository userRepository= UserRepository.getInstance();
        userRepository.save(users);
        RoomsRepository roomsRepository= RoomsRepository.getInstance();
        roomsRepository.save(rooms);

        Booking booking = new Booking(null, rooms,"05/29/2020","05/24/2020");
        BookingRepository bookingRepository = BookingRepository.getInstance();
        bookingRepository.save(booking);
        bookingRepository.findById(booking.getId());
    }
}
