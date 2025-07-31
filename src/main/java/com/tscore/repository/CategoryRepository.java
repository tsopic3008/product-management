package com.tscore.repository;

import com.tscore.model.Category;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.util.List;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class CategoryRepository implements PanacheRepository<Category> {
    public List<Category> findAllCategories() {
        return listAll();
    }
}
