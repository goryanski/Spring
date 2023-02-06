package com.softserve.itacademy.todolist.controller;

import com.softserve.itacademy.todolist.dto.UserDto;
import com.softserve.itacademy.todolist.dto.response.UserResponseDto;
import com.softserve.itacademy.todolist.exception.ValidationException;
import com.softserve.itacademy.todolist.model.User;
import com.softserve.itacademy.todolist.service.TaskService;
import com.softserve.itacademy.todolist.service.ToDoService;
import com.softserve.itacademy.todolist.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static com.softserve.itacademy.todolist.dto.UserDtoTransform.convertToEntity;


@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final ToDoService toDoService;
    private final TaskService taskService;

    @GetMapping
    public Map<String, Object> getAll() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.toString());
        response.put("body", userService.getAll().stream()
                .map(UserResponseDto::new)
                .collect(Collectors.toList()));

        return response;
    }

    @GetMapping("/{id}")
    public Map<String, Object> getUserById(@PathVariable long id) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.toString());
        response.put("body", userService.readById(id));
        return response;
    }

    @GetMapping("/{user_id}/todos")
    public Map<String, Object> getUsersTodos(@PathVariable long user_id) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.toString());
        response.put("body", toDoService.getByUserId(user_id));
        return response;
    }

    @GetMapping("/{user_id}/todos/{todo_id}/tasks")
    public Map<String, Object> getUsersTaskFromTodo(@PathVariable long user_id, @PathVariable long todo_id) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.toString());
        response.put("todo", toDoService.getByUserId(user_id));
        response.put("tasks", taskService.getByTodoId(todo_id));
        return response;
    }

    @PostMapping("/create")
    public Map<String, Object> create(@Validated @RequestBody UserDto userCreateDto,
                                      BindingResult result) {
        if (result.hasErrors()) {
            throw new ValidationException(result.getFieldErrors());
        }
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.CREATED.toString());
        response.put("body", userService.singUpUser(userCreateDto));
        return response;
    }

    @PatchMapping("/{user_id}/update")
    public Map<String, Object> update(@PathVariable long user_id,
                                      @Validated @RequestBody UserDto updateDto,
                                      BindingResult result) {
        if (result.hasErrors()) {
            throw new ValidationException(result.getFieldErrors());
        }
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.toString());
        response.put("body", userService.update(updateDto, user_id));
        return response;
    }

    @DeleteMapping("/{user_id}/delete")
    public Map<String, Object> delete(@PathVariable long user_id) {
        userService.delete(user_id);
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.toString());
        return response;
    }

}
