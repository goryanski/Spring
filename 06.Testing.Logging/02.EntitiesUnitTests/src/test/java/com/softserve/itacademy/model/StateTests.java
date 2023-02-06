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
public class StateTests {
    @Test
    void constraintViolationOnValidStateName() {
        State state = new State();
        state.setName("State 1-1 not_active");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<State>> violations = validator.validate(state);
        String expectedError = "";
        String actualError = getValidationErrors(violations);
        assertEquals(expectedError, actualError, "State name validation is incorrect");
    }

    @Test
    void constraintViolationOnEmptyStateName() {
        State state = new State();
        state.setName("");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<State>> violations = validator.validate(state);
        String expectedError = "State name should be from 1 to 20 latin letters, numbers, dash, space and underscore";
        String actualError = getValidationErrors(violations);
        assertEquals(expectedError, actualError, "Empty state name validation is incorrect");
    }

    @Test
    void constraintViolationOnIncorrectStateName() {
        State state = new State();
        state.setName("State: hungry!");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<State>> violations = validator.validate(state);
        String expectedError = "State name should be from 1 to 20 latin letters, numbers, dash, space and underscore";
        String actualError = getValidationErrors(violations);
        assertEquals(expectedError, actualError, "Incorrect state name validation is incorrect");
    }

    @Test
    void constraintViolationOnNullStateName() {
        State state = new State();
        state.setName(null);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<State>> violations = validator.validate(state);
        String expectedError = "State name cannot be null";
        String actualError = getValidationErrors(violations);
        assertEquals(expectedError, actualError, "Null state name validation is incorrect");
    }


    private String getValidationErrors(Set<ConstraintViolation<State>> violations) {
        StringBuilder stringBuilder = new StringBuilder();
        for (ConstraintViolation<State> violation : violations) {
            stringBuilder.append(violation.getMessage());
        }
        return stringBuilder.toString();
    }
}
