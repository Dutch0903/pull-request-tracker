package com.pullrequesttracker.domain.exception;

public class TokenWithoutExpirationException extends TokenException {
    public TokenWithoutExpirationException(String message) {
        super(message);
    }
}
