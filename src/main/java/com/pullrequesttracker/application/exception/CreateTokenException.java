package com.pullrequesttracker.application.exception;

public class CreateTokenException extends ApplicationException {
    public CreateTokenException(String message) {
        super(message);
    }

    public CreateTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
