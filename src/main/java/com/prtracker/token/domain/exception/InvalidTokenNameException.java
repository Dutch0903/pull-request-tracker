package com.prtracker.token.domain.exception;

public class InvalidTokenNameException extends RuntimeException {
    public InvalidTokenNameException(String message) {
        super("Invalid token name: " + message);
    }
}
