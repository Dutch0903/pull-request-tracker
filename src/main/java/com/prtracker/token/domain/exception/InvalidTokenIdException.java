package com.prtracker.token.domain.exception;

public class InvalidTokenIdException extends RuntimeException {
    public InvalidTokenIdException(String message) {
        super("Invalid token ID: " + message);
    }
}
