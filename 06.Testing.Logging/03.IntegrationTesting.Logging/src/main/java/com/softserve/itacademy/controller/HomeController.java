package com.softserve.itacademy.controller;

import com.softserve.itacademy.service.RoleService;
import com.softserve.itacademy.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class HomeController {
    private final UserService userService;
    Logger logger = LoggerFactory.getLogger(ToDoController.class);

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping({"/", "home"})
    public String home(Model model) {
        logger.info("method home(): get all users");
        model.addAttribute("users", userService.getAll());
        logger.info("method home(): all users is received from userService and added to Model attributes");
        return "home";
    }
}
