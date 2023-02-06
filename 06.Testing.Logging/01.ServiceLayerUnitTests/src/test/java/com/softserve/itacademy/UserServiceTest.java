package com.softserve.itacademy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.UserService;

import java.util.ArrayList;
import java.util.List;

@RunWith(JUnitPlatform.class)
public class UserServiceTest {
    private static UserService userService;

    @BeforeAll
    public static void setupBeforeClass() throws Exception {
        AnnotationConfigApplicationContext annotationConfigContext = new AnnotationConfigApplicationContext(Config.class);
        userService = annotationConfigContext.getBean(UserService.class);
        annotationConfigContext.close();
    }

    @Test
    public void checkAddValidUser() {
        User user = new User("Igor", "Ivanov", "igorok2082@gmail.com", "1111");
        User expected = user;
        User actual = userService.addUser(user);
        Assertions.assertEquals(expected, actual, "User not added (User with this email already exists or user is not valid)");
    }

    @Test
    public void checkAddInvalidUser() {
        // incorrect firstname
        User user = new User("Igor12!", "Ivanov", "igorok2081@gmail.com", "1111");
        User expected = null;
        User actual = userService.addUser(user);
        Assertions.assertEquals(expected, actual, "User with invalid data must not be added to users list");
    }

    @Test
    public void checkAddExistingUser() {
        User user1 = new User("Igor", "Ivanov", "igorok208@gmail.com", "1111");
        userService.addUser(user1);
        // user with this email already exists
        User user2 = new User("Tanya", "Ivanova", "igorok208@gmail.com", "2222");
        User expected = null;
        User actual = userService.addUser(user2);
        Assertions.assertEquals(expected, actual, "If user already exists, he must not be added to users list again");
    }

    @Test
    public void checkAddNullUser() {
        try {
            userService.addUser(null);
            Assertions.fail("Null cannot be added");
        } catch (NullPointerException e) {
            Assertions.assertTrue(true);
        }
    }

    @Test
    public void checkUpdateValidUser() {
        User user = new User("Igor", "Ivanov", "igorok2083@gmail.com", "1111");
        userService.addUser(user);
        User userToUpdate = new User("Igor", "Popov", "igorok2083@gmail.com", "2222");
        User expected = userToUpdate;
        userService.updateUser(userToUpdate);
        User actual = userService.readUser(user.getEmail());
        Assertions.assertEquals(expected, actual, "User not updated (User with this email doesn't exist or user is not valid)");
        Assertions.assertEquals(actual.getLastName(), expected.getLastName(), "Lastname is not updated");
        Assertions.assertEquals(actual.getPassword(), expected.getPassword(), "Password is not updated");
    }

    @Test
    public void checkUpdateInvalidUser() {
        User user = new User("Igor", "Ivanov", "igorok2084@gmail.com", "1111");
        userService.addUser(user);
        // incorrect lastname
        User userToUpdate = new User("Igor", "Popov12!", "igorok2084@gmail.com", "2222");
        User expected = user;
        userService.updateUser(userToUpdate);
        User actual = userService.readUser(user.getEmail());
        Assertions.assertEquals(expected, actual, "User not updated (User with this email doesn't exist or user is not valid)");
        Assertions.assertEquals(actual.getLastName(), expected.getLastName(), "Invalid lastname must not be updated");
    }

    @Test
    public void checkUpdateNotExistingUser() {
        User user = new User("Igor", "Ivanov", "igorok2085@gmail.com", "1111");
        userService.addUser(user);
        // user with this email does not exist
        User userToUpdate = new User("Igor", "Popov", "igorok2086@gmail.com", "2222");
        User expected = null;
        User actual = userService.updateUser(userToUpdate);
        Assertions.assertEquals(expected, actual, "User not updated (User with this email doesn't exist or user is not valid)");
    }

    @Test
    public void checkUpdateNullUser() {
        try {
            userService.updateUser(null);
            Assertions.fail("Null cannot be updated");
        } catch (NullPointerException e) {
            Assertions.assertTrue(true);
        }
    }

    @Test
    public void checkDeleteUser() {
        User user = new User("Igor", "Ivanov", "igorok2087@gmail.com", "1111");
        userService.addUser(user);
        User expected = null;
        userService.deleteUser(user);
        User actual = userService.readUser(user.getEmail());
        Assertions.assertEquals(expected, actual, "User was not remover");
    }

    @Test
    public void checkDeleteNullUser() {
        User user = new User("Igor", "Ivanov", "igorok1234@gmail.com", "1111");
        userService.addUser(user);
        User expected = user;
        userService.deleteUser(null);
        User actual = userService.readUser(user.getEmail());
        Assertions.assertEquals(expected, actual, "Cannot remove a user if user is null");
    }

    @Test
    public void checkGetAllUsers() {
        userService.clearAll();
        User user1 = new User("Igor", "Ivanov", "igorok2087@gmail.com", "1111");
        User user2 = new User("Masha", "Ivanova", "igorok2088@gmail.com", "1111");
        User user3 = new User("Vasil", "Vasin", "igorok2089@gmail.com", "1111");
        userService.addUser(user1);
        userService.addUser(user2);
        userService.addUser(user3);

        List<User> expected = new ArrayList<>();
        expected.add(user1);
        expected.add(user2);
        expected.add(user3);

        List<User> actual = userService.getAll();
        Assertions.assertEquals(expected, actual, "Can't get all users from userService");
    }

    @Test
    public void checkReadUser() {
        User user = new User("Igor", "Ivanov", "igorok20871@gmail.com", "1111");
        userService.addUser(user);
        User expected = user;
        User actual = userService.readUser(user.getEmail());
        Assertions.assertEquals(expected, actual, "Can't read user by email");
    }
}
