package com.prtracker.infrastructure.persistence.dto;

import java.time.Instant;
import java.util.UUID;

public record PullRequestDto(UUID id, UUID codeRepositoryId, int externalId, String title, String author, String status,
        Instant updatedAt) {
}
