package com.prtracker.domain.valueobject;

import com.prtracker.domain.exceptions.InvalidCodeRepositoryIdException;

import java.util.UUID;

public record CodeRepositoryId(UUID value) {
    public CodeRepositoryId {
        if (value == null) {
            throw new InvalidCodeRepositoryIdException("Code repository ID cannot be null");
        }
    }

    public static CodeRepositoryId from(UUID id) {
        return new CodeRepositoryId(id);
    }

    public static CodeRepositoryId create() {
        return new CodeRepositoryId(UUID.randomUUID());
    }
}
