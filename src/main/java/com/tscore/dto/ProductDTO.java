package com.tscore.dto;

import java.math.BigDecimal;

public class ProductDTO {
    public Long id;
    public String name;
    public String description;
    public BigDecimal price;
    public String categoryName;

    public ProductDTO(Long id, String name, String description, BigDecimal price, String categoryName) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.categoryName = categoryName;
    }
}
