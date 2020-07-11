package com.hotels.servlet.rest;


import com.hotels.domain.Users;
import com.hotels.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;


/** Represents a Users Rest Api
 * @author Khalid Muhammad Farhan
 * @version 13.0.2
 * @since  2020-05-15
 */

@Path(UserController.Base_Path)
public class UserController {
    private final static Logger LOG = LogManager.getLogger();
    private final static boolean DEBUG_TEMPORARY = false;

    public static final String Base_Path = "/users";

    private UserRepository userRepository= UserRepository.getInstance();


    /**
     * To get a list of all users
     * @return available users
     */
    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        if (DEBUG_TEMPORARY)
            LOG.debug("Enter UserRepository.findAll()");

        try {
            ObjectList<Users> countries =
                    new ObjectList<>(this.userRepository.findAll());
            return Response.ok(countries, MediaType.APPLICATION_JSON).build();


        } catch (Exception e) {

            LOG.error(e);
            return Response.noContent().build();
        }

    }

    /**
     * To get the specific user by id
     * @return available specific user
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") Long id) {
        if (DEBUG_TEMPORARY)
            LOG.debug("Enter UserRepository.get id" + id);
        try {

            Users u = this.userRepository.findById(id);
            return u!= null
                    ? Response.ok(u, MediaType.APPLICATION_JSON).build()
                    : Response.status(Response.Status.NOT_FOUND).build();

        } catch (Exception e) {

            LOG.error(e);
            return Response.noContent().build();
        }

    }

    /**
     * To create a new users
     * @param u user instance
     * @return new user
     */
    @POST
    @Path("/save")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add(Users u) {
        if (DEBUG_TEMPORARY)
            LOG.debug("Enter UserRepository.add room" + u);
        try {

            this.userRepository.saveOrUpdate(u);
            URI uri = new URI(String.format("%s/%d", Base_Path, u.getId()));
            return Response.created(uri).build();
        } catch (Exception e) {

            LOG.error(e);
            return Response.noContent().build();
        }

    }

    /**
     * To update user
     * @param id id
     * @param users users
     * @return update user
     */

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, Users users) {

        if (DEBUG_TEMPORARY)
            LOG.debug("Enter UserRepository.update id" + id);

        try {
            Users u = this.userRepository.findById(id);
            if (u == null)
                return Response.notModified("Not exist in database").build();
            u.setFirstName(users.getFirstName());
            u.setLastName(users.getLastName());
            u.setEmailAddress(users.getEmailAddress());
            u.setAddress(users.getAddress());
            u.setPhoneNumber(users.getPhoneNumber());
            u.setPassword(users.getPassword());
            this.userRepository.saveOrUpdate(u);
            return Response.ok(id).build();
        }
        catch (Exception e) {

            LOG.error(e);
            return Response.notModified("cannot Modified").build();
        }
    }

    /**
     * To delete user
     * @param id id specific for deleting
     * @return remove the user from database
     */
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        if (DEBUG_TEMPORARY)
            LOG.debug("Enter UserRepository.delete id" + id);

        try {
            this.userRepository.delete(id);
            return Response.ok().build();

        } catch (Exception e) {

            LOG.error(e);
            return Response.notModified("cannot delete").build();
        }
    }

}
