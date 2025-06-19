package com.nirmal.project.dao.impl;

import com.nirmal.project.dao.CategoryDao;
import com.nirmal.project.db.DBConnectionManager;
import com.nirmal.project.model.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

public class CategoryDaoImpl implements CategoryDao {
    private static Logger logger = LoggerFactory.getLogger(CategoryDaoImpl.class);

    private static final String INSERT_CATEGORY = """
        INSERT INTO category (name, description, is_active, is_deleted, created_by)
        VALUES (?, ?, ?, ?, ?);
    """;

    private static final String GET_ALL_CATEGORIES = """
        SELECT id, name, description, is_active, is_deleted, created_by, created_on, updated_by, updated_on
        FROM category WHERE is_deleted = false;
    """;

    @Override
    public Optional<Category> createCategory(Category category) {
        try(Connection connection = DBConnectionManager.getConnection();
            PreparedStatement ps = connection.prepareStatement(INSERT_CATEGORY, Statement.RETURN_GENERATED_KEYS);
        ){
            ps.setString(1, category.getName());
            ps.setString(2, category.getDescription());
            ps.setBoolean(3, category.getIsActive());
            ps.setBoolean(4, category.getIsDeleted());
            ps.setInt(5, category.getCreatedBy());

            int rowsInserted = ps.executeUpdate();
            if (rowsInserted > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        int generatedId = rs.getInt(1);
                        category.setId(generatedId);
                        return Optional.of(category);
                    }
                }
            }
            logger.info("Successfully Inserted Category: {}", category);
        }catch (Exception e){
            logger.error("Category Insert failed...", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Category> readAllCategories() {
        List<Category> fetchedCategories = new ArrayList<>();
        try(Connection connection = DBConnectionManager.getConnection();
            PreparedStatement ps = connection.prepareStatement(GET_ALL_CATEGORIES);
            ResultSet rs = ps.executeQuery();
        ){
           while (rs.next()){
               Category c = new Category();
               c.setId(rs.getInt("id"));
               c.setName(rs.getString("name"));
               c.setDescription(rs.getString("description"));
               c.setIsActive(rs.getBoolean("is_active"));
               c.setIsDeleted(rs.getBoolean("is_deleted"));
               c.setCreatedBy(rs.getInt("created_by"));
               c.setCreatedOn(rs.getTimestamp("created_on"));
               c.setUpdatedBy(rs.getInt("updated_by"));
               c.setUpdatedOn(rs.getTimestamp("updated_on"));

               fetchedCategories.add(c);
           }
            logger.info("Returning Fetched Categories: {}", fetchedCategories);
        }catch (Exception e){
            logger.error("Exception while trying to Fetch all categories....", e);
        }
        return fetchedCategories;
    }
}
