package com.tscore.resource;

import com.tscore.model.Category;
import com.tscore.repository.CategoryRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/categories")
@Produces(MediaType.APPLICATION_JSON)
public class CategoryResource {

    @Inject
    CategoryRepository categoryRepository;

    @GET
    public List<Category> getAll() {
        return categoryRepository.findAllCategories();
    }
}
