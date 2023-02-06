package com.softserve.itacademy.todolist.service;

import com.softserve.itacademy.todolist.dto.request.ToDoRequestDto;
import com.softserve.itacademy.todolist.dto.response.ToDoResponseDto;
import com.softserve.itacademy.todolist.dto.response.UserResponseDto;
import com.softserve.itacademy.todolist.model.ToDo;

import java.util.List;

public interface ToDoService {
    ToDoResponseDto create(ToDoRequestDto todo, long ownerId);
    ToDoResponseDto readById(long id);
    ToDoResponseDto update(ToDoRequestDto todoDto, long todoId, long ownerId);
    void delete(long id);
    List<ToDoResponseDto> getAll();
    List<ToDoResponseDto> getByUserId(long userId);
    void addCollaborator(long todoId, long userId);
    List<UserResponseDto> getToDoCollaborators(long todoId);
    void removeCollaborator(long todoId, long userId);
}
