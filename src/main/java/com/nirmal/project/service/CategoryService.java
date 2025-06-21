package com.nirmal.project.service;

import com.nirmal.project.dto.CategoryDto;
import com.nirmal.project.model.Category;
import java.util.List;
import java.util.Optional;

public interface CategoryService {
    public List<CategoryDto> getAllCategories();
    public Boolean saveCategory(CategoryDto categoryDto);
    public List<CategoryDto> getActiveCategories();
    public Optional<CategoryDto> getCategoryById(Integer id);
    public Boolean deleteCategoryById(Integer id);
    public Boolean updateCategoryById(Integer id, CategoryDto categoryDto);
}
