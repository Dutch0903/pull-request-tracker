package com.prtracker.coderepository.domain.exception;

public class InvalidFullNameException extends RuntimeException {
    public InvalidFullNameException(String message) {
        super("Invalid full name: " + message);
    }
}
