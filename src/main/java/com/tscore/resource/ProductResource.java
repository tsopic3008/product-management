package com.tscore.resource;

import com.tscore.dto.ProductDTO;
import com.tscore.service.ProductService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResource {

    @Inject
    ProductService productService;

    @GET
    public List<ProductDTO> getAll(@QueryParam("categoryId") Long categoryId) {
        if (categoryId != null) {
            return productService.findByCategory(categoryId);
        }
        return productService.findAll();
    }

    @GET
    @Path("/{id}")
    public ProductDTO getById(@PathParam("id") Long id) {
        return productService.findById(id);
    }
}
