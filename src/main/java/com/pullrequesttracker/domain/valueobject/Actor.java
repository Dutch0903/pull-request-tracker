package com.pullrequesttracker.domain.valueobject;

import java.util.Objects;

public record Actor(String value) {
    public Actor {
        Objects.requireNonNull(value, "Actor must not be null");
        if (value.isBlank())
            throw new IllegalArgumentException("Actor must not be blank");
    }

    public static Actor from(String value) {
        return new Actor(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
