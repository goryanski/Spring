package com.softserve.itacademy;

import com.softserve.itacademy.model.Priority;
import com.softserve.itacademy.model.Task;
import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.service.TaskService;
import com.softserve.itacademy.service.ToDoService;
import com.softserve.itacademy.service.impl.TaskServiceImpl;
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
public class TaskServiceTest {
    private static UserService userService;
    private static ToDoService toDoService;
    private static TaskService taskService;

    @BeforeAll
    public static void setupBeforeClass() throws Exception {
        AnnotationConfigApplicationContext annotationConfigContext = new AnnotationConfigApplicationContext(Config.class);
        userService = annotationConfigContext.getBean(UserService.class);
        toDoService = annotationConfigContext.getBean(ToDoService.class);
        taskService = annotationConfigContext.getBean(TaskService.class);
        annotationConfigContext.close();
    }

    @Test
    public void checkAddTask() {
        User user = new User("Vika", "Manuk", "vika01@gmail.com", "1111");
        ToDo toDo = new ToDo("todo01", user);
        user.addTodo(toDo);
        userService.addUser(user);

        Task newTask = new Task("Task01", Priority.LOW);
        Task expected = newTask;
        Task actual = taskService.addTask(newTask, toDo);
        Assertions.assertEquals(expected, actual, "Task was not added");
    }

    @Test
    public void checkAddInvalidTask() {
        User user = new User("Vika", "Manuk", "vika02@gmail.com", "1111");
        ToDo toDo = new ToDo("todo02", user);
        user.addTodo(toDo);
        userService.addUser(user);

        // invalid task name
        Task newTask = new Task("Task02!!;.", Priority.LOW);
        Task expected = null;
        Task actual = taskService.addTask(newTask, toDo);
        Assertions.assertEquals(expected, actual, "Invalid task cannot be added");
    }

    @Test
    public void checkAddNullTask() {
        User user = new User("Vika", "Manuk", "vika03@gmail.com", "1111");
        ToDo toDo = new ToDo("todo03", user);
        user.addTodo(toDo);
        userService.addUser(user);

        Task expected = null;
        Task actual = taskService.addTask(null, toDo);
        Assertions.assertEquals(expected, actual, "Cannot add task if it's null");
    }

    @Test
    public void checkAddTaskWithNullTodo() {
        try {
            Task newTask = new Task("Task02", Priority.LOW);
            Task actual = taskService.addTask(newTask, null);
            Assertions.fail("Cannot add task with null todo");
        } catch (NullPointerException e) {
            Assertions.assertTrue(true);
        }
    }

    @Test
    public void checkUpdateTask() {
        User user = new User("Vika", "Manuk", "vika04@gmail.com", "1111");
        ToDo toDo = new ToDo("todo04", user);
        Task task = new Task("Task04", Priority.LOW);
        toDo.addTask(task);
        user.addTodo(toDo);
        userService.addUser(user);

        Task taskToUpdate = new Task("Task04", Priority.HIGH);

        Task expected = taskToUpdate;
        Task actual = taskService.updateTask(taskToUpdate);
        Assertions.assertEquals(expected, actual, "Task was not updated");
    }

    @Test
    public void checkUpdateNotExistingTask() {
        User user = new User("Vika", "Manuk", "vika05@gmail.com", "1111");
        ToDo toDo = new ToDo("todo05", user);
        user.addTodo(toDo);
        userService.addUser(user);

        Task taskToUpdate = new Task("NotExistingTask", Priority.HIGH);
        Task expected = null;
        Task actual = taskService.updateTask(taskToUpdate);
        Assertions.assertEquals(expected, actual, "Cannot update not existing task");
    }

    @Test
    public void checkUpdateNullTask() {
        Task expected = null;
        Task actual = taskService.updateTask(null);
        Assertions.assertEquals(expected, actual, "Cannot update task if it's null");
    }

    @Test
    public void checkUpdateTaskWithNullName() {
        Task taskToUpdate = new Task(null, Priority.HIGH);
        Task expected = null;
        Task actual = taskService.updateTask(taskToUpdate);
        Assertions.assertEquals(expected, actual, "Cannot update task if it has null name");
    }

    @Test
    public void checkDeleteTask() {
        User user = new User("Vika", "Manuk", "vika05@gmail.com", "1111");
        ToDo toDo = new ToDo("todo05", user);
        Task task = new Task("Task05", Priority.LOW);
        toDo.addTask(task);
        user.addTodo(toDo);
        userService.addUser(user);

        Task expected = null;
        taskService.deleteTask(task);
        Task actual = taskService.getByToDoName(toDo, "Task05");
        Assertions.assertEquals(expected, actual, "Task wasn't deleted");
    }

    @Test
    public void checkDeleteNullTask() {
        User user = new User("Vika", "Manuk", "vika06@gmail.com", "1111");
        ToDo toDo = new ToDo("todo06", user);
        Task task = new Task("Task06", Priority.LOW);
        toDo.addTask(task);
        user.addTodo(toDo);
        userService.addUser(user);
        List<Task> tasks = taskService.getAll();
        Task expected = task;
        taskService.deleteTask(null);
        // task wasn't deleted
        Task actual = taskService.getByToDoName(toDo, "Task06");
        Assertions.assertEquals(expected, actual, "Null task can't be deleted");
    }

    @Test
    public void checkGetAllTasks() {
        List<Task> allTasks = taskService.getAll();

        User user = new User("Vika", "Manuk", "vika07@gmail.com", "1111");
        ToDo toDo = new ToDo("todo07", user);
        Task task = new Task("Task07", Priority.LOW);
        toDo.addTask(task);
        user.addTodo(toDo);
        userService.addUser(user);

        allTasks.add(task);
        List<Task> expected = allTasks;
        List<Task> actual = taskService.getAll();
        Assertions.assertEquals(expected, actual, "GetAllTasks works wrong");
    }

    @Test
    public void checkGetByToDo() {
        User user = new User("Vika", "Manuk", "vika08@gmail.com", "1111");
        ToDo toDo = new ToDo("todo08", user);
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task("Task08", Priority.LOW));
        tasks.add(new Task("Task008", Priority.MEDIUM));
        tasks.add(new Task("Task0008", Priority.HIGH));
        toDo.setTasks(tasks);
        user.addTodo(toDo);
        userService.addUser(user);

        List<Task> expected = tasks;
        List<Task> actual = taskService.getByToDo(toDo);
        Assertions.assertEquals(expected, actual, "Cannot get tasks by todo");
    }

    @Test
    public void checkGetByNullToDo() {
        List<Task> expected = null;
        List<Task> actual = taskService.getByToDo(null);
        Assertions.assertEquals(expected, actual, "Cannot get tasks by null todo");
    }

    @Test
    public void checkGetByToDoName() {
        User user = new User("Vika", "Manuk", "vika09@gmail.com", "1111");
        ToDo toDo = new ToDo("todo09", user);
        Task task = new Task("Task09", Priority.LOW);
        toDo.addTask(task);
        user.addTodo(toDo);
        userService.addUser(user);

        Task expected = task;
        Task actual = taskService.getByToDoName(toDo, "Task09");
        Assertions.assertEquals(expected, actual, "Cannot get task by todo and name");
    }

    @Test
    public void checkGetByToDoNameWithNullTodo() {
        Task expected = null;
        Task actual = taskService.getByToDoName(null, "Task09");
        Assertions.assertEquals(expected, actual, "Cannot get task by todo and name with null todo");
    }

    @Test
    public void checkGetByToDoNameWithNullName() {
        User user = new User("Vika", "Manuk", "vika10@gmail.com", "1111");
        ToDo toDo = new ToDo("todo10", user);
        user.addTodo(toDo);
        userService.addUser(user);

        Task expected = null;
        Task actual = taskService.getByToDoName(toDo, null);
        Assertions.assertEquals(expected, actual, "Cannot get task by todo and name with null task name");
    }

    @Test
    public void checkGetByUserName() {
        User user = new User("Vika", "Manuk", "vika11@gmail.com", "1111");
        ToDo toDo = new ToDo("todo11", user);
        Task task = new Task("Task11", Priority.LOW);
        toDo.addTask(task);
        user.addTodo(toDo);
        userService.addUser(user);

        Task expected = task;
        Task actual = taskService.getByUserName(user, "Task11");
        Assertions.assertEquals(expected, actual, "Cannot get task by user and name");
    }

    @Test
    public void checkGetByUserNameWithNullUser() {
        Task expected = null;
        Task actual = taskService.getByUserName(null, "Task11");
        Assertions.assertEquals(expected, actual, "Cannot get task by user and name if user is null");
    }

    @Test
    public void checkGetByUserNameWithNullName() {
        User user = new User("Vika", "Manuk", "vika12@gmail.com", "1111");
        ToDo toDo = new ToDo("todo12", user);
        user.addTodo(toDo);
        userService.addUser(user);

        Task expected = null;
        Task actual = taskService.getByUserName(user, null);
        Assertions.assertEquals(expected, actual, "Cannot get task by user and name if task name is null");
    }

    @Test
    public void checkGetByUserNameWithNotExistingUser() {
        User user = new User("Vika", "Manuk", "vika13@gmail.com", "1111");
        ToDo toDo = new ToDo("todo13", user);
        Task task = new Task("Task13", Priority.LOW);
        toDo.addTask(task);
        user.addTodo(toDo);
        userService.addUser(user);

        // wasn't added
        User user2 = new User("Vika", "Manuk", "vika14@gmail.com", "1111");

        Task expected = null;
        Task actual = taskService.getByUserName(user2, "Task13");
        Assertions.assertEquals(expected, actual, "Cannot get task by user and name");
    }
}
