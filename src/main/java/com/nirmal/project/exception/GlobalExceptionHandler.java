package com.nirmal.project.exception;

import jakarta.validation.ValidationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;

public class GlobalExceptionHandler {

    public static Response handle(Exception ex) {
        if (ex instanceof ResourceNotFoundException) {
            return buildResponse(404, ex.getMessage());
        } else if (ex instanceof ValidationException) {
            return buildResponse(400, ex.getMessage());
        } else if (ex instanceof IllegalArgumentException) {
            return buildResponse(422, ex.getMessage());
        } else if (ex instanceof SQLException) {
            return buildResponse(500, "Database error: " + ex.getMessage());
        } else if (ex instanceof DatabaseAccessException) {
            return buildResponse(500, ex.getMessage());
        } else {
            return buildResponse(500, "Something went wrong");
        }
    }

    private static Response buildResponse(int statusCode, String message) {
        return Response.status(statusCode)
                .entity("{\"error\": \"" + message + "\"}")
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}