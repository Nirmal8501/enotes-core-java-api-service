package com.nirmal.project.service.impl;

import com.nirmal.project.dao.CategoryDao;
import com.nirmal.project.model.Category;
import com.nirmal.project.service.CategoryService;

import java.util.List;
import java.util.Optional;

public class CategoryServiceImpl implements CategoryService {
    private final CategoryDao categoryDao;
    public CategoryServiceImpl(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }
    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = categoryDao.readAllCategories();
        return categories;
    }

    @Override
    public Boolean saveCategory(Category category) {
        category.setIsDeleted(false);
        category.setCreatedBy(1);
        Optional<Category> savedCategory = categoryDao.createCategory(category);
        return savedCategory.isPresent();
    }
}
