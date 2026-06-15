package com.pullrequesttracker.application.exception;

public class CreateCodeRepositoryException extends ApplicationException {
    public CreateCodeRepositoryException(String message) {
        super(message);
    }

    public CreateCodeRepositoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
