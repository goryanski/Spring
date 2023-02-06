package com.softserve.itacademy;

import com.softserve.itacademy.model.*;
import com.softserve.itacademy.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootApplication
@AllArgsConstructor
public class ToDoListApplication implements CommandLineRunner {

    UserRepository userRepository;
    RoleRepository roleRepository;
    ToDoRepository toDoRepository;
    TaskRepository taskRepository;
    StateRepository stateRepository;

    public static void main(String[] args) {
        SpringApplication.run(ToDoListApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Running Spring Boot Application");

        Role role = roleRepository.getOne(2L);
        User validUser  = new User();
        validUser.setEmail("valid@cv.edu.ua");
        validUser.setFirstName("Valid-Name");
        validUser.setLastName("Valid-Name");
        validUser.setPassword("qwQW12!@");
        validUser.setRole(role);
        validUser = userRepository.save(validUser);

        ToDo toDo = new ToDo();
        toDo.setTitle("Other");
        toDo.setOwner(validUser);
        toDo.setCreatedAt(LocalDateTime.now());
        toDo = toDoRepository.save(toDo);



        Role role1 = new Role();
        role1.setName("New role");
        role1 = roleRepository.save(role1);

        State state = new State();
        state.setName("New state");
        state = stateRepository.save(state);

        Task task = new Task();
        task.setName("New task");
        task.setPriority(Priority.MEDIUM.name());
        task.setState(state);
        task.setTodo(toDo);
        task = taskRepository.save(task);



        LocalDate localDate = toDo.getCreatedAt().toLocalDate();
        LocalDate today = LocalDate.now();
        System.out.println(localDate.equals(today));

    }
}

