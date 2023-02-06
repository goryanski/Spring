package com.softserve.itacademy.todolist.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.softserve.itacademy.todolist.model.User;
import lombok.Value;
import org.springframework.data.mapping.model.PropertyNameFieldNamingStrategy;

@Value
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserResponseDto {
    Long id;
    String firstName;
    String lastName;
    String email;
    String role;

    public UserResponseDto(User user) {
        id = user.getId();
        firstName = user.getFirstName();
        lastName = user.getLastName();
        email = user.getEmail();
        role = user.getRole().getName();
    }
}
