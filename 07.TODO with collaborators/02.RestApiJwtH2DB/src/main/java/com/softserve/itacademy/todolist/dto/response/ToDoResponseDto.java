package com.softserve.itacademy.todolist.dto.response;

import com.softserve.itacademy.todolist.model.ToDo;
import lombok.Getter;
import lombok.Setter;
import java.time.format.DateTimeFormatter;


@Getter
@Setter
public class ToDoResponseDto {
    private long id;
    private String title;
    private String createdAt;
    private long ownerId;

    public ToDoResponseDto(ToDo toDo) {
        this.id = toDo.getId();
        this.title = toDo.getTitle();
        this.ownerId = toDo.getOwner().getId();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy 'at' hh:mm");
        this.createdAt = toDo.getCreatedAt().format(formatter);
    }
}
