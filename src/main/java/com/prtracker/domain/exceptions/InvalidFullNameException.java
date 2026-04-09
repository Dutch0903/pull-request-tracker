package com.prtracker.domain.exceptions;

public class InvalidFullNameException extends RuntimeException {
    public InvalidFullNameException(String message) {
        super("Invalid Full Name: " + message);
    }
}
