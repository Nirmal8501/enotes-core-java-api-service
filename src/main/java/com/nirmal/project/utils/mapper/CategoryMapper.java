package com.nirmal.project.utils.mapper;

import com.nirmal.project.dto.CategoryDto;
import com.nirmal.project.dto.CategoryResponse;
import com.nirmal.project.model.Category;

public class CategoryMapper {

    public static Category toEntity(CategoryDto dto) {
        if(dto == null) return null;
        Category category = new Category();
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        category.setIsActive(dto.getActive());
        return category;
    }

    public static CategoryDto toDto(Category category) {
        if(category == null) return null;
        CategoryDto dto = new CategoryDto();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        dto.setActive(category.getIsActive());
        dto.setCreatedBy(category.getCreatedBy());
        return dto;
    }

    public static CategoryResponse toResponse(Category category){
        if(category == null) return null;
        CategoryResponse response = new CategoryResponse();
        response.setId(category.getId());
        response.setName(category.getName());
        response.setDescription(category.getDescription());
        response.setActive(category.getIsActive());
        response.setCreatedBy(category.getCreatedBy());
        return response;
    }
}