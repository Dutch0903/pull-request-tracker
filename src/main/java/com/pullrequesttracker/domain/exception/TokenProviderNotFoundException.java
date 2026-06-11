package com.pullrequesttracker.domain.exception;

public class TokenProviderNotFoundException extends TokenException {
    public TokenProviderNotFoundException(String message) {
        super(message);
    }
}
