package com.softserve.itacademy.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TodoTests {
    private static Role mentorRole;
    private static Role traineeRole;
    private static User validUser;

    private static ToDo validToDo;

    @BeforeAll
    static void init(){
        mentorRole = new Role();
        mentorRole.setName("MENTOR");
        traineeRole = new Role();
        traineeRole.setName("TRAINEE");
        validUser  = new User();
        validUser.setEmail("valid@cv.edu.ua");
        validUser.setFirstName("Valid-Name");
        validUser.setLastName("Valid-Name");
        validUser.setPassword("qwQW12!@");
        validUser.setRole(traineeRole);
        validToDo = new ToDo();
        validToDo.setCreatedAt(LocalDateTime.now());
        validToDo.setOwner(validUser);
        validToDo.setId(1);
        validToDo.setCollaborants(new HashSet<>());
    }

    @Test
    void checkIfToDoTitleIsNotEmpty(){
        validToDo.setTitle("ValidTitle");
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<ToDo>> violations = validator.validate(validToDo);
        assertEquals(0,violations.size(),"Todo title is not valid");
    }

    @Test
    void checkIfToDoTitleIsEmpty(){
        validToDo.setTitle("");
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<ToDo>> violations = validator.validate(validToDo);
        assertEquals(1, violations.size(),"Todo title should not be empty");
    }

    @Test
    void checkIfToDoTitleIsNull(){
        validToDo.setTitle(null);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<ToDo>> violations = validator.validate(validToDo);
        assertEquals(1,violations.size(),"Todo title should not be null");
    }
}