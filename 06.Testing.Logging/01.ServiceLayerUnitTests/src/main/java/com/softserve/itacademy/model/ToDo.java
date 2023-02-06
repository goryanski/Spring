package com.softserve.itacademy.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ToDo {

    private String title;

    private LocalDateTime createdAt;

    private User owner;

    private List<Task> tasks = new ArrayList<>();

    public ToDo(String title, User owner) {
        this.title = title;
        this.owner = owner;
        this.createdAt = LocalDateTime.now();
    }

    public boolean addTask(Task task){
        return tasks.add(task);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ToDo)) return false;
        ToDo toDo = (ToDo) o;
        return this.getOwner().equals(toDo.getOwner()) && this.getTitle().equals(toDo.getTitle());
    }

    @Override
    public int hashCode() {
        return this.getOwner().hashCode() + this.getTitle().hashCode();
    }
}
