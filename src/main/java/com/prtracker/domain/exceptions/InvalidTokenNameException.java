package com.prtracker.domain.exceptions;

public class InvalidTokenNameException extends RuntimeException {
    public InvalidTokenNameException(String message) {
        super("Invalid token name: " + message);
    }
}
