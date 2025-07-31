package com.tscore.repository;

import com.tscore.model.Product;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class ProductRepository implements PanacheRepository<Product> {
    public List<Product> findAllProducts() {
        return listAll();
    }
}
