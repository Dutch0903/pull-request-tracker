package com.prtracker.domain.exceptions;

public class InvalidCodeRepositoryIdException extends RuntimeException {
    public InvalidCodeRepositoryIdException(String message) {
        super("Invalid code repository ID: " + message);
    }
}
