package com.nirmal.project.exception;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class NullPointerExceptionMapper implements ExceptionMapper<java.lang.NullPointerException> {

    @Override
    public Response toResponse(NullPointerException exception) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorResponse(500, exception.getMessage()))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

}
