package com.prtracker.token.domain.model;

import com.prtracker.token.domain.exception.InvalidTokenNameException;

public record TokenName(String value) {
    public TokenName {
        if (value == null || value.isBlank()) {
            throw new InvalidTokenNameException("Token name cannot be empty");
        }
    }

    public static TokenName from(String name) {
        return new TokenName(name);
    }
}
