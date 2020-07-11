package com.hotels.servlet.rest;


import com.hotels.domain.Rooms;
import com.hotels.repository.RoomsRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;


/** Represents a Rooms Rest Api
 * @author Khalid Muhammad Farhan
 * @version 13.0.2
 * @since  2020-05-15
 */

@Path(RoomsController.Base_Path)
public class RoomsController {

    private final static Logger LOG = LogManager.getLogger();
    private final static boolean DEBUG_TEMPORARY = false;

    public static final String Base_Path = "/rooms";

    private RoomsRepository roomsRepository= RoomsRepository.getInstance();


    /**
     * To get a list of all rooms
     * @return available rooms
     */
    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        if (DEBUG_TEMPORARY)
            LOG.debug("Enter RoomRepository.findAll()");

        try {
            ObjectList<Rooms> countries =
                    new ObjectList<>(this.roomsRepository.findAll());
            return Response.ok(countries, MediaType.APPLICATION_JSON).build();


        } catch (Exception e) {

            LOG.error(e);
            return Response.noContent().build();
        }

    }

    /**
     * To get the specific room by id
     * @return available specific room
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") Long id) {
        if (DEBUG_TEMPORARY)
            LOG.debug("Enter roomsRepository.get id" + id);
        try {

            Rooms b = this.roomsRepository.findById(id);
            return b!= null
                    ? Response.ok(b, MediaType.APPLICATION_JSON).build()
                    : Response.status(Response.Status.NOT_FOUND).build();

        } catch (Exception e) {

            LOG.error(e);
            return Response.noContent().build();
        }

    }

    /**
     * To create a new rooms
     * @param r room instance
     * @return new booking
     */
    @POST
    @Path("/save")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add(Rooms r) {
        if (DEBUG_TEMPORARY)
            LOG.debug("Enter roomsRepository.add room" + r);
        try {

            this.roomsRepository.saveOrUpdate(r);
            URI uri = new URI(String.format("%s/%d", Base_Path, r.getId()));
            return Response.created(uri).build();
        } catch (Exception e) {

            LOG.error(e);
            return Response.noContent().build();
        }

    }

    /**
     * To update booking
     * @param id id
     * @param rooms room
     * @return update room
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, Rooms rooms) {

        if (DEBUG_TEMPORARY)
            LOG.debug("Enter roomsRepository.update id" + id);

        try {
            Rooms r = this.roomsRepository.findById(id);
            if (r == null)
                return Response.notModified("Not exist in database").build();
            r.setRoomType(rooms.getRoomType());
            r.setPricePerNight(rooms.getPricePerNight());
            r.setDescription(rooms.getDescription());
            this.roomsRepository.saveOrUpdate(r);
            return Response.ok(id).build();



        }
        catch (Exception e) {

            LOG.error(e);
            return Response.notModified("cannot Modified").build();
        }
    }

    /**
     * To delete room
     * @param id id specific for deleting
     * @return remove the room from database
     */
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        if (DEBUG_TEMPORARY)
            LOG.debug("Enter roomsRepository.delete id" + id);

        try {
            this.roomsRepository.delete(id);
            return Response.ok().build();

        } catch (Exception e) {

            LOG.error(e);
            return Response.notModified("cannot delete").build();
        }
    }

}
