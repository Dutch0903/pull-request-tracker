package com.prtracker.pullrequest.adapter.out.github.graphql.dto;

import java.time.Instant;

public record PullRequest(int number, String title, String state, boolean isDraft, Instant createdAt, Instant updatedAt,
        boolean merged, Actor mergedBy, Instant mergedAt, boolean closed, Actor author, int totalCommentsCount,
        String reviewDecision, NodeList<Label> labels, NodeList<ReviewRequest> reviewRequests,
        NodeList<Review> latestReviews, NodeList<Commit> commits) {
}
