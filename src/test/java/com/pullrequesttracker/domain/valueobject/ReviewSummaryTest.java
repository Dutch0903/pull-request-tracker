package com.pullrequesttracker.domain.valueobject;

import com.pullrequesttracker.domain.type.ReviewStatus;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReviewSummaryTest {
    @Test
    void approvalCount_whenNoReviews_shouldReturnZero() {
        ReviewSummary summary = new ReviewSummary(Collections.emptyList(), ReviewStatus.REVIEW_REQUIRED);

        assertEquals(0, summary.approvalCount());
    }

    @Test
    void approvalCount_whenAllApproved_shouldReturnCount() {
        List<Review> reviews = List.of(new Review("alice", ReviewStatus.APPROVED, Instant.now()),
                new Review("bob", ReviewStatus.APPROVED, Instant.now()));
        ReviewSummary summary = new ReviewSummary(reviews, ReviewStatus.APPROVED);

        assertEquals(2, summary.approvalCount());
    }

    @Test
    void approvalCount_whenMixedStatuses_shouldCountOnlyApprovals() {
        List<Review> reviews = List.of(new Review("alice", ReviewStatus.APPROVED, Instant.now()),
                new Review("bob", ReviewStatus.CHANGES_REQUESTED, Instant.now()),
                new Review("carol", ReviewStatus.REVIEW_REQUIRED, Instant.now()));
        ReviewSummary summary = new ReviewSummary(reviews, ReviewStatus.CHANGES_REQUESTED);

        assertEquals(1, summary.approvalCount());
    }
}
