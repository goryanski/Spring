package com.softserve.itacademy.controller;

import com.softserve.itacademy.dto.TaskDto;
import com.softserve.itacademy.dto.TaskTransformer;
import com.softserve.itacademy.model.Priority;
import com.softserve.itacademy.model.Task;
import com.softserve.itacademy.service.StateService;
import com.softserve.itacademy.service.TaskService;
import com.softserve.itacademy.service.ToDoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;
    private final ToDoService todoService;
    private final StateService stateService;

    Logger logger = LoggerFactory.getLogger(TaskController.class);

    public TaskController(TaskService taskService, ToDoService todoService, StateService stateService) {
        this.taskService = taskService;
        this.todoService = todoService;
        this.stateService = stateService;
    }

    @GetMapping("/create/todos/{todo_id}")
    public String create(@PathVariable("todo_id") long todoId, Model model) {
        logger.info("method create(): get create Task page");
        model.addAttribute("task", new TaskDto());
        model.addAttribute("todo", todoService.readById(todoId));
        model.addAttribute("priorities", Priority.values());
        return "create-task";
    }

    @PostMapping("/create/todos/{todo_id}")
    public String create(@PathVariable("todo_id") long todoId, Model model,
                         @Validated @ModelAttribute("task") TaskDto taskDto, BindingResult result) {
        logger.info("method create(): received a creation POST request");
        if (result.hasErrors()) {
            logger.error("method update(): validation errors while creating a Task");
            model.addAttribute("todo", todoService.readById(todoId));
            model.addAttribute("priorities", Priority.values());
            return "create-task";
        }
        logger.info("method create(): there are no validation errors while creating a new Task");
        Task task = TaskTransformer.convertToEntity(
                taskDto,
                todoService.readById(taskDto.getTodoId()),
                stateService.getByName("New")
        );
        logger.info("method create(): transform taskDto to entity");
        taskService.create(task);
        logger.info("method create(): taskService save new task(Id=" + task.getId() + ")");
        return "redirect:/todos/" + todoId + "/tasks";
    }

    @GetMapping("/{task_id}/update/todos/{todo_id}")
    public String update(@PathVariable("task_id") long taskId, @PathVariable("todo_id") long todoId, Model model) {
        logger.info("method update(): get update Task page request");
        TaskDto taskDto = TaskTransformer.convertToDto(taskService.readById(taskId));
        logger.info("method update(): receive task from taskService and convert to taskDto");
        model.addAttribute("task", taskDto);
        model.addAttribute("priorities", Priority.values());
        model.addAttribute("states", stateService.getAll());
        logger.info("method update(): send response update-task page");
        return "update-task";
    }

    @PostMapping("/{task_id}/update/todos/{todo_id}")
    public String update(@PathVariable("task_id") long taskId, @PathVariable("todo_id") long todoId, Model model,
                         @Validated @ModelAttribute("task") TaskDto taskDto, BindingResult result) {
        logger.info("method update(): received a creation POST request");
        if (result.hasErrors()) {
            logger.error("method update(): validation errors while updating a Task");
            model.addAttribute("priorities", Priority.values());
            model.addAttribute("states", stateService.getAll());
            return "update-task";
        }
        logger.info("method update(): there are no validation errors while creating a new Task");
        Task task = TaskTransformer.convertToEntity(
                taskDto,
                todoService.readById(taskDto.getTodoId()),
                stateService.readById(taskDto.getStateId())
        );
        logger.info("method update(): receive todo and state by Id, transform taskDto to entity");
        taskService.update(task);
        logger.info("method update(): task(Id=" + taskId + ") updated, redirect to /todos/" + todoId + "/tasks");
        return "redirect:/todos/" + todoId + "/tasks";
    }

    @GetMapping("/{task_id}/delete/todos/{todo_id}")
    public String delete(@PathVariable("task_id") long taskId, @PathVariable("todo_id") long todoId) {
        logger.info("method delete(): get delete request for task(Id=" + taskId + ")");
        taskService.delete(taskId);
        logger.info("method delete(): the task(Id=" + taskId + ") has been deleted");
        return "redirect:/todos/" + todoId + "/tasks";
    }
}
