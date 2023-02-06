package com.softserve.itacademy.model;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private List<ToDo> todos = new ArrayList<>();

    public User(String firstName, String lastName, String email, String password){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public boolean addTodo(ToDo todo){
        return todos.add(todo);
    }

    public void deleteTodo(ToDo todo) {todos.remove(todo);}

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<ToDo> getTodos() {
        return todos;
    }

    public void setTodos(List<ToDo> todos) {
        this.todos = todos;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(!(obj instanceof User)) return false;
        User other = (User) obj;
        return this.getEmail().equals(other.getEmail());
    }

    @Override
    public int hashCode() {
        return this.getEmail().hashCode();
    }
}
