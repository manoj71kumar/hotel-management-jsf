package com.hotels.servlet.jsf;

import com.hotels.domain.Booking;
import com.hotels.domain.Rooms;
import com.hotels.repository.BookingRepository;
import com.hotels.repository.RoomsRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@ViewScoped
@ManagedBean
public class BookingController extends AbstractController<Booking>{

    private static final Logger LOG= LogManager.getLogger();
    private static final Boolean DEBUG_TEMPORARY= false;

    private BookingRepository bookingRepository ;
    private RoomsRepository roomsRepository ;

    private BigDecimal payPalFee;
    private BigDecimal grandTotal;
    private String checkInDate;
    private String checkOutDate;
    private Long roomId;
    private String roomType;

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType)  {
        this.roomType = roomType;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }
    
	public BigDecimal getPayPalFee() {
		return payPalFee;
	}

	public void setPayPalFee(BigDecimal payPalFee) {
		this.payPalFee = payPalFee;
	}

	public BigDecimal getGrandTotal() {
		return grandTotal;
	}

	public void setGrandTotal(BigDecimal grandTotal) {
		this.grandTotal = grandTotal;
	}

	public BookingController() {
        this.bookingRepository= BookingRepository.getInstance();
        this.roomsRepository= RoomsRepository.getInstance();
    }

    public List<Rooms> getRoom() {
        try {
            return this.roomsRepository.findAll();
        }
        catch (Exception e) {
            LOG.error(e);
            return Collections.emptyList();
        }
    }
    public String save(Booking b) {
        try {

            if (b == null)
                return null;
            this.bookingRepository.save(b);
            return toUrl("reviewBooking.xhtml", true);
        }
        catch (Exception e) {
            LOG.error(e);
            return null;}
    }

    public String save(BookingController bookingController) {
        try {
            int limit = 0;
            List<Booking> bookedList = this.bookingRepository.findAll();
            for(Booking e : bookedList) {
                if(roomType.equals(e.getRooms().getRoomType())) {
                    limit++;
                }
            }

            if(limit == 2){
                LOG.error("Please Choose another room,This Room is not available");
                return null;
            }
            LocalDate checkIn =LocalDate.parse(checkInDate,DateTimeFormatter.ofPattern("MM/dd/yyyy"));
            LocalDate checkOut =LocalDate.parse(checkOutDate,DateTimeFormatter.ofPattern("MM/dd/yyyy"));
            Rooms rooms = this.roomsRepository.getByRoomType(roomType);
            Booking booking = new Booking(rooms, bookingController.checkInDate, bookingController.checkOutDate);
            if(!checkIn.isAfter(checkOut) && !checkIn.isEqual(checkOut))
                this.bookingRepository.save(booking);
            else{
                LOG.error("Check in Date can't be after checkout date");
                return null;}

            return toUrl("reviewBooking.xhtml", true);


        }

        catch (Exception e) {
            LOG.error(e);
            return null;
        }
    }
    public String update(Long id) {
        try {
            Booking booking = this.bookingRepository.findById(id);
            if (booking==null) {
                LOG.warn(String.format("Booking with id %d is not exists", id));
                return null;
            }
            Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
            sessionMap.put("editRecord", booking);
            return toUrl("editbooking.xthml", true);
        }
        catch (Exception e) {
            LOG.error(e);
            return null;
        }
    }

    public  String updateBooking(Booking b) {
        try {
            Rooms r=this.roomsRepository.findById(this.roomId);
            b.setRooms(r);
            this.bookingRepository.update(b);
            return toUrl("admin/booking.xhtml", true);
        }
        catch (Exception e) {
            LOG.error(e);
            return null;
        }
    }

    public String delete(Long id) {
        try {
            this.bookingRepository.delete(id);
            LOG.info(String.format("Booking with id %d has been deleted", id));
            return toUrl("admin/booking.xhtml", true);
        }
        catch (Exception e) {
            LOG.error(e);
            return null;
        }
    }
    public String checkout(Long id) {
        try {
            this.bookingRepository.delete(id);
            LOG.info(String.format("Booking with id %d has been CheckOut", id));
            return toUrl("admin/booking.xhtml", true);
        }
        catch (Exception e) {
            LOG.error(e);
            return null;
        }
    }

    @Override
    protected List<Booking> findAll(int currentPage, int rowsPerPage) throws Exception {
        return this.bookingRepository.findAll(currentPage, rowsPerPage);
    }

    @Override
    protected long getCounts() throws Exception {
        return this.bookingRepository.getCounts();
    }
    
    public void calculateAmount(List<Booking> bList){
    	BigDecimal totalAmount = BigDecimal.ZERO;
    	
    	for(Booking b: bList) {
    		totalAmount = totalAmount.add(BigDecimal.valueOf(b.getTotalAmount()));
    	}
    	
        setGrandTotal(totalAmount);
    }
}
