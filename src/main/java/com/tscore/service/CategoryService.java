package com.tscore.service;

import com.tscore.exception.NotFoundException;
import com.tscore.model.Category;
import com.tscore.repository.CategoryRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

import java.util.List;

@ApplicationScoped
public class CategoryService {

    private static final Logger log = Logger.getLogger(CategoryService.class);

    @Inject
    CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return categoryRepository.listAll();
    }

    public Category findById(Long id) {
        return categoryRepository.findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Category with ID " + id + " not found"));
    }

    @Transactional
    public Category create(Category category) {
        log.debugf("Creating category: %s", category.name);
        categoryRepository.persist(category);
        return category;
    }

    @Transactional
    public Category update(Long id, Category update) {
        var existing = findById(id);
        existing.name = update.name;
        return existing;
    }

    @Transactional
    public void delete(Long id) {
        if (!categoryRepository.deleteById(id)) {
            throw new NotFoundException("Category with ID " + id + " not found");
        }
    }
}
