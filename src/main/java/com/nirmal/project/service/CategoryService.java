package com.nirmal.project.service;

import com.nirmal.project.model.Category;
import java.util.List;

public interface CategoryService {
    public List<Category> getAllCategories();
    public Boolean saveCategory(Category category);

}
