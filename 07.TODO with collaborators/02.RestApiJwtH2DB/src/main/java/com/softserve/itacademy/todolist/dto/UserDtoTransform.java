package com.softserve.itacademy.todolist.dto;


import com.softserve.itacademy.todolist.model.User;

public class UserDtoTransform {

    public static User convertToEntity(UserDto userCreateDto) {
        User newUser = new User();
        newUser.setFirstName(userCreateDto.getFirstName());
        newUser.setLastName(userCreateDto.getLastName());
        newUser.setEmail(userCreateDto.getEmail());
        newUser.setPassword(userCreateDto.getPassword());
        return newUser;
    }

//    public static UserDto convertToDto(User user) {
//        return new UserDto(
//                user.getFirstName(),
//                user.getLastName(),
//                user.getEmail(),
//                user.getPassword(),
//                user.getRole()
//        );
//    }
}
