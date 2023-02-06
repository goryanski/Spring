package com.softserve.itacademy.controller;

import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.RoleService;
import com.softserve.itacademy.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/create")
    public String create(Model model) {
        logger.info("method create(): get create User page");
        model.addAttribute("user", new User());
        return "create-user";
    }

    @PostMapping("/create")
    public String create(@Validated @ModelAttribute("user") User user, BindingResult result) {
        logger.info("method create(): create User");
        if (result.hasErrors()) {
            logger.error("method create(): validation errors while creating a new User");
            return "create-user";
        }
        logger.info("method create(): there are no validation errors while creating a new User");
        user.setPassword(user.getPassword());
        user.setRole(roleService.readById(2));
        logger.info("method create(): a new User model is ready for userService");
        User newUser = userService.create(user);
        return "redirect:/todos/all/users/" + newUser.getId();
    }

    @GetMapping("/{id}/read")
    public String read(@PathVariable long id, Model model) {
        logger.info("method read(): read User");
        User user = userService.readById(id);
        model.addAttribute("user", user);
        return "user-info";
    }

    @GetMapping("/{id}/update")
    public String update(@PathVariable long id, Model model) {
        logger.info("method update(): get update User page");
        User user = userService.readById(id);
        logger.info("method update(): User entity is received from userService and ready to go to the view");
        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.getAll());
        return "update-user";
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable long id, @Validated @ModelAttribute("user") User user, BindingResult result, @RequestParam("roleId") long roleId, Model model) {
        logger.info("method update(): update User");
        User oldUser = userService.readById(id);
        if (result.hasErrors()) {
            logger.error("method update(): validation errors while updating a User");
            user.setRole(oldUser.getRole());
            model.addAttribute("roles", roleService.getAll());
            return "update-user";
        }
        if (oldUser.getRole().getName().equals("USER")) {
            logger.info("method update(): validation if ADMIN trying change the User role");
            user.setRole(oldUser.getRole());
        } else {
            logger.info("method update(): there are no validation errors while updating a User");
            user.setRole(roleService.readById(roleId));
        }
        logger.info("method update(): User entity is found, filled and ready for userService to update");
        userService.update(user);
        return "redirect:/users/" + id + "/read";
    }


    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("id") long id) {
        logger.info("method delete(): delete User");
        userService.delete(id);
        return "redirect:/users/all";
    }

    @GetMapping("/all")
    public String getAll(Model model) {
        logger.info("method getAll(): get all Users");
        model.addAttribute("users", userService.getAll());
        return "users-list";
    }
}
