package com.tscore.resource;

import com.tscore.dto.OrderRequest;
import com.tscore.dto.OrderResponse;
import com.tscore.service.OrderService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {

    @Inject
    OrderService orderService;

    @POST
    public Response placeOrder(@Valid OrderRequest request) {
        return Response.status(Response.Status.CREATED)
                .entity(orderService.placeOrder(request))
                .build();
    }

    @GET
    public List<OrderResponse> getByUserId(@QueryParam("userId") @NotBlank String userId) {
        return orderService.findByUserId(userId);
    }

    @GET
    @Path("/all")
    public List<OrderResponse> getAll() {
        return orderService.findAll();
    }

    @GET
    @Path("/{id}")
    public OrderResponse getById(@PathParam("id") Long id) {
        return orderService.findById(id);
    }
}
