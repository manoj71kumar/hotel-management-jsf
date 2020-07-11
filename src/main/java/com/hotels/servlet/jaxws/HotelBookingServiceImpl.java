package com.hotels.servlet.jaxws;

import com.hotels.db.HibernateUtil;
import com.hotels.domain.Booking;
import com.hotels.domain.Rooms;
import com.hotels.domain.Users;
import com.hotels.repository.BookingRepository;
import com.hotels.repository.RoomsRepository;
import com.hotels.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.jws.WebService;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebService(endpointInterface = "com.hotels.servlet.jaxws.HotelBookingService",
portName ="hotelBookingServicePort" ,
serviceName = "HotelBookingService")
public class HotelBookingServiceImpl implements HotelBookingService {
    private final static Logger LOG = LogManager.getLogger();
    private final static boolean DEBUG_TEMPORARY = false;
    @Override
    public boolean createBooking(Date checkInDate, Date checkOutDate, int noOfDay, double totalAmount, String roomType) {
    try{
        BookingRepository bookingRepository = BookingRepository.getInstance();
        Rooms rooms = RoomsRepository.getInstance().getByRoomType(roomType);
        Booking booking = new Booking(null, rooms,dateToString(checkInDate), dateToString(checkOutDate));
        bookingRepository.save(booking);
    }catch ( Exception ex){
        LOG.error(ex.getMessage());
        return false;
    }
    return  true;
    }

    @Override
    public boolean createRoom(String roomType, double pricePerNight, String description)
    {
        try {
            RoomsRepository roomsRepository = RoomsRepository.getInstance();
            Rooms rm = new Rooms(null, roomType, pricePerNight, description);
            roomsRepository.save(rm);
        }catch (IOException ex){
            LOG.error(ex.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public boolean createUser(String firstName, String lastName, String phoneNumber, String address, String emailAddress, String password) {
        HibernateUtil util=new HibernateUtil();
        try {
            UserRepository ur = UserRepository.getInstance();
            Users u = new Users(null, firstName, lastName, phoneNumber, address, emailAddress, password);
            ur.save(u);
        }catch (IOException ex){
            LOG.error(ex.getMessage());
            return false;
        }

        return true;
    }
    public String dateToString(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String  strDate = formatter.format(date);
        return strDate;
    }
}
