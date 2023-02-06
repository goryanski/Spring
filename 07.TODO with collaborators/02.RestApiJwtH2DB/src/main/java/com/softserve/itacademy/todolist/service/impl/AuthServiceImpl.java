package com.softserve.itacademy.todolist.service.impl;

import com.softserve.itacademy.todolist.dto.UserDto;
import com.softserve.itacademy.todolist.dto.auth.JwtResponseDTO;
import com.softserve.itacademy.todolist.model.User;
import com.softserve.itacademy.todolist.repository.UserRepository;
import com.softserve.itacademy.todolist.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public JwtResponseDTO getJwtErrorResponse(String username) {
        Optional<User> user = userRepository.findByEmail(username);
        String errorMessage = user.isPresent()
                ? "Incorrect password"
                : "User not found";
        return new JwtResponseDTO("none", errorMessage, "none", -1);
    }

    @Override
    public JwtResponseDTO getJwtSuccessResponse(String username, String token) {
        // we already checked that account with this username really exists
        User user = userRepository.findByEmail(username).get();
        return new JwtResponseDTO(
                token,
                "none",
                user.getRole().getName(),
                user.getId()
        );
    }

    @Override
    public void registerPerson(UserDto registerPerson) {

    }
}
