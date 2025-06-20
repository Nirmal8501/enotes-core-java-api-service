package com.nirmal.project.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nirmal.project.dto.CategoryDto;
import com.nirmal.project.model.Category;
import com.nirmal.project.service.CategoryService;
import com.nirmal.project.service.impl.CategoryServiceImpl;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

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
    public Response saveCategory(String categoryJson){
        CategoryDto categoryDto = gson.fromJson(categoryJson, CategoryDto.class);
        Boolean res =  categoryService.saveCategory(categoryDto);
        if (res) {
            return Response.status(Response.Status.CREATED)
                    .entity("{\"message\": \"Category created successfully\"}")
                    .build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Failed to create category\"}")
                    .build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCategories(@QueryParam("active") Boolean isActive){
        List<CategoryDto> allCategories;
        if(isActive == null){
            allCategories = categoryService.getAllCategories();
        }else{
            allCategories = categoryService.getActiveCategories();
        }

        if(!allCategories.isEmpty())
            return Response.ok(gson.toJson(allCategories), MediaType.APPLICATION_JSON).build();
        return Response.noContent().build();
    }

}
