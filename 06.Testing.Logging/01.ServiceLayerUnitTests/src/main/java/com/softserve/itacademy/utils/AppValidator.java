package com.softserve.itacademy.utils;
import com.softserve.itacademy.model.Task;
import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.model.User;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AppValidator {

    private final String userNamePattern = "^[A-Z]{1}[a-z]{2,20}$";
    private final String emailPattern = "^^[\\w\\-\\.0-9]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    private final String passwordPattern = "^[A-Za-z0-9!;.]{4,18}$";
    private final String toDoNamePattern = "^[a-zA-Z0-9]+$";
    private final String taskNamePattern = "^[a-zA-Z0-9]{2,30}$";

    public static AppValidator getValidator() {
        return new AppValidator();
    }

    public boolean isUserValid(User user) {
       return user.getFirstName().matches(userNamePattern)
                && user.getLastName().matches(userNamePattern)
                && user.getEmail().matches(emailPattern)
                && user.getPassword().matches(passwordPattern);
    }

    public boolean isToDoValid(ToDo todo){
        return todo.getTitle().matches(toDoNamePattern);
    }

    public boolean isTaskValid(Task task) {
        return task.getName().matches(taskNamePattern);
    }
}
