package com.tscore.dto;

import java.math.BigDecimal;

public record ProductDTO(
        Long id,
        String name,
        String description,
        BigDecimal price,
        String categoryName,
        String imageUrl) {
}
