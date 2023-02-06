package com.softserve.itacademy;

import com.softserve.itacademy.dto.TaskDto;
import com.softserve.itacademy.dto.TaskTransformer;
import com.softserve.itacademy.model.Priority;
import com.softserve.itacademy.model.Role;
import com.softserve.itacademy.model.State;
import com.softserve.itacademy.model.Task;
import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.StateService;
import com.softserve.itacademy.service.TaskService;
import com.softserve.itacademy.service.ToDoService;
import org.junit.jupiter.api.Assertions;
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

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
public class TaskControllerTests {
    private static final int testTodoId = 7;
    private static final int testTaskId = 6;
    private static final int testStateId = 5;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private  TaskService taskService;
    private final ToDoService todoService;
    private final StateService stateService;

    static Task testTask;

    private static MultiValueMap<String, String> validTaskMap;
    private static MultiValueMap<String, String> invalidTaskMap;

    @Autowired
    public TaskControllerTests(TaskService taskService, ToDoService todoService, StateService stateService) {
        this.taskService = taskService;
        this.todoService = todoService;
        this.stateService = stateService;
    }

    @BeforeAll
    private static void init(){
        validTaskMap = new LinkedMultiValueMap<>();
        invalidTaskMap = new LinkedMultiValueMap<>();
        testTask = new Task();
        Role testRole = new Role();
        ToDo testToDo = new ToDo();
        User testUser = new User();
        State testState = new State();

        testRole.setUsers(Arrays.asList(testUser));
        testRole.setName("TestRole");

        testUser.setId(1);
        testUser.setLastName("Last");
        testUser.setFirstName("First");
        testUser.setRole(testRole);
        testUser.setPassword("SomePassword123");
        testUser.setEmail("Valid@email.com");
        testUser.setMyTodos(new ArrayList<>());

        testToDo.setOwner(testUser);
        testToDo.setTitle("Test ToDo");
        testToDo.setCreatedAt(LocalDateTime.now());
        testToDo.setId(testTodoId);

        testState.setName("TestState");
        testState.setTasks(Arrays.asList(testTask));

        testTask.setId(1);
        testTask.setName("Test Task");
        testTask.setPriority(Priority.MEDIUM);
        testTask.setTodo(testToDo);
        testTask.setState(testState);

        validTaskMap.add("id", String.valueOf(testTaskId));
        validTaskMap.add("name", "Test Task");
        validTaskMap.add("priority", "MEDIUM");
        validTaskMap.add("todoId", String.valueOf(testTodoId));
        validTaskMap.add("stateId", String.valueOf(testStateId));

        invalidTaskMap.add("name", "");
        invalidTaskMap.add("priority", "fake");
        invalidTaskMap.add("todoId", "-1");
        invalidTaskMap.add("state", " ");
    }

    @Test
    public void createTaskGetRequestTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/tasks/create/todos/" + testTodoId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("task"))
                .andExpect(MockMvcResultMatchers.model().attribute("task", new TaskDto()))
                .andExpect(MockMvcResultMatchers.model().attributeExists("todo"))
                .andExpect(MockMvcResultMatchers.model().attribute("todo", todoService.readById(testTodoId)))
                .andExpect(MockMvcResultMatchers.model().attributeExists("priorities"))
                .andExpect(MockMvcResultMatchers.model().attribute("priorities", Priority.values()));
    }

    @Test
    public void createTaskPostRequestTestWithCorrectParam() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/tasks/create/todos/" + testTodoId)
                .params(validTaskMap))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
        Task actual = taskService.readById(testTaskId);
        Assertions.assertEquals("Test Task", actual.getName());
    }

    @Test
    public void createTaskPostRequestTestWithIncorrectParam() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/tasks/create/todos/" + testTodoId)
                        .params(invalidTaskMap))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    public void updateTaskGetRequestTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/tasks/" + testTaskId + "/update/todos/" + testTodoId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("task"))
                .andExpect(MockMvcResultMatchers.model().attribute("task", TaskTransformer.convertToDto(taskService.readById(testTaskId))))
                .andExpect(MockMvcResultMatchers.model().attributeExists("priorities"))
                .andExpect(MockMvcResultMatchers.model().attribute("priorities", Priority.values()))
                .andExpect(MockMvcResultMatchers.model().attributeExists("states"))
                .andExpect(MockMvcResultMatchers.model().attribute("states", stateService.getAll()));
    }


    @Test
    public void updateTaskPostRequestTestWithCorrectParam() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/tasks/" + testTaskId + "/update/todos/" + testTodoId)
                        .params(validTaskMap))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
        Task actual = taskService.readById(testTaskId);
        Assertions.assertEquals("Test Task", actual.getName());
    }

    @Test
    public void updateTaskPostRequestTestWithIncorrectParam() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/tasks/" + testTaskId + "/update/todos/" + testTodoId)
                        .params(invalidTaskMap))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }


    @Test
    public void deleteTaskTest() throws Exception {
        taskService.create(testTask);
        
        mockMvc.perform(MockMvcRequestBuilders.get("/tasks/" + testTaskId + "/delete/todos/" + testTodoId))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());

        EntityNotFoundException thrown = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            taskService.readById(testTaskId);
        });
        Assertions.assertEquals("Task with id " + testTaskId + " not found", thrown.getMessage());
    }
}
