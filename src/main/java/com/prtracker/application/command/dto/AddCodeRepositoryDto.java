package com.prtracker.application.command.dto;

import java.util.UUID;

public record AddCodeRepositoryDto(String repositoryReference, UUID tokenId) {
    public AddCodeRepositoryDto {
        if (repositoryReference == null || repositoryReference.isBlank()) {
            throw new IllegalArgumentException("Repository reference cannot be null or blank");
        }
    }
}
