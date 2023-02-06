package com.softserve.itacademy.todolist.controller;

import com.softserve.itacademy.todolist.dto.TaskDto;
import com.softserve.itacademy.todolist.dto.TaskTransformer;
import com.softserve.itacademy.todolist.dto.request.TaskRequestDto;
import com.softserve.itacademy.todolist.dto.response.TaskResponseDto;
import com.softserve.itacademy.todolist.dto.response.UserResponseDto;
import com.softserve.itacademy.todolist.exception.EntityNotFoundException;
import com.softserve.itacademy.todolist.exception.ValidationException;
import com.softserve.itacademy.todolist.model.Task;
import com.softserve.itacademy.todolist.service.StateService;
import com.softserve.itacademy.todolist.service.TaskService;
import com.softserve.itacademy.todolist.service.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/tasks")
public class TaskController {

    private final TaskService taskService;
    private final ToDoService toDoService;
    private final StateService stateService;

    @Autowired
    public TaskController(TaskService taskService, ToDoService toDoService, StateService stateService){
        this.taskService = taskService;
        this.toDoService = toDoService;
        this.stateService = stateService;
    }

    @GetMapping
    public Map<String, Object> getAll(){
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.toString());
        response.put("body", taskService.getAll().stream()
                .map(TaskResponseDto::new)
                .collect(Collectors.toList()));

        return response;
    }

    @GetMapping("/{id}")
    public Map<String, Object> readById(@PathVariable("id") long id){

        Map<String, Object> response = new HashMap<>();
        try {
            Task task = taskService.readById(id);
            response.put("status", HttpStatus.OK.toString());
            response.put("body", new TaskResponseDto(task));
        }catch (EntityNotFoundException e){
            response.put("status", HttpStatus.NOT_FOUND.toString());
            response.put("body", null);
        }
        return response;
    }

    @PostMapping("/create/{todo_id}")
    public Map<String, Object> create(@PathVariable("todo_id") long todoId,
                                      @Validated @RequestBody TaskRequestDto taskRequestDto,
                                      BindingResult result) {
        if (result.hasErrors()) {
            throw new ValidationException(result.getFieldErrors());
        }
        Task task = TaskTransformer.convertToEntity(taskRequestDto,
                taskService.getToDoById(todoId), stateService.getByName("NEW"));

        task = taskService.create(task);

        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.CREATED.toString());
        response.put("body", new TaskResponseDto(task));
        return response;
    }

    @PutMapping("/{id}")
    public Map<String, Object> update(@Validated @RequestBody TaskDto taskDto,
                                      @PathVariable("id") long id,
                                      BindingResult result) {
        if (result.hasErrors()) {
            throw new ValidationException(result.getFieldErrors());
        }

        taskDto.setId(id);
        Task task = TaskTransformer.convertToEntity(taskDto,
                taskService.getToDoById(taskDto.getTodoId()),
                stateService.readById(taskDto.getStateId()));

        task = taskService.update(task);

        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.CREATED.toString());
        response.put("body", new TaskResponseDto(task));
        return response;
    }

    @DeleteMapping("/{id}")
    public Map<String, Object> delete(@PathVariable("id") long id){
        Map<String, Object> response = new HashMap<>();
        try {
            taskService.delete(id);
            response.put("status", HttpStatus.OK);
        }catch (EntityNotFoundException e){
            response.put("status", HttpStatus.NOT_FOUND.toString());
        }
        return response;
    }




}
