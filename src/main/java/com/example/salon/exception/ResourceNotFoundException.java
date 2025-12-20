package com.example.salon.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    private ResourceNotFoundException(String message) {
        super(message);
    }

    public static ResourceNotFoundException forId(String resource, Long id) {
        return new ResourceNotFoundException(
                resource + " not found with id: " + id
        );
    }

    public static ResourceNotFoundException forField(
            String resource,
            String field,
            String value
    ) {
        return new ResourceNotFoundException(
                resource + " not found with " + field + ": " + value
        );
    }
}
