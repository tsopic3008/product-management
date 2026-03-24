package com.tscore.service;

import com.tscore.dto.OrderItemDTO;
import com.tscore.dto.OrderRequest;
import com.tscore.dto.OrderResponse;
import com.tscore.exception.NotFoundException;
import com.tscore.model.Order;
import com.tscore.model.OrderItem;
import com.tscore.repository.OrderRepository;
import com.tscore.repository.ProductRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

import java.util.List;

@ApplicationScoped
public class OrderService {

    private static final Logger log = Logger.getLogger(OrderService.class);

    @Inject
    OrderRepository orderRepository;

    @Inject
    ProductRepository productRepository;

    @Transactional
    public OrderResponse placeOrder(OrderRequest request) {
        log.debugf("Placing order for user: %s", request.userId());

        var order = new Order();
        order.userId = request.userId();

        for (var item : request.items()) {
            var product = productRepository.findByIdOptional(item.productId())
                    .orElseThrow(() -> new NotFoundException("Product with ID " + item.productId() + " not found"));

            var orderItem = new OrderItem();
            orderItem.order = order;
            orderItem.product = product;
            orderItem.quantity = item.quantity();
            order.items.add(orderItem);
        }

        orderRepository.persist(order);
        return toResponse(order);
    }

    public List<OrderResponse> findByUserId(String userId) {
        return orderRepository.find("userId", userId).list().stream()
                .map(this::toResponse)
                .toList();
    }

    public OrderResponse findById(Long id) {
        return orderRepository.findByIdOptional(id)
                .map(this::toResponse)
                .orElseThrow(() -> new NotFoundException("Order with ID " + id + " not found"));
    }

    public List<OrderResponse> findAll() {
        return orderRepository.listAll().stream()
                .map(this::toResponse)
                .toList();
    }

    private OrderResponse toResponse(Order order) {
        var items = order.items.stream()
                .map(i -> new OrderItemDTO(i.product.id, i.product.name, i.quantity))
                .toList();
        return new OrderResponse(order.id, order.userId, order.createdAt, items);
    }
}
