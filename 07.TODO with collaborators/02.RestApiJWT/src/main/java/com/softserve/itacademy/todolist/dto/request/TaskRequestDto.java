package com.softserve.itacademy.todolist.dto.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class TaskRequestDto {

    @NotBlank(message = "The 'name' cannot be empty")
    String name;
    @NotBlank(message = "The 'priority' cannot be empty")
    String priority;
}
