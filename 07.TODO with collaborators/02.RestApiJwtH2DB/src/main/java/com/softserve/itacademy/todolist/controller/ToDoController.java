package com.softserve.itacademy.todolist.controller;

import com.softserve.itacademy.todolist.dto.request.ToDoRequestDto;
import com.softserve.itacademy.todolist.dto.response.ToDoResponseDto;
import com.softserve.itacademy.todolist.exception.AccessDeniedException;
import com.softserve.itacademy.todolist.exception.ValidationException;
import com.softserve.itacademy.todolist.security.PersonDetails;
import com.softserve.itacademy.todolist.service.TaskService;
import com.softserve.itacademy.todolist.service.ToDoService;
import com.softserve.itacademy.todolist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/todos")
public class ToDoController {
    private final ToDoService todoService;
    private final TaskService taskService;
    private final UserService userService;

    @Autowired
    public ToDoController(ToDoService todoService, TaskService taskService, UserService userService) {
        this.todoService = todoService;
        this.taskService = taskService;
        this.userService = userService;
    }

    @PostMapping("/create/users/{owner_id}")
    public Map<String, Object> create(@PathVariable("owner_id") long ownerId,
                         @Validated @RequestBody ToDoRequestDto todoDto,
                         BindingResult result) {
        if (result.hasErrors()) {
            throw new ValidationException(result.getFieldErrors());
        }
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.CREATED.toString());
        response.put("body",todoService.create(todoDto, ownerId));
        return response;
    }

    @GetMapping("/{id}/tasks")
    public Map<String, Object> read(@PathVariable long id) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.toString());
        response.put("todo", todoService.readById(id));
        response.put("tasks", taskService.getByTodoId(id));
        response.put("users", userService.getPossibleCollaborators(id));
        return response;
    }


    @PatchMapping("/{todo_id}/update/users/{owner_id}")
    public Map<String, Object> update(@PathVariable("todo_id") long todoId,
                         @PathVariable("owner_id") long ownerId,
                         @Validated @RequestBody ToDoRequestDto todoDto,
                         BindingResult result) {
        if (result.hasErrors()) {
            throw new ValidationException(result.getFieldErrors());
        }
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.toString());
        response.put("body", todoService.update(todoDto, todoId, ownerId));
        return response;
    }

    @DeleteMapping("/{todo_id}/delete/users/{owner_id}")
    public Map<String, Object> delete(@PathVariable("todo_id") long todoId, @PathVariable("owner_id") long ownerId) {
        todoService.delete(todoId);
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.toString());
        return response;
    }

    @GetMapping("/all/users/{user_id}")
    public Map<String, Object> getAll(@PathVariable("user_id") long userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        if(personDetails.getPerson().getId() != userId) {
            throw new AccessDeniedException("You can access only your todos");
        }
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.toString());
        response.put("todos", todoService.getByUserId(userId));
        response.put("user", userService.readById(userId));
        return response;
    }

    @GetMapping("/{id}/collaborators")
    public Map<String, Object> getToDoCollaborators(@PathVariable("id") long todoId) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.CREATED.toString());
        response.put("body", todoService.getToDoCollaborators(todoId));
        return response;
    }

    @PatchMapping("/{id}/add/collaborator")
    public Map<String, Object> addCollaborator(@PathVariable("id") long todoId, @RequestParam("user_id") long userId) {
        todoService.addCollaborator(todoId, userId);
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.CREATED.toString());
        return response;
    }

    @PatchMapping("/{id}/remove/collaborator")
    public Map<String, Object> removeCollaborator(@PathVariable("id") long todoId, @RequestParam("user_id") long userId) {
        todoService.removeCollaborator(todoId, userId);
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.toString());
        return response;
    }
}
