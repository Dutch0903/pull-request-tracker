package com.pullrequesttracker.application.provider;

public class TokenInfoException extends RuntimeException {
    public TokenInfoException(String message) {
        super(message);
    }

    public TokenInfoException(String message, Throwable cause) {
        super(message, cause);
    }
}
