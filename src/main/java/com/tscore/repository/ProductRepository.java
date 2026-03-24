package com.tscore.repository;

import com.tscore.model.Product;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class ProductRepository implements PanacheRepository<Product> {

    public List<Product> findByCategory(Long categoryId) {
        return find("category.id", categoryId).list();
    }
}
