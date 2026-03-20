package com.prtracker.domain.valueobject;

import com.prtracker.domain.exceptions.InvalidTokenIdException;

import java.util.UUID;

public record TokenId(UUID value) {
    public TokenId {
        if (value == null) {
            throw new InvalidTokenIdException("Token ID cannot be null");
        }
    }

    public static TokenId from(UUID id) {
        return new TokenId(id);
    }

    public static TokenId create() {
        return new TokenId(UUID.randomUUID());
    }
}
