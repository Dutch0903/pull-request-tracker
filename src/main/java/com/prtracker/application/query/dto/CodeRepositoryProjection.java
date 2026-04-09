package com.prtracker.application.query.dto;

import com.prtracker.domain.entity.CodeRepository;
import com.prtracker.domain.enums.CodeRepositoryStatus;

import java.util.UUID;

public record CodeRepositoryProjection(UUID id, String owner, String name, CodeRepositoryStatus status) {
    public static CodeRepositoryProjection from(CodeRepository repository) {
        return new CodeRepositoryProjection(repository.getId().value(), repository.getFullName().owner(),
                repository.getFullName().name(), repository.getStatus());
    }
}
