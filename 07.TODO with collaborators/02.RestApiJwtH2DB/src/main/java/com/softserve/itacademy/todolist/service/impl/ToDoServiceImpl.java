package com.softserve.itacademy.todolist.service.impl;

import com.softserve.itacademy.todolist.dto.request.ToDoRequestDto;
import com.softserve.itacademy.todolist.dto.response.ToDoResponseDto;
import com.softserve.itacademy.todolist.dto.response.UserResponseDto;
import com.softserve.itacademy.todolist.exception.EntityAlreadyExistsException;
import com.softserve.itacademy.todolist.exception.NullEntityReferenceException;
import com.softserve.itacademy.todolist.exception.EntityNotFoundException;
import com.softserve.itacademy.todolist.model.ToDo;
import com.softserve.itacademy.todolist.model.User;
import com.softserve.itacademy.todolist.repository.ToDoRepository;
import com.softserve.itacademy.todolist.repository.UserRepository;
import com.softserve.itacademy.todolist.service.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ToDoServiceImpl implements ToDoService {

    private final ToDoRepository todoRepository;
    private final UserRepository userRepository;

    @Autowired
    public ToDoServiceImpl(ToDoRepository todoRepository, UserRepository userRepository) {
        this.todoRepository = todoRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ToDoResponseDto create(ToDoRequestDto todoDto, long ownerId) {
        if(todoRepository.findByTitle(todoDto.getTitle()).isPresent()) {
            throw new EntityAlreadyExistsException("Todo with this title already exists");
        }
        ToDo newTodo = new ToDo();
        newTodo.setTitle(todoDto.getTitle());
        newTodo.setCreatedAt(LocalDateTime.now());
        Optional<User> owner = userRepository.findById(ownerId);
        if(owner.isEmpty()) {
            throw new EntityNotFoundException("Owner of todo with id=" + ownerId + " is not found");
        }
        newTodo.setOwner(owner.get());
        newTodo.setId(0L);
        return new ToDoResponseDto(todoRepository.save(newTodo));
    }

    @Override
    public ToDoResponseDto readById(long id) {
        ToDo toDo = todoRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("ToDo with id " + id + " not found"));
        return new ToDoResponseDto(toDo);
    }

    @Override
    @Transactional
    public ToDoResponseDto update(ToDoRequestDto todoDto, long todoId, long ownerId) {

        ToDo oldTodo = todoRepository.findById(todoId).orElseThrow(
                () -> new EntityNotFoundException("ToDo with id " + todoId + " not found"));

        if(todoRepository.findByTitle(todoDto.getTitle()).isPresent()
                && !todoDto.getTitle().equals(oldTodo.getTitle())) {
            throw new EntityAlreadyExistsException("Todo with this title already exists");
        }
        oldTodo.setTitle(todoDto.getTitle());
        return new ToDoResponseDto(oldTodo);
    }


    @Override
    public void delete(long id) {
        Optional<ToDo> todo = todoRepository.findById(id);
        if(todo.isEmpty()) {
            throw new EntityNotFoundException("ToDo with id " + id + " not found");
        }
        todoRepository.delete(todo.get());
    }

    @Override
    public List<ToDoResponseDto> getAll() {
        return todoRepository.findAll()
                .stream()
                .map(ToDoResponseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<ToDoResponseDto> getByUserId(long userId) {
        return todoRepository.getByUserId(userId)
                .stream()
                .map(ToDoResponseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserResponseDto> getToDoCollaborators(long todoId) {
        ToDo todo = todoRepository.findById(todoId).orElseThrow(
                () -> new EntityNotFoundException("ToDo with id " + todoId + " not found"));

        return todo.getCollaborators()
                .stream()
                .map(UserResponseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void addCollaborator(long todoId, long userId) {
        ToDo todo = todoRepository.findById(todoId).orElseThrow(
                () -> new EntityNotFoundException("ToDo with id " + todoId + " not found"));

        User user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("User with id " + userId + " not found"));

        List<User> collaborators = todo.getCollaborators();
        if(collaborators.contains(user)) {
            throw new EntityAlreadyExistsException("User with id " + userId + " already is a collaborator for this todo");
        }
        collaborators.add(user);
    }


    @Override
    @Transactional
    public void removeCollaborator(long todoId, long userId) {
        ToDo todo = todoRepository.findById(todoId).orElseThrow(
                () -> new EntityNotFoundException("ToDo with id " + todoId + " not found"));

        User user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("User with id " + userId + " not found"));

        List<User> collaborators = todo.getCollaborators();
        if(!collaborators.contains(user)) {
            throw new EntityNotFoundException("User with id " + userId + " is not a collaborator for this todo");
        }
        collaborators.remove(user);
    }

}
