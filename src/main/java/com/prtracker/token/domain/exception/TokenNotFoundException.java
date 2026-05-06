package com.prtracker.token.domain.exception;

import com.prtracker.shared.kernel.TokenId;

public class TokenNotFoundException extends RuntimeException {
    public TokenNotFoundException(TokenId id) {
        super("Token not found: " + id.value());
    }
}
