package com.pullrequesttracker.testfixtures.domain.valueobject;

import com.pullrequesttracker.domain.type.ReviewStatus;
import com.pullrequesttracker.domain.valueobject.Actor;
import com.pullrequesttracker.domain.valueobject.Review;

import java.time.Instant;

public class ReviewTestBuilder {
    private String reviewer = "reviewer";
    private ReviewStatus status = ReviewStatus.APPROVED;
    private Instant submittedAt = Instant.now();

    public static ReviewTestBuilder aReview() {
        return new ReviewTestBuilder();
    }

    public ReviewTestBuilder withReviewer(String reviewer) {
        this.reviewer = reviewer;
        return this;
    }

    public ReviewTestBuilder withStatus(ReviewStatus status) {
        this.status = status;
        return this;
    }

    public ReviewTestBuilder withSubmittedAt(Instant submittedAt) {
        this.submittedAt = submittedAt;
        return this;
    }

    public Review build() {
        return new Review(Actor.from(reviewer), status, submittedAt);
    }
}
