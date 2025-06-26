package com.nirmal.project.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException (String message){
        super(message);
    }
}
