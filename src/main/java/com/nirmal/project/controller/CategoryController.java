package com.nirmal.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.nirmal.project.dto.CategoryDto;
import com.nirmal.project.exception.GlobalExceptionHandler;
import com.nirmal.project.service.CategoryService;
import com.nirmal.project.utils.validation.CategoryDtoValidator;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Path("/enotes-api-service/api/v1/category")
public class CategoryController {
    private static final Gson gson = new Gson();
    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);
    private final CategoryService categoryService;


    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveCategory(@Valid String categoryJson) {

        CategoryDto categoryDto = gson.fromJson(categoryJson, CategoryDto.class);
        CategoryDtoValidator.validate(categoryDto);
        Boolean res = categoryService.saveCategory(categoryDto);
        return Response.status(Response.Status.CREATED)
                .entity("{\"message\": \"Category created successfully\"}")
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCategories(@QueryParam("active") Boolean isActive) {

        List<CategoryDto> allCategories;
        if (isActive == null) {
            allCategories = categoryService.getAllCategories();
        } else {
            allCategories = categoryService.getActiveCategories();
        }

        return Response.ok(gson.toJson(allCategories), MediaType.APPLICATION_JSON).build();

    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCategoryById(@PathParam("id") Integer id) {
        Optional<CategoryDto> categoryDto = categoryService.getCategoryById(id);
        return Response.ok()
                .type(MediaType.APPLICATION_JSON)
                .entity(gson.toJson(categoryDto.get()))
                .build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCategoryById(@PathParam("id") Integer id) {

        categoryService.deleteCategoryById(id);
        return Response.ok()
                .type(MediaType.APPLICATION_JSON)
                .entity(String.format("{\"message\": \"Category with ID -> %d deleted successfully\"}", id))
                .build();

    }

    @PATCH
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCategoryById(@PathParam("id") Integer id, String categoryDtoJson) {
        CategoryDto categoryDto = gson.fromJson(categoryDtoJson, CategoryDto.class);

        categoryService.updateCategoryById(id, categoryDto);
        return Response.ok()
                .type(MediaType.APPLICATION_JSON)
                .entity(String.format("{\"message\": \"Category with ID -> %d updated successfully\"}", id))
                .build();

    }

}
