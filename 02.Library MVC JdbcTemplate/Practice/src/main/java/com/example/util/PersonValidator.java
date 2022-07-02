package com.example.util;

import com.example.dao.PersonDAO;
import com.example.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Calendar;


@Component
public class PersonValidator implements Validator {
    private final PersonDAO personDAO;

    @Autowired
    public PersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;

        // full name and email must be unique
        if (personDAO.getPersonByFullName(person.getFullName()).isPresent()) {
            errors.rejectValue("fullName", "", "Person with this full name already exists");
        }
        if (personDAO.getPersonByEmail(person.getEmail()).isPresent()) {
            errors.rejectValue("email", "", "Person with this email already exists");
        }

        // person birth year cannot be greater than current year
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        if(person.getYear() > currentYear) {
            errors.rejectValue("year", "", "Person birth year cannot be greater than the current year");
        }
    }
}
