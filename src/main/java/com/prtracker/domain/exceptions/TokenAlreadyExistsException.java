package com.prtracker.domain.exceptions;

public class TokenAlreadyExistsException extends RuntimeException {
    public TokenAlreadyExistsException(String name) {
        super("Token with name " + name + " already exists");
    }
}
