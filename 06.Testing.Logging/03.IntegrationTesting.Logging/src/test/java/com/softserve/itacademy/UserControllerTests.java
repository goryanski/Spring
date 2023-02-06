package com.softserve.itacademy;

import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
public class UserControllerTests {

    public static MultiValueMap<String, String> userMap = new LinkedMultiValueMap<>();
    public static MultiValueMap<String, String> invalidUserMap = new LinkedMultiValueMap<>();
    private static UserService userService;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    public UserControllerTests(UserService userService) {
        UserControllerTests.userService = userService;
    }

    @BeforeAll
    public static void config() {
        userMap.add("firstName", "Mykhailo");
        userMap.add("lastName", "Kolotailo");
        userMap.add("password", "coolPassword1");
        userMap.add("email", "kolotailo@mail.com");

        invalidUserMap.add("firstName", "mykhailo");
        invalidUserMap.add("lastName", "kolotailo");
        invalidUserMap.add("password", "coolpassword");
        invalidUserMap.add("email", "kolotailo#mail.com");

    }

    @Test
    public void getPageCreateUserTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/create"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void createUserTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/users/create")
                        .params(userMap))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());

    }

    @Test
    public void createInvalidUserTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/users/create")
                        .params(invalidUserMap))
                // if status is 200, user won't be created (because of validation)
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void readByUserIdTest() throws Exception {
        User user = userService.readById(4);
        mockMvc.perform(MockMvcRequestBuilders.get("/users/" + 4 + "/read"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("user"))
                .andExpect(MockMvcResultMatchers.model().attribute("user", user));
    }

    @Test
    public void getPageUpdateUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/" + 4 + "/update"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void errorUpdateUserTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/users/" + 4 + "/update")
                        .param("roleId", "1")
                        .params(invalidUserMap))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void tryingUpdateWithUserRoleTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/users/" + 6 + "/update")
                        .params(userMap)
                        .param("roleId", "1"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    public void updateUserTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/users/" + 4 + "/update")
                        .param("firstName", "Petro")
                        .param("lastName", "Shevchenko")
                        .param("password", "oldPassword1")
                        .param("roleId", "1")
                        .param("email", "mike@mail.com"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    public void deleteUserByIdTest() throws Exception {
        long userDeleteId = 5;
        mockMvc.perform(MockMvcRequestBuilders.get("/users/" + userDeleteId + "/delete/"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    public void getAllUsersTest() throws Exception {
        List<User> users = userService.getAll();

        mockMvc.perform(MockMvcRequestBuilders.get("/users/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("users"))
                .andExpect(MockMvcResultMatchers.model().attribute("users", users));
    }
}