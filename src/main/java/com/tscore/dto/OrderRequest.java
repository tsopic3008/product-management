package com.tscore.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record OrderRequest(
        @NotBlank(message = "userId must not be blank") String userId,
        @NotNull @Size(min = 1, message = "Order must contain at least one item") @Valid List<Item> items) {

    public record Item(
            @NotNull(message = "productId must not be null") Long productId,
            @Min(value = 1, message = "Quantity must be at least 1") int quantity) {
    }
}
