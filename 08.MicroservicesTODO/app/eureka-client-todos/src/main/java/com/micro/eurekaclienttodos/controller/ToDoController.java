package com.micro.eurekaclienttodos.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/todos")
public class ToDoController {

    @GetMapping
    public String getAll() {
        return "They are all here!";
    }
}
