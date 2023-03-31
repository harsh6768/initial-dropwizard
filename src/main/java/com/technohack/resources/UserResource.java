package com.technohack.resources;

import com.technohack.dao.UserDao;
import com.technohack.db.entities.User;
import com.technohack.utils.entities.CustomResponse;
import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.annotations.ApiOperation;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private final UserDao userDao;

    public UserResource(UserDao userDao) {
        this.userDao = userDao;
    }

    @GET
    @UnitOfWork
    @ApiOperation(value = "Get all users", notes = "Returns a list of all users")
    public CustomResponse getUsers() {
        try {
            List<User> listOfUsers = userDao.findAll();
            return CustomResponse.buildSuccessResponse("List Of Users", listOfUsers);
        } catch (Exception error) {
            return CustomResponse.buildErrorResponse("Error while fetching Users List");
        }
    }

    @GET
    @Path("/{id}")
    @UnitOfWork
    @ApiOperation(value = "Get user by ID", notes = "Returns a user with the given ID")
    public CustomResponse getUser(@PathParam("id") long id) {
        try {
            Optional<User> user = userDao.findById(id);
            if (user.isEmpty()) {
                return CustomResponse.buildErrorResponse("User not found");
            }
            return CustomResponse.buildSuccessResponse("User Details", user);
        } catch (Exception error) {
            System.out.println("Get User Error::" + error);
            return CustomResponse.buildErrorResponse("Failed to get User Details");
        }
    }

    @POST
    @UnitOfWork
    @ApiOperation(value = "Create user", notes = "Creates a new user")
    public CustomResponse createUser(User user) {
        userDao.create(user);
        return CustomResponse.buildSuccessResponse("User Data Inserted", user);
    }

    @PUT
    @Path("/{id}")
    @UnitOfWork
    @ApiOperation(value = "Update user by ID", notes = "Updates a user with the given ID")
    public CustomResponse updateUser(@PathParam("id") long id, User user) {
        User existingUser = userDao.findById(id).orElseThrow(() -> new NotFoundException("User not found"));

        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());

        userDao.update(existingUser);
        return CustomResponse.buildSuccessResponse("User Updated", existingUser);
    }

    @DELETE
    @Path("/{id}")
    @UnitOfWork
    @ApiOperation(value = "Delete user by ID", notes = "Deletes a user with the given ID")
    public CustomResponse deleteUser(@PathParam("id") long id) {
        try {
            User existingUser = userDao.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
            userDao.delete(existingUser);
            return CustomResponse.buildSuccessResponse("User Deleted", existingUser);
        } catch (Exception error) {
            System.out.println("Delete User Error :::" + error);
            throw error;
        }
    }

}