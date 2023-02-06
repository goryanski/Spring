package com.softserve.itacademy.todolist.exception;

import lombok.Getter;
import org.springframework.validation.FieldError;

import java.util.List;

@Getter
public class ValidationException extends RuntimeException {
    private List<FieldError> fieldErrors;

    public ValidationException() {}

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(List<FieldError> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }
}
