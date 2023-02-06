package com.softserve.itacademy;

import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.service.ToDoService;
import com.softserve.itacademy.service.impl.ToDoServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.UserService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(JUnitPlatform.class)
public class ToDoServiceTest {
    private static UserService userService;
    private static ToDoService toDoService;

    @BeforeAll
    public static void setupBeforeClass() throws Exception {
        AnnotationConfigApplicationContext annotationConfigContext = new AnnotationConfigApplicationContext(Config.class);
        userService = annotationConfigContext.getBean(UserService.class);
        toDoService = annotationConfigContext.getBean(ToDoService.class);
        annotationConfigContext.close();
    }

    @Test
    public void checkAddToDo() {
        User user = new User("Oleh", "Tereshko","oleg@gmail.com", "12345");
        userService.addUser(user);
        ToDo todo = new ToDo("Mytodo", user);
        toDoService.addTodo(todo, user);
        ToDo expected = todo;

        ToDo actual = toDoService.getByUserTitle(user, "Mytodo");
        Assertions.assertEquals(expected, actual, "Todo added incorrectly!");
    }

    @Test
    public void checkAddToDoWithNotExistingUser() {
        User user = new User("Serhiy", "Kaidash","Sergiy@gmail.com", "4123");
        ToDo todo = new ToDo("Other todo", user);
        ToDo expected = null;
        // User wasn't added to list
        ToDo actual = toDoService.addTodo(todo, user);
        Assertions.assertEquals(expected, actual, "You can't add todo with not existing user");
    }

    @Test
    public void checkAddToDoWithInvalidTitle() {
        User user = new User("Serhiy", "Kaidash","Sergiy@gmail.com", "4123");
        userService.addUser(user);
        ToDo todo = new ToDo("todo !.!./", user);
        ToDo expected = null;
        ToDo actual = toDoService.addTodo(todo, user);
        Assertions.assertEquals(expected, actual, "You can't add todo with invalid title");
    }

    @Test
    public void checkAddToDoWithNullUser() {
        try {
            ToDo todo = new ToDo("Shop todo", null);
            ToDo actual = toDoService.addTodo(todo, null);
            Assertions.fail("Cannot add todo with null user");
        } catch (NullPointerException e) {
            Assertions.assertTrue(true);
        }
    }

    @Test
    public void checkAddToDoWithNullToDo() {
        User user = new User("Serhiy", "Kaidash","Sergiy12@gmail.com", "4123");
        ToDo expected = null;
        ToDo actual = toDoService.addTodo(null, user);
        Assertions.assertEquals(expected, actual, "Cannot add todo with null user");
    }


    @Test
    public void checkUpdateToDo(){
        User user = new User("Oleh", "Tereshko","oleg33@gmail.com", "12345");
        ArrayList<ToDo> todos = new ArrayList<>();
        todos.add(new ToDo("Mytodo01", user));
        user.setTodos(todos);
        userService.addUser(user);

        ToDo todoToUpdate = new ToDo("Mytodo01", user); // with other tasks etc.
        ToDo expected = todoToUpdate;
        ToDo actual = toDoService.updateTodo(todoToUpdate);
        Assertions.assertEquals(expected, actual, "Cannot update todo");
    }

    @Test
    public void checkUpdateToDoWithNotExistingTodo(){
        User user = new User("Oleh", "Tereshko","oleg02@gmail.com", "12345");
        ArrayList<ToDo> todos = new ArrayList<>();
        todos.add(new ToDo("Mytodo02", user));
        user.setTodos(todos);
        userService.addUser(user);

        // there are no todos with this title in the list
        ToDo todoToUpdate = new ToDo("NotExistingTodo", user);
        ToDo expected = null;
        ToDo actual = toDoService.updateTodo(todoToUpdate);
        Assertions.assertEquals(expected, actual, "Cannot update todo if there is no todo with this title in the list");
    }

    @Test
    public void checkUpdateNullTodo(){
        try {
            ToDo actual = toDoService.updateTodo(null);
            Assertions.fail("If todo is null - it mustn't be updated");
        } catch (NullPointerException e) {
            Assertions.assertTrue(true);
        }
    }


    @Test
    public void checkGetByUser() {
        User user = new User("Igor", "Ivanov", "igorok17208@gmail.com", "1111");
        List<ToDo> todos = new ArrayList<>();
        todos.add(new ToDo("Home work", user));
        todos.add(new ToDo("Vacation", user));
        todos.add(new ToDo("Study plan", user));
        user.setTodos(todos);
        userService.addUser(user);

        List<ToDo> expected = todos;
        List<ToDo> actual = toDoService.getByUser(user);
        Assertions.assertEquals(expected, actual, "Can't get todos by user");
    }

    @Test
    public void checkGetByNotExistingUser() {
        // user with this email does not exist
        User user = new User("Igor", "Ivanov", "igorok7@gmail.com", "1111");
        List<ToDo> expected = null;
        List<ToDo> actual = toDoService.getByUser(user);
        Assertions.assertEquals(expected, actual, "A non-existent user cannot have todos");
    }

    @Test
    public void checkGetByNullUser() {
        try {
            List<ToDo> toDos = toDoService.getByUser(null);
            Assertions.fail("Cannot get todos while user is null");
        } catch (NullPointerException e) {
            Assertions.assertTrue(true);
        }
    }

    @Test
    public void checkGetByUserTitle() {
        User user = new User("Igor", "Ivanov", "igorok1700@gmail.com", "1111");
        List<ToDo> todos = new ArrayList<>();
        ToDo vacationTodo = new ToDo("Vacation", user);
        todos.add(new ToDo("Home work", user));
        todos.add(vacationTodo);
        todos.add(new ToDo("Study plan", user));
        user.setTodos(todos);
        userService.addUser(user);

        ToDo expected = vacationTodo;
        ToDo actual = toDoService.getByUserTitle(user, "Vacation");
        Assertions.assertEquals(expected, actual, "Cannot get todo by user and todo title");
    }

    @Test
    public void checkGetByUserTitleWithNotExistingUser() {
        // user with this email does not exist
        User user = new User("Igor", "Ivanov", "igorok17001@gmail.com", "1111");
        ToDo expected = null;
        ToDo actual = toDoService.getByUserTitle(user, "Some title");
        Assertions.assertEquals(expected, actual, "Cannot get todo by non-existent user");
    }

    @Test
    public void checkGetByUserTitleWithNotExistingTitle() {
        // user with this email does not exist
        User user = new User("Igor", "Ivanov", "igorok17001@gmail.com", "1111");
        List<ToDo> todos = new ArrayList<>();
        todos.add(new ToDo("My todo", user));
        user.setTodos(todos);
        userService.addUser(user);
        ToDo expected = null;
        ToDo actual = toDoService.getByUserTitle(user, "Some title");
        Assertions.assertEquals(expected, actual, "Cannot get todo by non-existent title");
    }

    @Test
    public void checkGetByUserTitleWithNullUser() {
        try {
            ToDo actual = toDoService.getByUserTitle(null, "Some title");
            Assertions.fail("Cannot get todo while user is null");
        } catch (NullPointerException e) {
            Assertions.assertTrue(true);
        }
    }

    @Test
    public void checkGetByUserTitleWithNullTitle() {
        User user = new User("Igor", "Ivanov", "igorok170013@gmail.com", "1111");
        List<ToDo> todos = new ArrayList<>();
        todos.add(new ToDo("Awesome toto", user));
        user.setTodos(todos);
        userService.addUser(user);

        ToDo expected = null;
        ToDo actual = toDoService.getByUserTitle(user, null);
        Assertions.assertEquals(expected, actual, "Cannot get todo while title is null");
    }

    @Test
    public void checkDeleteToDo() {
        User user = new User("Igor", "Ivanov", "igo773@gmail.com", "1111");
        ToDo todo = new ToDo("ToDoIvanov", user);
        user.addTodo(todo);
        userService.addUser(user);

        ToDo expected = null;
        toDoService.deleteTodo(todo);
        ToDo actual = toDoService.getByUserTitle(user, "ToDoIvanov");
        Assertions.assertEquals(expected, actual, "ToDo was not removed");
    }

    @Test
    public void checkDeleteNullToDo() {
        User user = new User("Igor", "Ivanov", "igo14@gmail.com", "1111");
        ToDo todo = new ToDo("ToDoIgo14", user);
        user.addTodo(todo);
        userService.addUser(user);

        ToDo expected = todo;
        toDoService.deleteTodo(null);
        ToDo actual = toDoService.getByUserTitle(user, "ToDoIgo14");
        Assertions.assertEquals(expected, actual, "Null todo can't be deleted");
    }

    @Test
    public void checkGetAllToDos() {
        List<ToDo> allTodos = toDoService.getAll();

        User user = new User("Masha", "Ivanova", "igor@gmail.com", "1111");
        ArrayList<ToDo> userTodos = new ArrayList<>();
        ToDo userTodo = new ToDo("Masha list", user);
        userTodos.add(userTodo);
        user.setTodos(userTodos);
        userService.addUser(user);

        allTodos.add(userTodo);
        List<ToDo> expected = allTodos;
        List<ToDo> actual = toDoService.getAll();
        Assertions.assertEquals(expected, actual, "GetAllToDos works wrong");
    }

}


