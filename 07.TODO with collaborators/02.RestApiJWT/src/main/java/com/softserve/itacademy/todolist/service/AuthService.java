package com.softserve.itacademy.todolist.service;


import com.softserve.itacademy.todolist.dto.UserDto;
import com.softserve.itacademy.todolist.dto.auth.JwtResponseDTO;

public interface AuthService {
    public JwtResponseDTO getJwtErrorResponse(String username);
    public JwtResponseDTO getJwtSuccessResponse(String username, String token);
    public void registerPerson(UserDto registerPerson);
}
