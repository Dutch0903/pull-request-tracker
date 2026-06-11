package com.pullrequesttracker.domain.exception;

public class InvalidTokenValueException extends TokenException {
    public InvalidTokenValueException(String message) {
        super(message);
    }
}
