package com.hotels.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Objects;


/** Represents a Booking.
 * @author Khalid Muhammad Farhan
 * @version 13.0.2
 * @since  2020-05-15
 */
@Entity
@Table(name = Booking.TBL_NAME)

public class Booking extends AbstractEntity<Long> {

    public  static final String TBL_NAME = "Booking";
    public static final String FLD_ROOM = "rooms";
    public static final String FLD_CHECK_IN = "checkInDate";
    public static final String FLD_CHECK_OUT = "checkOutDate";


    /**
     * Represents the Many to One Relationship with Rooms Available
     */

    @ManyToOne(fetch = FetchType.EAGER,cascade =CascadeType.PERSIST)
    @JoinColumn(name = FLD_ROOM)
    private Rooms rooms;

    /**
     * Represents the Check_In Date of Customer
     */
    @Column(name = FLD_CHECK_IN , nullable = false)
    @NotNull(message = "Date can not be Empty")
    private String checkInDate;

    /**
     * Represents the Check_Out Date of Customer
     */
    @Column(name = FLD_CHECK_OUT , nullable = false)
    @NotNull(message = "Date can not be Empty")
    private String checkOutDate;

    /**
     * Represents the No. of Days Customer staying
     */
    @Transient
    @Column(name = "Total_days")
    private long noOfDay;

    /**
     * Represents the Total Amount of Booking
     */
    @Transient
    @Column(name = "Total_Amount")
    private double totalAmount;

    /**
     * Empty Constructor for Java Beans
     */
    public Booking() {
    }

    /**
     * Constructor and initialization
     * @param rooms Rooms available for Booking
     * @param checkInDate Check in Date for Booking
     * @param checkOutDate Check out Date for Booking
     */
    public Booking(Rooms rooms, String checkInDate, String checkOutDate) {
        this.rooms = rooms;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;


    }

    /**
     * Constructor and initialization
     * @param id  Unqiue id of a Room
     * @param rooms Rooms available for Booking
     * @param checkInDate Check in Date for Booking
     * @param checkOutDate Check out Date for Booking
     */
    public Booking(Long id, Rooms rooms, String checkInDate, String checkOutDate) {
        super(id);
        this.rooms = rooms;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.noOfDay= noOfDay;
        this.totalAmount=totalAmount;

    }

    /**
     * Gets the rooms’s Types for Booking.
     * @return A string representing the booking’s room
     *  type.
     */
    public Rooms getRooms() {
        return rooms;
    }

    /** Sets the rooms’s Type.
     * @param rooms A String containing the rooms’s
     *     type.
     */
    public void setRooms(Rooms rooms) {
        this.rooms = rooms;
    }

    /**
     * Gets the booking’s Check in Dte.
     * @return A string representing the booking’s check in
     *  date.
     */
    public String getCheckInDate() {
        return checkInDate;
    }

    /** Sets the booking’s check in Date.
     * @param checkInDate A String containing the rooms’s
     *     type.
     */
    public void setCheckInDate(String checkInDate) {
        if (checkInDate==null)
            return;
        this.checkInDate = checkInDate;
    }

    /**
     * Gets the booking’s Check out Date.
     * @return A string representing the booking’s check out
     *  date.
     */
    public String getCheckOutDate() {
        return checkOutDate;
    }

    /** Sets the booking’s check out Date.
     * @param checkOutDate A String containing the booking’s
     *     check in date.
     */
    public void setCheckOutDate(String checkOutDate) {
        if (checkInDate==null)
            return;
        this.checkOutDate = checkOutDate;
    }

    /**
     * Entity listener which we can use for calculating these properties upon entity loading
     * @throws ParseException Errors
     */
    @PostLoad
    private void postLoad() throws ParseException{

        LocalDate checkIn = LocalDate.parse(checkInDate,DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        LocalDate checkOut = LocalDate.parse(checkOutDate,DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        this.noOfDay = Period.between(checkIn, checkOut).getDays();

        this.totalAmount = this.noOfDay * this.rooms.getPricePerNight();


    }

    /**
     * Gets the booking’s days.
     * @return A string representing the booking’s
     *  days.
     */
    public long getNoOfDay() {
        return noOfDay;
    }

    /**
     * Gets the booking’s days.
     * @return A string representing the booking’s
     *  total payment.
     */
    public double getTotalAmount() {
        return totalAmount;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * @param o o - the reference object with which to compare.
     * @return true if this object is the same as the obj argument; false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Booking)) return false;
        Booking booking = (Booking) o;
        return noOfDay == booking.noOfDay &&
                Double.compare(booking.totalAmount, totalAmount) == 0 &&
                Objects.equals(rooms, booking.rooms) &&
                Objects.equals(checkInDate, booking.checkInDate) &&
                Objects.equals(checkOutDate, booking.checkOutDate);
    }

    /**
     * Returns a hash code value for the object. This method is supported for the benefit of hash tables such as those provided by HashMap.
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(rooms, checkInDate, checkOutDate, noOfDay, totalAmount);
    }

}
