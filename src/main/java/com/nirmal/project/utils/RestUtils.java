package com.nirmal.project.utils;

import com.nirmal.project.exception.DatabaseAccessException;
import com.nirmal.project.exception.GlobalExceptionMapper;
import com.nirmal.project.exception.ResourceNotFoundException;
import io.muserver.rest.RestHandlerBuilder;
import jakarta.validation.ValidationException;

import java.sql.SQLException;

public class RestUtils {
    public static RestHandlerBuilder buildHandler(Object controller){
        RestHandlerBuilder builder = RestHandlerBuilder.restHandler(controller);

        builder.addExceptionMapper(ResourceNotFoundException.class, new GlobalExceptionMapper<>());
        builder.addExceptionMapper(ValidationException.class, new GlobalExceptionMapper<>());
        builder.addExceptionMapper(IllegalArgumentException.class, new GlobalExceptionMapper<>());
        builder.addExceptionMapper(SQLException.class, new GlobalExceptionMapper<>());
        builder.addExceptionMapper(Exception.class, new GlobalExceptionMapper<>());
        builder.addExceptionMapper(RuntimeException.class, new GlobalExceptionMapper<>());
        builder.addExceptionMapper(NullPointerException.class, new GlobalExceptionMapper<>());
        builder.addExceptionMapper(DatabaseAccessException.class, new GlobalExceptionMapper<>());

        return builder;
    }
}
