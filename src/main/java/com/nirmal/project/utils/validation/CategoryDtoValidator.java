package com.nirmal.project.utils.validation;

import com.nirmal.project.dto.CategoryDto;

import jakarta.validation.ValidationException;
import java.util.LinkedHashMap;
import java.util.Map;

public class CategoryDtoValidator {

    public static void validate(CategoryDto dto) {
        if (dto == null) {
            throw new ValidationException("Category data is required");
        }

        Map<String, String> errors = new LinkedHashMap<>();

        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            errors.put("name", "Name is required");
        }

        if (dto.getDescription() == null || dto.getDescription().trim().isEmpty()) {
            errors.put("description", "Description is required");
        } else if (dto.getDescription().length() > 200) {
            errors.put("description", "Description must not exceed 200 characters");
        }

        if (dto.getActive() == null) {
            errors.put("active", "Active status is required");
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(convertErrorsToString(errors));
        }
    }

    private static String convertErrorsToString(Map<String, String> errors) {
        StringBuilder sb = new StringBuilder();
        errors.forEach((field, message) -> sb.append(field).append(": ").append(message).append("; "));
        return sb.toString().trim();
    }
}
