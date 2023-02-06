package com.softserve.itacademy.todolist.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.softserve.itacademy.todolist.model.Task;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TaskResponseDto {

    private long id;

    private String name;

    private String priority;

    private long toDoId;

    private long stateId;

    public TaskResponseDto(Task task){
        this.id = task.getId();
        this.name = task.getName();
        this.priority = task.getPriority().name();
        this.toDoId = task.getTodo().getId();
        this.stateId = task.getState().getId();
    }

}
