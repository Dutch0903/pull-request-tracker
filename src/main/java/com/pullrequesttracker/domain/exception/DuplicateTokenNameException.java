package com.pullrequesttracker.domain.exception;

public class DuplicateTokenNameException extends TokenException {
    public DuplicateTokenNameException(String message) {
        super(message);
    }
}
