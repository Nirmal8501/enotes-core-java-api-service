package com.nirmal.project.dao.impl;

import com.nirmal.project.dao.CategoryDao;
import com.nirmal.project.db.DBConnectionManager;
import com.nirmal.project.exception.DatabaseAccessException;
import com.nirmal.project.model.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
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

    private static final String GET_ACTIVE_CATEGORIES = """
             SELECT id, name, description, is_active, is_deleted, created_by, created_on, updated_by, updated_on FROM category where is_active = true;
            """;

    private static final String GET_CATEGORY_BY_ID = """
            Select id, name, description, is_active, is_deleted, created_by, created_on, updated_by, updated_on FROM category where id = ? AND is_deleted = false
            """;

    private static final String DELETE_CATEGORY_BY_ID = """
            UPDATE category SET is_deleted = true WHERE id = ?;
            """;

    private static final String UPDATE_CATEGORY_BY_ID = """
            UPDATE category SET name = COALESCE(?, name),
            description = COALESCE(?, description),
            is_active = COALESCE(?, is_active),
            updated_by = COALESCE(?, updated_by),
            updated_on = CURRENT_TIMESTAMP
            WHERE id = ? AND is_deleted = false;
           """;

    private static final String CATEGORY_EXISTS_BY_NAME = """
    SELECT 1 FROM category WHERE name = ? AND is_deleted = false
""";

    @Override
    public Optional<Category> createCategory(Category category) {
        try (Connection connection = DBConnectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(INSERT_CATEGORY, Statement.RETURN_GENERATED_KEYS);
        ) {
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
        } catch (Exception e) {
            logger.error("Category Insert failed...", e);
            throw new DatabaseAccessException("Exception Occurred in DB operation: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Category> readAllCategories() {
        List<Category> fetchedCategories = new ArrayList<>();
        try (Connection connection = DBConnectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(GET_ALL_CATEGORIES);
             ResultSet rs = ps.executeQuery();
        ) {
            while (rs.next()) {
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
            return fetchedCategories;
        } catch (Exception e) {
            logger.error("Error while fetching all categories...", e);
            throw new DatabaseAccessException("Exception Occurred in DB operation: " + e.getMessage());
        }
    }

    @Override
    public List<Category> readActiveCategories() {
        List<Category> fetchedCategories = new ArrayList<>();
        try(Connection connection = DBConnectionManager.getConnection();
            PreparedStatement ps = connection.prepareStatement(GET_ACTIVE_CATEGORIES);
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
            logger.info("Returning Fetched Active Categories: {}", fetchedCategories);
            return fetchedCategories;
        }catch (Exception e){
            logger.error("Exception while trying to Fetch all active categories....", e);
            throw new DatabaseAccessException("Exception Occurred in DB operation: " + e.getMessage());
        }
    }

    @Override
    public Optional<Category> readCategoryById(Integer id) {

        try (Connection connection = DBConnectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(GET_CATEGORY_BY_ID);
        ) {
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                Category category = new Category();
                category.setId(resultSet.getInt("id"));
                category.setName(resultSet.getString("name"));
                category.setDescription(resultSet.getString("description"));
                category.setIsActive(resultSet.getBoolean("is_active"));
                category.setIsDeleted(resultSet.getBoolean("is_deleted"));
                category.setCreatedBy(resultSet.getInt("created_by"));
                category.setCreatedOn(resultSet.getTimestamp("created_on"));
                category.setUpdatedBy(resultSet.getInt("updated_by"));
                category.setUpdatedOn(resultSet.getTimestamp("updated_on"));

                logger.info("Fetched category with ID {}: {}", id, category);
                return Optional.of(category);
            } else {
                logger.warn("No category found with ID: {}", id);
                return Optional.empty();
            }

        } catch (Exception e) {
            logger.error("Error while fetching category with ID: {}", id, e);
            throw new RuntimeException("Exception occurred while inserting category to DB");
        }
    }

    @Override
    public Boolean deleteCategoryById(Integer id) {
        try(Connection connection = DBConnectionManager.getConnection();
            PreparedStatement ps = connection.prepareStatement(DELETE_CATEGORY_BY_ID)
        ){
            ps.setInt(1, id);
            int rowsDeleted = ps.executeUpdate();
            if (rowsDeleted > 0) {
                logger.info("Category with ID {} marked as deleted", id);
                return true;
            } else {
                logger.warn("No category found to delete with ID {}", id);
                return false;
            }

        } catch (Exception e) {
            logger.error("Exception while deleting category with ID: {}", id, e);
            throw new DatabaseAccessException("Exception Occurred in DB operation: " + e.getMessage());
        }
    }

    @Override
    public Boolean updateCategoryById(Integer id, Category category) {
        try(Connection connection = DBConnectionManager.getConnection();
            PreparedStatement ps = connection.prepareStatement(UPDATE_CATEGORY_BY_ID);
        ){
            ps.setString(1, category.getName());
            ps.setString(2, category.getDescription());
            if (category.getIsActive() != null) {
                ps.setBoolean(3, category.getIsActive());
            } else {
                ps.setNull(3, Types.BOOLEAN);
            }
            if (category.getUpdatedBy() != null) {
                ps.setInt(4, category.getUpdatedBy());
            } else {
                ps.setNull(4, Types.INTEGER);
            }
            ps.setInt(5, id);

            int rowsUpdated = ps.executeUpdate();
            logger.info("Category with ID {} updated successfully...", id);
            return rowsUpdated > 0;

        } catch (Exception e) {
            logger.error("Error updating category with ID: {}", id, e);
            throw new DatabaseAccessException("Exception Occurred in DB operation: " + e.getMessage());
        }
    }

    @Override
    public boolean categoryExistsByName(String name) {
        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(CATEGORY_EXISTS_BY_NAME)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // true if exists
            }
        } catch (Exception e) {
            logger.error("Error checking category existence", e);
            throw new DatabaseAccessException("DB error during existence check");
        }
    }
}
