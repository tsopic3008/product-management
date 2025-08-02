package com.tscore.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.math.BigDecimal;

@Entity
public class Product {
    @Id
    @GeneratedValue
    public Long id;

    public String name;
    public String description;
    public BigDecimal price;
    public String image_url;

    @ManyToOne
    public Category category;
}

