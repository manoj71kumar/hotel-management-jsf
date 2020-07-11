package com.hotels.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

/** Represents a Rooms.
 * @author Khalid Muhammad Farhan
 * @version 13.0.2
 * @since  2020-05-15
 */
@Entity
@Table(name = Rooms.TBL_NAME)
public class Rooms extends AbstractEntity<Long> {

    public static final String TBL_NAME= "Rooms";
    public static final String FLD_ROOM_TYPE="roomType";
    public static final String FLD_ROOM_DESCRIPTION="description";

    /**
     * Represents the Types of Rooms Available
     */
    @Column(name = FLD_ROOM_TYPE , nullable = false)
    @NotNull(message = "Room Type can not be Empty")
    @Size(min = 2,max = 20, message = "First Name must be between the given length")
    private String roomType;

    /**
     * Represents the Rooms Price Per Night
     */
    @NotNull(message = "Price can not be Empty")
    private double pricePerNight;

    /**
     * Represents the Rooms Facilities Available
     */
    @Lob
    @Column(name = FLD_ROOM_DESCRIPTION , nullable = false)
    @NotNull(message = "Description can not be Empty")
    private String description;

    /**
     * Represents the All Booking Available related to the Rooms
     */
    @JsonIgnore
    @Transient
    @OneToMany(mappedBy = Booking.FLD_ROOM, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Booking> bookings;

    /**
     * Empty Constructor for Java Beans
     */
    public Rooms() {}

    /**
     * Constructor and initialization
     * @param roomType  Types of Rooms
     * @param pricePerNight Rooms Price related the Rooms
     * @param description Facilities Available for Rooms
     */

    public Rooms( String roomType, double pricePerNight, String description) {

        this.roomType = roomType;
        this.pricePerNight = pricePerNight;
        this.description = description;
    }

    /**
     * Constructor and initialization
     * @param id  Unqiue id of a Room
     * @param roomType  Types of Rooms
     * @param pricePerNight Rooms Price related the Rooms
     * @param description Facilities Available for Rooms
     */
    public Rooms(Long id, String roomType, double pricePerNight, String description) {
        super(id);
        this.roomType = roomType;
        this.pricePerNight = pricePerNight;
        this.description = description;
    }


    /**
     * Gets the rooms’s Types.
     * @return A string representing the room’s Types
     *  name.
     */
    public String getRoomType() {
        return roomType;
    }

    /** Sets the rooms’s Type.
     * @param roomType A String containing the rooms’s
     *     type.
     */
    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    /**
     * Gets the rooms’s Price Per Night.
     * @return A string representing the room’s first
     *  name.
     */
    public double getPricePerNight() {
        return pricePerNight;
    }

    /** Sets the rooms’s Price Per Night.
     * @param pricePerNight A String containing the rooms’s
     *     Price.
     */
    public void setPricePerNight(double pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    /**
     * Gets the room’s faciltiy available.
     * @return A string representing the room’s description.
     */
    public String getDescription() {
        return description;
    }

    /** Sets the rooms’s Price Description.
     * @param description A String containing the rooms’s
     *     description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the list of rooms's Booking related .
     * @return A string representing the room’s
     *  booking.
     */
    public List<Booking> getBookings() {
        return bookings;
    }

    /** Sets the rooms’s Booking related.
     * @param bookings A String containing the rooms’s
     *     booking.
     */
    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * @param o o - the reference object with which to compare.
     * @return true if this object is the same as the obj argument; false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rooms)) return false;
        Rooms rooms = (Rooms) o;
        return Objects.equals(roomType, rooms.roomType) &&
                Objects.equals(pricePerNight, rooms.pricePerNight) &&
                Objects.equals(description, rooms.description) &&
                Objects.equals(bookings, rooms.bookings);
    }

    /**
     * Returns a hash code value for the object. This method is supported for the benefit of hash tables such as those provided by HashMap.
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(roomType, pricePerNight, description, bookings);
    }

}

