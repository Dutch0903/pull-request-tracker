package com.prtracker.domain.valueobject;

public record CodeRepositoryId(Long value) {
    public static CodeRepositoryId from(Long id) {
        return new CodeRepositoryId(id);
    }
}
