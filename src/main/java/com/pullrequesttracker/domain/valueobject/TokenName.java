package com.pullrequesttracker.domain.valueobject;

import java.util.Objects;

public record TokenName(String value) {
    public TokenName {
        Objects.requireNonNull(value, "Token name must not be null");
        if (value.isBlank()) throw new IllegalArgumentException("Token name must not be blank");
    }

    public static TokenName from(String name) {
        return new TokenName(name);
    }

    @Override
    public String toString() {
        return value;
    }
}
