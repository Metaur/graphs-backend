package com.example.graphs.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Requested vertex not found")
public class VertexNotFoundException extends RuntimeException {
    public VertexNotFoundException(String message) {
        super(message);
    }
}
