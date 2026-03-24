package com.tscore.dto;

import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
        Long id,
        String userId,
        LocalDateTime createdAt,
        List<OrderItemDTO> items) {
}
