package com.pullrequesttracker.infrastructure.persistence.dto;

import org.jspecify.annotations.Nullable;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record PullRequestDto(UUID id, UUID codeRepositoryId, int externalId, String author, Instant createdAt,
        String title, boolean draft, String status, String ciStatus, List<String> labels, List<ReviewDto> reviews,
        String reviewStatus, int commentCount, @Nullable String mergedBy, @Nullable Instant mergedAt,
        Instant updatedAt) {
}
