package com.pullrequesttracker.domain.repository;

public class TokenPersistenceException extends Exception {
    public TokenPersistenceException(String message) {
        super(message);
    }

    public TokenPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
