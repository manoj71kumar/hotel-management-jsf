package com.hotels.servlet.rest;


import com.hotels.domain.Booking;
import com.hotels.domain.Rooms;
import com.hotels.repository.BookingRepository;
import com.hotels.repository.RoomsRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.net.URI;

/** Represents a Booking Rest Api
 * @author Khalid Muhammad Farhan
 * @version 13.0.2
 * @since  2020-05-15
 */

@Path(BookingsController.Base_Path)
public class BookingsController implements Serializable {

    private final static Logger LOG = LogManager.getLogger();
    private final static boolean DEBUG_TEMPORARY = false;


    public static final String Base_Path = "/booking";

    private BookingRepository bookingRepository= BookingRepository.getInstance();
    private RoomsRepository roomsRepository = RoomsRepository.getInstance();


    /**
     * To get a list of all booking
     * @return available booking
     */
    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        if (DEBUG_TEMPORARY)
            LOG.debug("Enter BookingRepository.findAll()");

        try {
            ObjectList<Booking> booking =
                    new ObjectList<>(this.bookingRepository.findAll());
            return Response.ok(booking, MediaType.APPLICATION_JSON).build();


        } catch (Exception e) {

            LOG.error(e);
            return Response.noContent().build();
        }

    }

    /**
     * To get the specific booking by id
     * @return available specific booking
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") Long id) {
        if (DEBUG_TEMPORARY)
            LOG.debug("Enter BookingRepository.get id" + id);
        try {

            Booking b = this.bookingRepository.findById(id);
            return b!= null
                    ? Response.ok(b, MediaType.APPLICATION_JSON).build()
                    : Response.status(Response.Status.NOT_FOUND).build();

        } catch (Exception e) {

            LOG.error(e);
            return Response.noContent().build();
        }

    }

    /**
     * To create a new booking
     * @param b booking instance
     * @return new booking
     */
    @POST
    @Path("/save")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(Booking b) {
        if (DEBUG_TEMPORARY)
            LOG.debug("Enter bookingRepository.add booking" + b);
        try {

            this.bookingRepository.saveOrUpdate(b);
            URI uri = new URI(String.format("%s/%d", Base_Path, b.getId()));
            return Response.created(uri).build();
        } catch (Exception e) {

            LOG.error(e);
            return Response.noContent().build();
        }

    }

    /**
     * To update booking
     * @param id id
     * @param booking booking
     * @return update booking
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, Booking booking ) {

        if (DEBUG_TEMPORARY)
            LOG.debug("Enter BookingRepository.update id" + id);

        try {
            Booking b = this.bookingRepository.findById(id);
            Rooms rooms = this.roomsRepository.findById(id);
            if (b == null)
                return Response.notModified("Not exist in database").build();
            b.setRooms(booking.getRooms());
            b.setCheckInDate(booking.getCheckInDate());
            b.setCheckOutDate(booking.getCheckOutDate());
            this.bookingRepository.saveOrUpdate(b);
            return Response.ok(id).build();



        }
        catch (Exception e) {

            LOG.error(e);
            return Response.notModified("cannot Modified").build();
        }
    }

    /**
     * To delete booking
     * @param id id specific for deleting
     * @return remove the booking from database
     */
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        if (DEBUG_TEMPORARY)
            LOG.debug("Enter BookingRepository.delete id" + id);

        try {
            this.bookingRepository.delete(id);
            return Response.ok().build();

        } catch (Exception e) {

            LOG.error(e);
            return Response.notModified("cannot delete").build();
        }
    }




}
