package com.pullrequesttracker.domain.sync;

import com.pullrequesttracker.domain.type.CiStatus;
import com.pullrequesttracker.domain.type.PullRequestStatus;
import com.pullrequesttracker.domain.type.ReviewStatus;
import com.pullrequesttracker.domain.valueobject.Review;
import org.jspecify.annotations.Nullable;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

public record PullRequestSyncData(
        int externalId,
        String title,
        String author,
        boolean isDraft,
        PullRequestStatus status,
        @Nullable String mergedBy,
        @Nullable Instant mergedAt,
        CiStatus ciStatus,
        List<String> labels,
        List<Review> reviews,
        ReviewStatus reviewStatus,
        int commentCount,
        Instant createdAt,
        Instant updatedAt
) {
    public PullRequestSyncData {
        if (externalId <= 0) throw new IllegalArgumentException("External id must be positive");
        Objects.requireNonNull(title, "Title must not be null");
        Objects.requireNonNull(author, "Author must not be null");
        Objects.requireNonNull(status, "Status must not be null");
        Objects.requireNonNull(ciStatus, "CI status must not be null");
        Objects.requireNonNull(labels, "Labels must not be null");
        Objects.requireNonNull(reviews, "Reviews must not be null");
        Objects.requireNonNull(reviewStatus, "Review status must not be null");
        if (commentCount < 0) throw new IllegalArgumentException("Comment count must not be negative");
        Objects.requireNonNull(createdAt, "Created at must not be null");
        Objects.requireNonNull(updatedAt, "Updated at must not be null");
    }
}
