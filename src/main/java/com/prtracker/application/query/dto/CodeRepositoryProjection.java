package com.prtracker.application.query.dto;

import com.prtracker.domain.entity.CodeRepository;
import com.prtracker.domain.enums.CodeRepositoryStatus;

public record CodeRepositoryProjection(String identifier, String owner, String name, CodeRepositoryStatus status) {
    public static CodeRepositoryProjection from(CodeRepository repository) {
        return new CodeRepositoryProjection(repository.getIdentifier().value(), repository.getOwner(),
                repository.getName(), repository.getStatus());
    }
}
