package com.softserve.itacademy.todolist.service.impl;

import com.softserve.itacademy.todolist.dto.UserDto;
import com.softserve.itacademy.todolist.dto.response.UserResponseDto;
import com.softserve.itacademy.todolist.exception.EntityAlreadyExistsException;
import com.softserve.itacademy.todolist.exception.EntityNotFoundException;
import com.softserve.itacademy.todolist.exception.NullEntityReferenceException;
import com.softserve.itacademy.todolist.model.ToDo;
import com.softserve.itacademy.todolist.model.User;
import com.softserve.itacademy.todolist.repository.RoleRepository;
import com.softserve.itacademy.todolist.repository.ToDoRepository;
import com.softserve.itacademy.todolist.repository.UserRepository;
import com.softserve.itacademy.todolist.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.softserve.itacademy.todolist.dto.UserDtoTransform.convertToEntity;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ToDoRepository toDoRepository;
    private final RoleRepository roleRepository;


    @Override
    public User create(User role) {
        if (role != null) {
            return userRepository.save(role);
        }
        throw new NullEntityReferenceException("User cannot be 'null'");
    }

    @Override
    public UserResponseDto singUpUser(UserDto userDto) {
        if (getByEmail(userDto.getEmail()) != null) {
            throw new EntityAlreadyExistsException("User with this email already exist");
        }
        User newUser = convertToEntity(userDto);
        newUser.setRole(roleRepository.findByName(userDto.getRole())
                .orElseThrow(() -> new EntityNotFoundException("Role not found")));
        newUser.setId(10L);
        return new UserResponseDto(userRepository.save(newUser));
    }

    public User getByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new EntityNotFoundException("User with email " + email + " not found"));
    }

    @Override
    public UserResponseDto readById(long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User with id " + id + " not found"));

        return new UserResponseDto(user);
    }


    public UserResponseDto update(UserDto userDto, long user_id) {
        if (userDto != null) {
            if (getByEmail(userDto.getEmail()) != null) {
                throw new EntityAlreadyExistsException("User with this email already exist");
            }
            User oldUser = userRepository.findById(user_id).orElseThrow(
                    () -> new EntityNotFoundException("User with id " + user_id + " not found"));
            oldUser.setFirstName(userDto.getFirstName());
            oldUser.setLastName(userDto.getLastName());
            oldUser.setPassword(userDto.getPassword());
            oldUser.setEmail(userDto.getEmail());
            oldUser.setRole(roleRepository.findByName(userDto.getRole())
                    .orElseThrow(() -> new EntityNotFoundException("Role not found")));
            userRepository.save(oldUser);
            return new UserResponseDto(oldUser);
        }
        throw new NullEntityReferenceException("User cannot be 'null'");
    }


    @Override
    public void delete(long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()) {
            throw new EntityNotFoundException("User not Found!");
        }
        userRepository.delete(user.get());
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public List<UserResponseDto> getPossibleCollaborators(long todoId) {
        ToDo todo = toDoRepository.findById(todoId).orElseThrow(
                () -> new EntityNotFoundException("ToDo with id " + todoId + " not found"));

        return userRepository.findAll()
                .stream()
                .filter(user -> user.getId() != todo.getOwner().getId())
                .map(UserResponseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> user = userRepository.findByEmail(username);
        if (user.isEmpty()) {
            throw new EntityNotFoundException("User not Found!");
        }
        return user.get();
    }
}
