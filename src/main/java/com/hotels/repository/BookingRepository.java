package com.hotels.repository;

import com.hotels.domain.Booking;

public class BookingRepository extends AbstractRepository<Long, Booking> {

    @Override
    protected Class<Booking> getClazz() {
        return Booking.class;
    }
    public BookingRepository() {
    }
    private static BookingRepository instance;


    public static synchronized BookingRepository getInstance(){

        if (instance==null)
            instance= new BookingRepository();
        return instance;

    }

}
