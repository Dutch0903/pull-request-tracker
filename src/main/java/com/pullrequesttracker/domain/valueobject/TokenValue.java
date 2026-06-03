package com.pullrequesttracker.domain.valueobject;

import java.util.Objects;

public record TokenValue(String value) {
    public TokenValue {
        Objects.requireNonNull(value, "Token value must not be null");
        if (value.isBlank()) throw new IllegalArgumentException("Token value must not be blank");
    }

    @Override
    public String toString() {
        return value;
    }
}
