package com.pullrequesttracker.domain.valueobject;

import java.util.Objects;

public record Title(String value) {
    public Title {
        Objects.requireNonNull(value, "Title must not be null");
        if (value.isBlank())
            throw new IllegalArgumentException("Title must not be blank");
    }

    public static Title from(String value) {
        return new Title(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
