package com.prtracker.coderepository.application.query;

import com.prtracker.coderepository.domain.model.CodeRepository;
import com.prtracker.coderepository.domain.model.CodeRepositoryStatus;

import java.util.UUID;

public record CodeRepositoryProjection(UUID id, String owner, String name, CodeRepositoryStatus status) {
    public static CodeRepositoryProjection from(CodeRepository repository) {
        return new CodeRepositoryProjection(repository.getId().value(), repository.getFullName().owner(),
                repository.getFullName().name(), repository.getStatus());
    }
}
