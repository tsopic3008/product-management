package com.tscore.endpoint;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/secure")
public class SecureResource {

    @GET
    @RolesAllowed("user")
    public Response securedEndpoint() {
        return Response.ok("Hello, secured world!").build();
    }
}

