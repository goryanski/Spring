package com.example.practice.util;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

// separate class with static method which returns validation errors to the client - one method for all controllers
public class ErrorsUtil {
    public static void returnErrorsToClient(BindingResult bindingResult) {
        // form all errors to one string
        StringBuilder errorMsg = new StringBuilder();

        List<FieldError> errors = bindingResult.getFieldErrors();
        for (FieldError error : errors) {
            errorMsg.append(error.getField())
                    .append(" - ").append(error.getDefaultMessage() == null ? error.getCode() : error.getDefaultMessage())
                    .append(";");
        }

        // pass this string to custom exception class
        throw new MeasurementException(errorMsg.toString());
    }
}