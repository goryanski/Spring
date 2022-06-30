package com.example.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Task {
    private int id;

    @NotEmpty(message = "Task should not be empty")
    @Size(min = 2, max = 264, message = "Text size should be between 2 and 264 symbols")
    private String text;

    public Task() {}

    public Task(int id, String text) {
        this.id = id;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}