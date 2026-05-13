package com.prtracker.pullrequest.domain.model;

import com.prtracker.pullrequest.domain.enums.CiStatus;
import com.prtracker.pullrequest.domain.enums.PullRequestStatus;
import com.prtracker.pullrequest.domain.enums.ReviewStatus;

import java.time.Instant;
import java.util.List;

public record PullRequestSyncData(int externalId, String title, String author, boolean isDraft,
        PullRequestStatus status, String mergedBy, Instant mergedAt, CiStatus ciStatus, List<String> labels,
        List<String> requestedReviewers, List<Review> reviews, ReviewStatus reviewStatus, int commentCount,
        Instant createdAt, Instant updatedAt) {
}
