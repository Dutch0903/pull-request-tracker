package com.prtracker.testfixtures.pullrequest.domain.model;

import com.prtracker.pullrequest.domain.enums.ReviewStatus;
import com.prtracker.pullrequest.domain.model.Review;

import java.time.Instant;

public class ReviewTestBuilder {
    private String reviewer = "reviewer";
    private ReviewStatus reviewStatus = ReviewStatus.APPROVED;
    private Instant submittedAt = Instant.now();

    public static ReviewTestBuilder aReview() {
        return new ReviewTestBuilder();
    }

    public static ReviewTestBuilder copyOf(Review review) {
        return aReview().withReviewer(review.reviewer()).withReviewStatus(review.status())
                .withSubmittedAt(review.submittedAt());
    }

    public ReviewTestBuilder withReviewer(String reviewer) {
        this.reviewer = reviewer;
        return this;
    }

    public ReviewTestBuilder withReviewStatus(ReviewStatus reviewStatus) {
        this.reviewStatus = reviewStatus;
        return this;
    }

    public ReviewTestBuilder withSubmittedAt(Instant submittedAt) {
        this.submittedAt = submittedAt;
        return this;
    }

    public Review build() {
        return new Review(reviewer, reviewStatus, submittedAt);
    }
}
