package com.softserve.itacademy.model;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TaskTests {
    @Test
    void constraintViolationOnValidTaskName() {
        Task task = new Task();
        task.setName("My first task");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Task>> violations = validator.validate(task);
        String expectedError = "";
        String actualError = getValidationErrors(violations);
        assertEquals(expectedError, actualError, "Task name validation is incorrect");
    }

    @Test
    void constraintViolationOnEmptyTaskName() {
        Task task = new Task();
        task.setName("");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Task>> violations = validator.validate(task);
        String expectedError = "Task name should be between 3 and 200 symbols";
        String actualError = getValidationErrors(violations);
        assertEquals(expectedError, actualError, "Empty task name validation is incorrect");
    }

    @Test
    void constraintViolationOnIncorrectSizeTaskName() {
        Task task = new Task();
        task.setName("My");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Task>> violations = validator.validate(task);
        String expectedError = "Task name should be between 3 and 200 symbols";
        String actualError = getValidationErrors(violations);
        assertEquals(expectedError, actualError, "Incorrect task name validation is incorrect");
    }

    @Test
    void constraintViolationOnNullTaskName() {
        Task task = new Task();
        task.setName(null);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Task>> violations = validator.validate(task);
        String expectedError = "Task name cannot be null";
        String actualError = getValidationErrors(violations);
        assertEquals(expectedError, actualError, "Null task name validation is incorrect");
    }

    private String getValidationErrors(Set<ConstraintViolation<Task>> violations) {
        StringBuilder stringBuilder = new StringBuilder();
        for (ConstraintViolation<Task> violation : violations) {
            stringBuilder.append(violation.getMessage());
        }
        return stringBuilder.toString();
    }
}
