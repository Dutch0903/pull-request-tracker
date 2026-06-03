package com.pullrequesttracker.domain.valueobject;

import java.util.Objects;
import java.util.UUID;

public record CodeRepositoryId(UUID value) {
    public CodeRepositoryId {
        Objects.requireNonNull(value, "Code repository id must not be null");
    }

    public static CodeRepositoryId from(UUID id) {
        return new CodeRepositoryId(id);
    }

    public static CodeRepositoryId create() {
        return new CodeRepositoryId(UUID.randomUUID());
    }
}
