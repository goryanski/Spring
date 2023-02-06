package com.softserve.itacademy.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.softserve.itacademy.utils.AppValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private List<User> users;
    AppValidator validator;

    @Autowired
    public UserServiceImpl(AppValidator validator) {
        users = new ArrayList<>();
        this.validator = validator;
    }

    @Override
    public User addUser(User newUser) {
        Optional<User> foundUser = getUserByEmail(newUser.getEmail());
        if(foundUser.isPresent() || !validator.isUserValid(newUser)) {
           return null;
        }
        users.add(newUser);
        return newUser;
    }

    @Override
    public User updateUser(User newUser) {
        Optional<User> selectedUser = getUserByEmail(newUser.getEmail());
        if(!selectedUser.isPresent() || !validator.isUserValid(newUser)) {
            return null;
        }
        selectedUser.get().setFirstName(newUser.getFirstName());
        selectedUser.get().setLastName(newUser.getLastName());
        selectedUser.get().setPassword(newUser.getPassword());
        selectedUser.get().setTodos(newUser.getTodos());
        return newUser;
    }

    @Override
    public void deleteUser(User user) {
        users.remove(user);
    }

    @Override
    public List<User> getAll() {
        return users;
    }

    @Override
    public User readUser(String email) {
        return getUserByEmail(email).orElse(null);
    }

    @Override
    public void clearAll() {
        this.users.clear();
    }

    public Optional<User> getUserByEmail(String email) {
        return users.stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }
}
