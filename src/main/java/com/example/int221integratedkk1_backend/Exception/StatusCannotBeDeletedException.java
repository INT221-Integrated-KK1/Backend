package com.example.int221integratedkk1_backend.Exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class StatusCannotBeDeletedException extends RuntimeException {
    public StatusCannotBeDeletedException(String message) {
        super(message);
    }
}
