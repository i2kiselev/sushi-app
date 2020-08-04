package com.i2kiselev.springCourseProject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoEntityException extends RuntimeException {
    public NoEntityException(String errorMessage) {
        super(errorMessage);
    }
}
