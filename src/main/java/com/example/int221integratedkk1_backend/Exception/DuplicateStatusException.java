package com.example.int221integratedkk1_backend.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DuplicateStatusException extends RuntimeException {
    public DuplicateStatusException(String message) {
        super(message);
    }
}
