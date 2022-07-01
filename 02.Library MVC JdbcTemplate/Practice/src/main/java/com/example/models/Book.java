package com.example.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public class Book {
    private int id;
    private int personId;

    @Size(min = 2, max = 200, message = "Book name should be between 2 and 200 symbols")
    private String name;

    @Size(min = 2, max = 74, message = "Author name should be between 2 and 74 symbols")
    private String author;

    @Min(value = 1900, message = "Year can not be less then 1900")
    //@Max(value = )
    private int year;

    public Book() {}

    public Book(int id, int personId, String name, String author, int year) {
        this.id = id;
        this.personId = personId;
        this.name = name;
        this.author = author;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return name + ", " + author + ", " + year;
    }
}
