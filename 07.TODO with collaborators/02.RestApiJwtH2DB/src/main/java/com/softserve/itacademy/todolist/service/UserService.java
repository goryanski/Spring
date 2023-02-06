package com.softserve.itacademy.todolist.service;

import com.softserve.itacademy.todolist.dto.UserDto;
import com.softserve.itacademy.todolist.dto.response.UserResponseDto;
import com.softserve.itacademy.todolist.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    User create(User user);
    UserResponseDto readById(long id);
    UserResponseDto update(UserDto user, long user_id);
    void delete(long id);
    List<User> getAll();

    List<UserResponseDto> getPossibleCollaborators(long todoId);
    UserResponseDto singUpUser(UserDto userDto);
}
