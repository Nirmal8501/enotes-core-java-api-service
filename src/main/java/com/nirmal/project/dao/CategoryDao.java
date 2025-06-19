package com.nirmal.project.dao;

import com.nirmal.project.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryDao {
    Optional<Category> createCategory(Category category);
    List<Category> readAllCategories();
}
