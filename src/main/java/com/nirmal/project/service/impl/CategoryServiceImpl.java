package com.nirmal.project.service.impl;

import com.nirmal.project.dao.CategoryDao;
import com.nirmal.project.dto.CategoryDto;
import com.nirmal.project.exception.ResourceNotFoundException;
import com.nirmal.project.model.Category;
import com.nirmal.project.service.CategoryService;
import com.nirmal.project.utils.mapper.CategoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class CategoryServiceImpl implements CategoryService {
    private static Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    private final CategoryDao categoryDao;

    public CategoryServiceImpl(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryDao.readAllCategories();
        return categories.stream().map(CategoryMapper::toDto).toList();
//        return categoryDtoList;
    }

    @Override
    public Boolean saveCategory(CategoryDto categoryDto) {
        Category category = CategoryMapper.toEntity(categoryDto);
        category.setIsDeleted(false);
        category.setCreatedBy(1);
//        category.setCreatedOn(new Date());
        Optional<Category> savedCategory = categoryDao.createCategory(category);
        return savedCategory.isPresent();

    }

    @Override
    public List<CategoryDto> getActiveCategories() {
        List<Category> categories = categoryDao.readActiveCategories();
        return categories.stream().map(CategoryMapper::toDto).toList();
    }

    @Override
    public Optional<CategoryDto> getCategoryById(Integer id) {

        Optional<Category> category = categoryDao.readCategoryById(id);
        if (category.isPresent()) {
            return Optional.of(CategoryMapper.toDto(category.get()));
        } else throw new ResourceNotFoundException(String.format("User with category -> %s not found", id));
    }

    @Override
    public Boolean deleteCategoryById(Integer id) {
        Boolean deleted =  categoryDao.deleteCategoryById(id);
        if (!deleted) {
            throw new ResourceNotFoundException("Category with ID " + id + " not found or already deleted");
        }
        return true;
    }

    @Override
    public Boolean updateCategoryById(Integer id, CategoryDto categoryDto) {
        Category category = CategoryMapper.toEntity(categoryDto);
        category.setUpdatedBy(id);
        Boolean updated =  categoryDao.updateCategoryById(id, category);
        if(!updated){
            throw new ResourceNotFoundException("Category with ID " + id + " not found or already deleted");
        }
        return true;
    }
}
