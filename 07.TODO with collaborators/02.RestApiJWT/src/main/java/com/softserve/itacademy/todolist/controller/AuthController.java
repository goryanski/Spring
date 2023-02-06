package com.softserve.itacademy.todolist.controller;

import com.softserve.itacademy.todolist.dto.auth.JwtResponseDTO;
import com.softserve.itacademy.todolist.dto.auth.LoginRequestDTO;
import com.softserve.itacademy.todolist.exception.AccessDeniedException;
import com.softserve.itacademy.todolist.security.JwtTokenProvider;
import com.softserve.itacademy.todolist.service.AuthService;
import com.softserve.itacademy.todolist.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final RoleService roleService;
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthController(RoleService roleService, AuthService authService, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.roleService = roleService;
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }


    @PostMapping("/login")
    public JwtResponseDTO signIn(@RequestBody LoginRequestDTO authenticationDTO) {
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(
                        authenticationDTO.getUsername(),
                        authenticationDTO.getPassword());

        try {
            authenticationManager.authenticate(authInputToken);
        } catch (BadCredentialsException e) {
            // Incorrect username or password. find out what's incorrect exactly and return error:
            return authService.getJwtErrorResponse(authenticationDTO.getUsername());
        }

        String token = jwtTokenProvider.generateToken(authenticationDTO.getUsername());
        return authService.getJwtSuccessResponse(authenticationDTO.getUsername(), token);
    }





    @GetMapping("/error/forbidden")
    public String forbiddenError() {
        throw new AccessDeniedException("You cannot access this page");
    }


//    @PatchMapping("/change_roles")
//    public Map<String, Object> changeRoles() {
//        roleService.changeRoles();
//        Map<String, Object> response = new HashMap<>();
//        response.put("status", HttpStatus.OK.toString());
//        return response;
//    }
}
