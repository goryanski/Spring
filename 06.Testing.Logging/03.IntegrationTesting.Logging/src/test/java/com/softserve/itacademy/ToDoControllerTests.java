package com.softserve.itacademy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softserve.itacademy.model.Task;
import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.TaskService;
import com.softserve.itacademy.service.ToDoService;
import com.softserve.itacademy.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class ToDoControllerTests {
    @Autowired
    private MockMvc mockMvc;

    private final ToDoService todoService;
    private final TaskService taskService;
    private final UserService userService;

    private final long testUserId = 5;
    private final long testTodoId = 7;

    @Autowired
    public ToDoControllerTests(ToDoService todoService, TaskService taskService, UserService userService) {
        this.todoService = todoService;
        this.taskService = taskService;
        this.userService = userService;
    }


    @Test
    public void getPageCreateToDoTest() throws Exception {
        ToDo emptyToDo =  new ToDo();
        long ownerId = testUserId;

        mockMvc.perform(MockMvcRequestBuilders.get("/todos/create/users/" + testUserId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("todo"))
                .andExpect(MockMvcResultMatchers.model().attribute("todo", emptyToDo))
                .andExpect(MockMvcResultMatchers.model().attributeExists("ownerId"))
                .andExpect(MockMvcResultMatchers.model().attribute("ownerId", ownerId));
    }

    @Test
    public void createToDoTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/todos/create/users/" + testUserId)
                        .param("title", "New best title"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());

        ToDo toDo = todoService.getByTitle("New best title");
        Assertions.assertNotNull(toDo);
    }

    @Test
    public void createToDoWithInvalidTitleTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/todos/create/users/" + testUserId)
                        .param("title", ""))
                // if status is 200, user won't be created (because of validation)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void readToDoTest() throws Exception {
        ToDo todo = todoService.readById(testTodoId);
        List<Task> tasks = taskService.getByTodoId(testTodoId);
        List<User> users = userService.getAll().stream()
                .filter(user -> user.getId() != todo.getOwner().getId()).collect(Collectors.toList());

        mockMvc.perform(MockMvcRequestBuilders.get("/todos/" + testTodoId + "/tasks"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("todo"))
                .andExpect(MockMvcResultMatchers.model().attribute("todo", todo))
                .andExpect(MockMvcResultMatchers.model().attributeExists("tasks"))
                .andExpect(MockMvcResultMatchers.model().attribute("tasks", tasks))
                .andExpect(MockMvcResultMatchers.model().attributeExists("users"))
                .andExpect(MockMvcResultMatchers.model().attribute("users", users));
    }

    @Test
    public void getPageUpdateToDoTest() throws Exception {
        ToDo todo = todoService.readById(testTodoId);

        mockMvc.perform(MockMvcRequestBuilders.get("/todos/" + testTodoId + "/update/users/" + testUserId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("todo"))
                .andExpect(MockMvcResultMatchers.model().attribute("todo", todo));
    }

    @Test
    public void updateToDoTest() throws Exception {
        long toDoIdToUpdate = 8;

        mockMvc.perform(MockMvcRequestBuilders.post("/todos/" + toDoIdToUpdate + "/update/users/" + testUserId)
                        .param("id", String.valueOf(toDoIdToUpdate))
                        .param("title", "Updated title"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());

        Assertions.assertNotNull(todoService.getByTitle("Updated title"));
    }

    @Test
    public void updateToDoWithInvalidTitleTest() throws Exception {
        long toDoIdToUpdate = 8;

        mockMvc.perform(MockMvcRequestBuilders.post("/todos/" + toDoIdToUpdate + "/update/users/" + testUserId)
                        .param("id", String.valueOf(toDoIdToUpdate))
                        .param("title", ""))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void deleteToDoTest() throws Exception {
        long toDoIdToDelete = 9;

        mockMvc.perform(MockMvcRequestBuilders.get("/todos/" + toDoIdToDelete + "/delete/users/" + testUserId))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());

        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            ToDo todo = todoService.readById(toDoIdToDelete);
        });
    }

    @Test
    public void getAllToDosTest() throws Exception {
        List<ToDo> todos = todoService.getByUserId(testUserId);
        User user = userService.readById(testUserId);

        mockMvc.perform(MockMvcRequestBuilders.get("/todos/all/users/" + testUserId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("todos"))
                .andExpect(MockMvcResultMatchers.model().attribute("todos", todos))
                .andExpect(MockMvcResultMatchers.model().attributeExists("user"))
                .andExpect(MockMvcResultMatchers.model().attribute("user", user));
    }

    @Test
    public void addCollaboratorTest() throws Exception {
        long newCollaboratorId = 4;

        mockMvc.perform(MockMvcRequestBuilders.get("/todos/" + testTodoId + "/add")
                .param("user_id", String.valueOf(newCollaboratorId)))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    public void addIncorrectCollaboratorTest() throws Exception {
        long newCollaboratorId = 400;

        mockMvc.perform(MockMvcRequestBuilders.get("/todos/" + testTodoId + "/add")
                        .param("user_id", String.valueOf(newCollaboratorId)))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    public void removeCollaboratorTest() throws Exception {
        long collaboratorId = 4;

        mockMvc.perform(MockMvcRequestBuilders.get("/todos/" + 10 + "/remove")
                        .param("user_id", String.valueOf(collaboratorId)))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    public void removeIncorrectCollaboratorTest() throws Exception {
        long collaboratorId = 400;

        mockMvc.perform(MockMvcRequestBuilders.get("/todos/" + 10 + "/remove")
                        .param("user_id", String.valueOf(collaboratorId)))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }
}
