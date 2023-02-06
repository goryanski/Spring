package com.softserve.itacademy.controller;

import com.softserve.itacademy.exception.AccessDeniedException;
import com.softserve.itacademy.security.PersonDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/get_home_page")
    public String getHomePage() {
        // get authentication user to check his role and decide what page we have to show him
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();

        if(personDetails.getPerson().getRole().getName().equals("ROLE_USER")) {
            return "redirect:/todos/all/users/" + personDetails.getPerson().getId();
        }
        // for admin
        return "redirect:/home";
    }

    @GetMapping("/error/forbidden")
    public String forbiddenError() {
        throw new AccessDeniedException("You cannot access this page");
    }
}
