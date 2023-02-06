package com.softserve.itacademy.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.softserve.itacademy.utils.AppValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.ToDoService;
import com.softserve.itacademy.service.UserService;

@Service
public class ToDoServiceImpl implements ToDoService {

    private UserService userService;
    private AppValidator validator;

    @Autowired
    public ToDoServiceImpl(UserService userService, AppValidator validator) {
        this.userService = userService;
        this.validator = validator;
    }

    public ToDo addTodo(ToDo todo, User user) {
        User foundUser = userService.readUser(user.getEmail());
        if(foundUser == null
                || !validator.isToDoValid(todo)
                || !todo.getOwner().equals(user)) {
            return null;
        }

        foundUser.addTodo(todo);
        return todo;
    }

    public ToDo updateTodo(ToDo todoToUpdate) {
        User todoOwner = todoToUpdate.getOwner();
        if(todoOwner != null) {
            ToDo selectedTodo = todoOwner.getTodos().stream()
                    .filter(todo -> todo.getTitle().equals(todoToUpdate.getTitle()))
                    .findFirst()
                    .orElse(null);
            if(selectedTodo != null) {
                selectedTodo.setTasks(todoToUpdate.getTasks());
                selectedTodo.setCreatedAt(todoToUpdate.getCreatedAt());
                return todoToUpdate;
            }
        }
        return null;
    }

    public void deleteTodo(ToDo todoToDelete) {
       if(todoToDelete != null) {
           User user = todoToDelete.getOwner();
           user.getTodos().remove(todoToDelete);
       }
    }

    public List<ToDo> getAll() {
        List<ToDo> todos = new ArrayList<>();
        for(User user : userService.getAll()) {
            todos.addAll(user.getTodos());
        }
        return todos;
    }

    public List<ToDo> getByUser(User user) {
        User foundUser = userService.readUser(user.getEmail());
        return foundUser != null ? foundUser.getTodos() : null;
    }

    public ToDo getByUserTitle(User user, String title) {
        User foundUser = userService.readUser(user.getEmail());
        if(foundUser != null) {
            return foundUser.getTodos().stream()
                    .filter(todo -> todo.getTitle().equals(title))
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }
}
