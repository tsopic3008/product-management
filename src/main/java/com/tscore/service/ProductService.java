package com.tscore.service;

import com.tscore.dto.ProductDTO;
import com.tscore.exception.NotFoundException;
import com.tscore.model.Product;
import com.tscore.repository.ProductRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.List;

@ApplicationScoped
public class ProductService {

    private static final Logger log = Logger.getLogger(ProductService.class);

    @Inject
    ProductRepository productRepository;

    public List<ProductDTO> findAll() {
        return productRepository.listAll().stream()
                .map(this::toDTO)
                .toList();
    }

    public List<ProductDTO> findByCategory(Long categoryId) {
        return productRepository.findByCategory(categoryId).stream()
                .map(this::toDTO)
                .toList();
    }

    public ProductDTO findById(Long id) {
        log.debugf("Fetching product with id: %d", id);
        return productRepository.findByIdOptional(id)
                .map(this::toDTO)
                .orElseThrow(() -> new NotFoundException("Product with ID " + id + " not found"));
    }

    private ProductDTO toDTO(Product p) {
        return new ProductDTO(
                p.id,
                p.name,
                p.description,
                p.price,
                p.category != null ? p.category.name : null,
                p.imageUrl);
    }
}
