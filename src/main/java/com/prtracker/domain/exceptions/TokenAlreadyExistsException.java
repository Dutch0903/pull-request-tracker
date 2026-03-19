package com.prtracker.domain.exceptions;

import com.prtracker.domain.valueobject.TokenName;

public class TokenAlreadyExistsException extends RuntimeException {
    public TokenAlreadyExistsException(TokenName name) {
        super("Token with name " + name.value() + " already exists");
    }
}
