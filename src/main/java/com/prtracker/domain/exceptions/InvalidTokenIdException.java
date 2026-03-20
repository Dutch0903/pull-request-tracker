package com.prtracker.domain.exceptions;

public class InvalidTokenIdException extends RuntimeException {
    public InvalidTokenIdException(String message) {
        super("Invalid token ID: " + message);
    }
}
