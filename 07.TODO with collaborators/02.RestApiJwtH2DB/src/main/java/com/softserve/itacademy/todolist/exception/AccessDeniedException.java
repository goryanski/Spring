package com.softserve.itacademy.todolist.exception;

public class AccessDeniedException extends RuntimeException {
    public AccessDeniedException() {}

    public AccessDeniedException(String message) {
        super(message);
    }
}
