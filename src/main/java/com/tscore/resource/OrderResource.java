package com.tscore.resource;

import com.tscore.dto.OrderRequest;
import com.tscore.model.Order;
import com.tscore.model.OrderItem;
import com.tscore.model.Product;
import com.tscore.repository.OrderRepository;
import com.tscore.repository.ProductRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;

@Path("/orders")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class OrderResource {

    @Inject
    OrderRepository orderRepository;

    @Inject
    ProductRepository productRepository;

    @POST
    @Transactional
    public Response placeOrder(OrderRequest request) {
        if (request.items == null || request.items.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Order must contain at least one item.")
                    .build();
        }

        Order order = new Order();
        order.userId = request.userId;

        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderRequest.Item item : request.items) {
            Product product = productRepository.findById(item.productId);
            if (product == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Product with ID " + item.productId + " not found.")
                        .build();
            }

            OrderItem orderItem = new OrderItem();
            orderItem.order = order;
            orderItem.product = product;
            orderItem.quantity = item.quantity;

            orderItems.add(orderItem);
        }

        order.items = orderItems;

        orderRepository.persist(order);

        return Response.status(Response.Status.CREATED).entity(order).build();
    }

    @GET
    public List<Order> getOrdersByUser(@QueryParam("userId") String userId) {
        return orderRepository.find("userId", userId).list();
    }

    @GET
    @Path("/{id}")
    public Order getOrderById(@PathParam("id") Long id) {
        return orderRepository.findById(id);
    }

    @GET
    @Path("/all")
    public List<Order> getAllOrders() {
        return orderRepository.listAll();
    }

}
