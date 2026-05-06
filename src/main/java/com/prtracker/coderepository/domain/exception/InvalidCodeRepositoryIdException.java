package com.prtracker.coderepository.domain.exception;

public class InvalidCodeRepositoryIdException extends RuntimeException {
    public InvalidCodeRepositoryIdException(String message) {
        super("Invalid code repository ID: " + message);
    }
}
