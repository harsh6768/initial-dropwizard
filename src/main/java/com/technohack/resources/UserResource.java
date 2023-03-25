package com.technohack.resources;

import com.technohack.dao.UserDao;
import com.technohack.entity.User;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private final UserDao userDao;

    public UserResource(UserDao userDao) {
        this.userDao = userDao;
    }

    @GET
    @UnitOfWork
    public List<User> getUsers() {
        return userDao.findAll();
    }

    @GET
    @Path("/{id}")
    @UnitOfWork
    public User getUser(@PathParam("id") long id) {
        return userDao.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
    }

    @POST
    @UnitOfWork
    public Response createUser(User user) {
        userDao.create(user);
        return Response.status(Response.Status.CREATED).entity(user).build();
    }

    @PUT
    @Path("/{id}")
    @UnitOfWork
    public Response updateUser(@PathParam("id") long id, User user) {
        User existingUser = userDao.findById(id).orElseThrow(() -> new NotFoundException("User not found"));

        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());

        userDao.update(existingUser);
        return Response.ok(existingUser).build();
    }

    @DELETE
    @Path("/{id}")
    @UnitOfWork
    public Response deleteUser(@PathParam("id") long id) {
        User existingUser = userDao.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
        userDao.delete(existingUser);
        return Response.noContent().build();
    }

}