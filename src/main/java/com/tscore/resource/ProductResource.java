package com.tscore.resource;

import com.tscore.dto.ProductDTO;
import com.tscore.model.Product;
import com.tscore.repository.ProductRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
public class ProductResource {

    @Inject
    ProductRepository productRepository;

    @GET
    public List<ProductDTO> getAll() {
        return productRepository.findAllProducts().stream()
                .map(p -> new ProductDTO(
                        p.id,
                        p.name,
                        p.description,
                        p.price,
                        p.category.name,
                        p.image_url
                ))
                .collect(Collectors.toList());
    }
}
