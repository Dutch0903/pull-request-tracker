package com.prtracker.token.domain.exception;

import com.prtracker.token.domain.model.TokenName;

public class TokenAlreadyExistsException extends RuntimeException {
    public TokenAlreadyExistsException(TokenName name) {
        super("Token with name " + name.value() + " already exists");
    }
}
