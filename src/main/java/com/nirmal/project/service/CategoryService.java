package com.nirmal.project.service;

import com.nirmal.project.dto.CategoryDto;
import com.nirmal.project.model.Category;
import java.util.List;

public interface CategoryService {
    public List<CategoryDto> getAllCategories();
    public Boolean saveCategory(CategoryDto categoryDto);
    public List<CategoryDto> getActiveCategories();
}
