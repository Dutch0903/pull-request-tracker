package com.prtracker.domain.exceptions;

import com.prtracker.domain.valueobject.TokenId;

public class TokenNotFoundException extends RuntimeException {
    public TokenNotFoundException(TokenId id) {
        super("Token not found: " + id.value());
    }
}
