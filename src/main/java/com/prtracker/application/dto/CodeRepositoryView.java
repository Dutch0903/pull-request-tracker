package com.prtracker.application.dto;

import com.prtracker.domain.entity.CodeRepository;
import com.prtracker.domain.enums.CodeRepositoryStatus;

public record CodeRepositoryView(String identifier, String owner, String name, String url, CodeRepositoryStatus status) {
    public static CodeRepositoryView from(CodeRepository repository) {
        return new CodeRepositoryView(repository.getIdentifier().value(), repository.getOwner(), repository.getName(),
                repository.getUrl(), repository.getStatus());
    }
}
