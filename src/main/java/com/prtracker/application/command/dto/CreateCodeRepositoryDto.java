package com.prtracker.application.command.dto;

import java.util.UUID;

public record CreateCodeRepositoryDto(String repositoryReference, UUID tokenId) {
    public CreateCodeRepositoryDto {
        if (repositoryReference == null || repositoryReference.isBlank()) {
            throw new IllegalArgumentException("Repository reference cannot be null or blank");
        }
    }
}
