package com.pullrequesttracker.application.exception;

public class UpdateTokenException extends ApplicationException {
    public UpdateTokenException(String message) {
        super(message);
    }

    public UpdateTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
