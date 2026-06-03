package com.pullrequesttracker.domain.valueobject;

import java.util.Objects;
import java.util.UUID;

public record TokenId(UUID value) {
    public TokenId {
        Objects.requireNonNull(value, "Token id must not be null");
    }

    public static TokenId from(UUID id) {
        return new TokenId(id);
    }

    public static TokenId create() {
        return new TokenId(UUID.randomUUID());
    }
}
