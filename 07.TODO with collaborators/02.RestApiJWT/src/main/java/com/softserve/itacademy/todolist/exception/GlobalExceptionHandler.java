package com.softserve.itacademy.todolist.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler { // consider extending ResponseEntityExceptionHandler

    @ExceptionHandler
    public Map<String, Object> handleEntityNotFoundException(EntityNotFoundException ex) {
        return getResponse(HttpStatus.BAD_REQUEST.toString(), ex.getMessage());
    }

    @ExceptionHandler
    public Map<String, Object> handleValidationException(ValidationException ex) {
        return getResponse(HttpStatus.BAD_REQUEST.toString(), getValidationErrorMessage(ex.getFieldErrors()));
    }

    @ExceptionHandler
    public Map<String, Object> handleNullEntityReferenceException(NullEntityReferenceException ex) {
        return getResponse(HttpStatus.BAD_REQUEST.toString(), ex.getMessage());
    }

    @ExceptionHandler
    public Map<String, Object> handleEntityAlreadyExistsException(EntityAlreadyExistsException ex) {
        return getResponse(HttpStatus.CONFLICT.toString(), ex.getMessage());
    }

    @ExceptionHandler
    public Map<String, Object> handleAccessDeniedException(AccessDeniedException ex) {
        return getResponse(HttpStatus.FORBIDDEN.toString(), ex.getMessage());
    }


    private Map<String, Object> getResponse(String status, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", status);
        response.put("message", message);
        return response;
    }

    private String getValidationErrorMessage(List<FieldError> fieldErrors) {
        // form all object field errors to one string
        StringBuilder errorMessage = new StringBuilder();

        for (FieldError error : fieldErrors) {
            errorMessage.append(error.getField())
                    .append(" - ")
                    .append(error.getDefaultMessage() == null ? error.getCode() : error.getDefaultMessage())
                    .append("; ");
        }
        return errorMessage.toString();
    }
}
