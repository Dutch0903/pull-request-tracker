package com.prtracker.coderepository.application.command;

import java.util.UUID;

public record CreateCodeRepositoryDto(String repositoryReference, UUID tokenId) {
    public CreateCodeRepositoryDto {
        if (repositoryReference == null || repositoryReference.isBlank()) {
            throw new IllegalArgumentException("Repository reference cannot be null or blank");
        }
    }
}
