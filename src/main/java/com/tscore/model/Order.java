package com.tscore.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "user_id")
    public String userId;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    public List<OrderItem> items;
}

