package com.prtracker.pullrequest.adapter.out.persistence;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record PullRequestDto(UUID id, UUID codeRepositoryId, int externalId, String title, String author, boolean draft,
        String status, String ciStatus, int commentCount, List<String> labels, List<String> requestedReviewers,
        List<ReviewDto> reviews, String reviewStatus, Instant createdAt, Instant updatedAt, String mergedBy,
        Instant mergedAt) {
}
