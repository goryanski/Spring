package com.softserve.itacademy.todolist.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ToDoRequestDto {
    @NotBlank(message = "The 'title' cannot be empty")
    private String title;
}
