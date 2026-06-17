package com.pullrequesttracker.application.exception;

public class DeleteTokenException extends ApplicationException {
    public DeleteTokenException(String message) {
        super(message);
    }

    public DeleteTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
