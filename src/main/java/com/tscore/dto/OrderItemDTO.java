package com.tscore.dto;

public record OrderItemDTO(
        Long productId,
        String productName,
        int quantity) {
}
