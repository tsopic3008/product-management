package com.tscore.endpoint;

import com.tscore.UserDTO;
import com.tscore.model.User;
import com.tscore.service.UserService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
public class UserEndpoint {

    @Inject
    UserService userService;

    @POST
    public Response registerUser(UserDTO user) {
        userService.registerUser(user);
        return Response.ok().build();

    }
}
