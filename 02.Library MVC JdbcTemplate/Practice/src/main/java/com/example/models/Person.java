package com.example.models;

import javax.validation.constraints.*;
import java.util.Calendar;

public class Person {
    //private static final int currentYear = Calendar.getInstance().get(Calendar.YEAR);
    private int id;

    @Size(min = 2, max = 74, message = "FullName should be between 2 and 74 symbols")
    private String fullName;

    @Email(message = "Email should be valid")
    private String email;

    @Min(value = 1900, message = "Year can not be less then 1900")
    //@Max(value = )
    private int year;

    public Person() {}

    public Person(int id, String fullName, String email, int year) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return fullName + ", " + year;
    }
}
