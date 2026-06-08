package com.pullrequesttracker.domain.valueobject;

import java.util.Objects;

public record FullName(String owner, String name) {
    public FullName {
        Objects.requireNonNull(owner, "Owner must not be null");
        if (owner.isBlank())
            throw new IllegalArgumentException("Owner must not be blank");
        Objects.requireNonNull(name, "Name must not be null");
        if (name.isBlank())
            throw new IllegalArgumentException("Name must not be blank");
    }

    @Override
    public String toString() {
        return owner + "/" + name;
    }
}
