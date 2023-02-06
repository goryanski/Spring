package com.softserve.itacademy.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.softserve.itacademy.service.UserService;
import com.softserve.itacademy.utils.AppValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softserve.itacademy.model.Task;
import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.TaskService;
import com.softserve.itacademy.service.ToDoService;

@Service
public class  TaskServiceImpl implements TaskService {
    private ToDoService toDoService;
    private AppValidator validator;
    private UserService userService;

    @Autowired
    public TaskServiceImpl(ToDoService toDoService, AppValidator validator, UserService userService) {
        this.toDoService = toDoService;
        this.validator = validator;
        this.userService = userService;
    }

    public Task addTask(Task task, ToDo todo) {
        ToDo userTodo = toDoService.getByUserTitle(todo.getOwner(), todo.getTitle());
        if(userTodo != null && task != null && validator.isTaskValid(task)) {
            userTodo.addTask(task);
            return task;
        }
        return null;
    }

    public Task updateTask(Task taskToUpdate) {
        if(taskToUpdate != null && taskToUpdate.getName() != null) {
            for(ToDo toDo : toDoService.getAll()) {
                for(Task task : toDo.getTasks()) {
                    if(task.getName().equals(taskToUpdate.getName())) {
                        task.setPriority(taskToUpdate.getPriority());
                        return taskToUpdate;
                    }
                }
            }
        }
        return null;
    }

    public void deleteTask(Task taskToDelete) {
        if(taskToDelete != null) {
            ToDo foundTodo = null;
            for(ToDo toDo : toDoService.getAll()) {
                for(Task task : toDo.getTasks()) {
                    if(task.getName().equals(taskToDelete.getName())) {
                        foundTodo = toDo;
                    }
                }
            }
            if(foundTodo != null) {
                foundTodo.getTasks().remove(taskToDelete);
            }
        }
    }

    public List<Task> getAll() {
        List<Task> tasks = new ArrayList<>();
        for(ToDo toDo : toDoService.getAll()) {
            tasks.addAll(toDo.getTasks());
        }
       return tasks;
    }

    public List<Task> getByToDo(ToDo todo) {
        if(todo != null && toDoService.getAll().contains(todo)) {
            return todo.getTasks();
        }
        return null;
    }

    public Task getByToDoName(ToDo todo, String name) {
        if(todo != null && name != null && toDoService.getAll().contains(todo)) {
            return todo.getTasks()
                    .stream()
                    .filter(t -> t.getName().equals(name))
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }

    public Task getByUserName(User user, String name) {
        if(user != null && name != null) {
            User foundUser = userService.readUser(user.getEmail());
            if(foundUser != null) {
                for(ToDo toDo : foundUser.getTodos()) {
                    for(Task task : toDo.getTasks()) {
                        if(task.getName().equals(name)) {
                            return task;
                        }
                    }
                }
            }
        }
        return null;
    }

}
