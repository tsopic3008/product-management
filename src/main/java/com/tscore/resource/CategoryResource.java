package com.tscore.resource;

import com.tscore.model.Category;
import com.tscore.service.CategoryService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/categories")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoryResource {

    @Inject
    CategoryService categoryService;

    @GET
    public List<Category> getAll() {
        return categoryService.findAll();
    }

    @GET
    @Path("/{id}")
    public Category getById(@PathParam("id") Long id) {
        return categoryService.findById(id);
    }

    @POST
    public Response create(@Valid Category category) {
        return Response.status(Response.Status.CREATED)
                .entity(categoryService.create(category))
                .build();
    }

    @PUT
    @Path("/{id}")
    public Category update(@PathParam("id") Long id, @Valid Category category) {
        return categoryService.update(id, category);
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        categoryService.delete(id);
        return Response.noContent().build();
    }
}
