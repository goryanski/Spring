package app.EasyFoodAPI.controllers;

import app.EasyFoodAPI.security.PersonDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin()
@RequestMapping("/api/easyFood/user")
public class UsersController {

    @GetMapping("/get-info")
    public Map<String, String> getInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();

        Map<String, String> response = new HashMap<>();
        response.put("username", personDetails.getUsername());
        response.put("role", personDetails.getAccount().getRole().getName());
        response.put("email", personDetails.getAccount().getPerson().getEmail());

        return response;
    }
}