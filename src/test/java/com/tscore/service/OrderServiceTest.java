package com.tscore.service;

import com.tscore.dto.OrderRequest;
import com.tscore.exception.NotFoundException;
import com.tscore.model.Category;
import com.tscore.model.Order;
import com.tscore.model.OrderItem;
import com.tscore.model.Product;
import com.tscore.repository.OrderRepository;
import com.tscore.repository.ProductRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    OrderRepository orderRepository;

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    OrderService orderService;

    private Product laptop;
    private Order existingOrder;

    @BeforeEach
    void setUp() {
        var electronics = new Category();
        electronics.id = 1L;
        electronics.name = "Electronics";

        laptop = new Product();
        laptop.id = 1L;
        laptop.name = "Laptop Pro";
        laptop.price = new BigDecimal("1499.99");
        laptop.category = electronics;

        var item = new OrderItem();
        item.id = 1L;
        item.product = laptop;
        item.quantity = 2;

        existingOrder = new Order();
        existingOrder.id = 10L;
        existingOrder.userId = "user-42";
        existingOrder.createdAt = LocalDateTime.now();
        existingOrder.items = List.of(item);
        item.order = existingOrder;
    }

    @Test
    void placeOrder_validRequest_createsAndReturnsOrderResponse() {
        var request = new OrderRequest("user-42", List.of(new OrderRequest.Item(1L, 2)));
        when(productRepository.findByIdOptional(1L)).thenReturn(Optional.of(laptop));
        doNothing().when(orderRepository).persist(any(Order.class));

        var response = orderService.placeOrder(request);

        verify(orderRepository).persist(any(Order.class));
        assertThat(response.userId()).isEqualTo("user-42");
        assertThat(response.items()).hasSize(1);
        assertThat(response.items().get(0).productId()).isEqualTo(1L);
        assertThat(response.items().get(0).quantity()).isEqualTo(2);
    }

    @Test
    void placeOrder_productNotFound_throwsNotFoundException() {
        var request = new OrderRequest("user-42", List.of(new OrderRequest.Item(99L, 1)));
        when(productRepository.findByIdOptional(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderService.placeOrder(request))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("99");

        verify(orderRepository, never()).persist(any(Order.class));
    }

    @Test
    void findById_existingId_returnsOrderResponse() {
        when(orderRepository.findByIdOptional(10L)).thenReturn(Optional.of(existingOrder));

        var response = orderService.findById(10L);

        assertThat(response.id()).isEqualTo(10L);
        assertThat(response.userId()).isEqualTo("user-42");
        assertThat(response.items()).hasSize(1);
        assertThat(response.items().get(0).productName()).isEqualTo("Laptop Pro");
    }

    @Test
    void findById_nonExistingId_throwsNotFoundException() {
        when(orderRepository.findByIdOptional(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderService.findById(99L))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    void findAll_returnsAllOrderResponses() {
        when(orderRepository.listAll()).thenReturn(List.of(existingOrder));

        var result = orderService.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).userId()).isEqualTo("user-42");
    }

    @Test
    @SuppressWarnings("unchecked")
    void findByUserId_delegatesToRepository() {
        PanacheQuery<Order> mockQuery = mock(PanacheQuery.class);
        when(orderRepository.find("userId", "user-42")).thenReturn(mockQuery);
        when(mockQuery.list()).thenReturn(List.of(existingOrder));

        var result = orderService.findByUserId("user-42");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).userId()).isEqualTo("user-42");
    }
}
