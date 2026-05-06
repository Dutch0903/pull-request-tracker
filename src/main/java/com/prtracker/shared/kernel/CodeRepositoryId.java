package com.prtracker.shared.kernel;

import com.prtracker.coderepository.domain.exception.InvalidCodeRepositoryIdException;

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
