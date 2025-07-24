package com.nirmal.project.exception;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class GlobalExceptionMapper<T extends Throwable> implements ExceptionMapper<T> {

    @Override
    public Response toResponse(T exception) {
        return GlobalExceptionHandler.handle(exception);
    }
}
