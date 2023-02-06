package com.softserve.itacademy.controller;

import com.softserve.itacademy.model.Task;
import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.TaskService;
import com.softserve.itacademy.service.ToDoService;
import com.softserve.itacademy.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/todos")
public class ToDoController {
    private final ToDoService todoService;
    private final TaskService taskService;
    private final UserService userService;

    Logger logger = LoggerFactory.getLogger(ToDoController.class);

    public ToDoController(ToDoService todoService, TaskService taskService, UserService userService) {
        this.todoService = todoService;
        this.taskService = taskService;
        this.userService = userService;
    }

    @GetMapping("/create/users/{owner_id}")
    public String create(@PathVariable("owner_id") long ownerId, Model model) {
        logger.info("method create(): get create ToDo page");
        model.addAttribute("todo", new ToDo());
        model.addAttribute("ownerId", ownerId);
        return "create-todo";
    }

    @PostMapping("/create/users/{owner_id}")
    public String create(@PathVariable("owner_id") long ownerId, @Validated @ModelAttribute("todo") ToDo todo, BindingResult result) {
        logger.info("method create(): create ToDo");
        if (result.hasErrors()) {
            logger.error("method create(): validation errors while creating a new ToDo");
            return "create-todo";
        }
        logger.info("method create(): there are no validation errors while creating a new ToDo");
        todo.setCreatedAt(LocalDateTime.now());
        todo.setOwner(userService.readById(ownerId));

        logger.info("method create(): a new ToDo model is ready for todoService");
        todoService.create(todo);
        return "redirect:/todos/all/users/" + ownerId;
    }

    @GetMapping("/{id}/tasks")
    public String read(@PathVariable long id, Model model) {
        logger.info("method read(): read ToDo");
        ToDo todo = todoService.readById(id);
        List<Task> tasks = taskService.getByTodoId(id);
        List<User> users = userService.getAll().stream()
                .filter(user -> user.getId() != todo.getOwner().getId()).collect(Collectors.toList());
        logger.info("method read(): all data is received from services and ready to go to the view");

        model.addAttribute("todo", todo);
        model.addAttribute("tasks", tasks);
        model.addAttribute("users", users);
        return "todo-tasks";
    }

    @GetMapping("/{todo_id}/update/users/{owner_id}")
    public String update(@PathVariable("todo_id") long todoId, @PathVariable("owner_id") long ownerId, Model model) {
        logger.info("method update(): get update ToDo page");
        ToDo todo = todoService.readById(todoId);
        logger.info("method update(): todo entity is received from todoService and ready to go to the view");

        model.addAttribute("todo", todo);
        return "update-todo";
    }

    @PostMapping("/{todo_id}/update/users/{owner_id}")
    public String update(@PathVariable("todo_id") long todoId, @PathVariable("owner_id") long ownerId,
                         @Validated @ModelAttribute("todo") ToDo todo, BindingResult result) {
        logger.info("method update(): update ToDo");
        if (result.hasErrors()) {
            logger.error("method update(): validation errors while updating a ToDo");
            todo.setOwner(userService.readById(ownerId));
            return "update-todo";
        }
        logger.info("method update(): there are no validation errors while updating a ToDo");
        ToDo oldTodo = todoService.readById(todoId);
        todo.setOwner(oldTodo.getOwner());
        todo.setCollaborators(oldTodo.getCollaborators());
        todo.setCreatedAt(oldTodo.getCreatedAt());
        todo.setTasks(oldTodo.getTasks());
        logger.info("method update(): ToDo entity is found, filled and ready for todoService to update");

        todoService.update(todo);
        return "redirect:/todos/all/users/" + ownerId;
    }

    @GetMapping("/{todo_id}/delete/users/{owner_id}")
    public String delete(@PathVariable("todo_id") long todoId, @PathVariable("owner_id") long ownerId) {
        logger.info("method delete(): delete ToDo");
        todoService.delete(todoId);
        return "redirect:/todos/all/users/" + ownerId;
    }

    @GetMapping("/all/users/{user_id}")
    public String getAll(@PathVariable("user_id") long userId, Model model) {
        logger.info("method getAll(): get all ToDos");
        List<ToDo> todos = todoService.getByUserId(userId);
        logger.info("method getAll(): all ToDos are found");
        model.addAttribute("todos", todos);
        logger.info("method getAll(): finding user by id");
        model.addAttribute("user", userService.readById(userId));
        return "todos-user";
    }

    @GetMapping("/{id}/add")
    public String addCollaborator(@PathVariable long id, @RequestParam("user_id") long userId) {
        logger.info("method addCollaborator(): add Collaborator to ToDo");
        ToDo todo = todoService.readById(id);

        logger.info("method addCollaborator(): selected ToDo was found by id");
        List<User> collaborators = todo.getCollaborators();
        collaborators.add(userService.readById(userId));
        todo.setCollaborators(collaborators);

        logger.info("method addCollaborator(): a new collaborator was added to ToDo model and this model is ready for updating in todoService");
        todoService.update(todo);
        return "redirect:/todos/" + id + "/tasks";
    }

    @GetMapping("/{id}/remove")
    public String removeCollaborator(@PathVariable long id, @RequestParam("user_id") long userId) {
        logger.info("method removeCollaborator(): remove Collaborator from ToDo");
        ToDo todo = todoService.readById(id);
        logger.info("method removeCollaborator(): selected ToDo was found by id");

        List<User> collaborators = todo.getCollaborators();
        collaborators.remove(userService.readById(userId));
        todo.setCollaborators(collaborators);

        logger.info("method removeCollaborator(): collaborator was removed from ToDo model and this model is ready for updating in the todoService");
        todoService.update(todo);
        return "redirect:/todos/" + id + "/tasks";
    }
}
