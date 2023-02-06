package com.softserve.itacademy.todolist.service;

import com.softserve.itacademy.todolist.dto.TaskDto;
import com.softserve.itacademy.todolist.dto.request.TaskRequestDto;
import com.softserve.itacademy.todolist.dto.response.UserResponseDto;
import com.softserve.itacademy.todolist.model.Task;
import com.softserve.itacademy.todolist.model.ToDo;

import java.util.List;
import java.util.stream.Collectors;

public interface TaskService {
    Task create(Task task);
    Task readById(long id);
    Task update(Task task);
    void delete(long id);
    List<Task> getAll();

    List<TaskDto> getByTodoId(long todoId);

    ToDo getToDoById(long todoId);

}
