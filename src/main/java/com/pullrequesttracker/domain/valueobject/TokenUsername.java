package com.pullrequesttracker.domain.valueobject;

import java.util.Objects;

public record TokenUsername(String value) {
    public TokenUsername {
        Objects.requireNonNull(value, "Token username must not be null");
        if (value.isBlank())
            throw new IllegalArgumentException("Token username must not be blank");
    }

    @Override
    public String toString() {
        return value;
    }
}
