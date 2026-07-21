package com.pullrequesttracker.testfixtures.domain.valueobject;

import com.pullrequesttracker.domain.type.ReviewStatus;
import com.pullrequesttracker.domain.valueobject.Actor;
import com.pullrequesttracker.domain.valueobject.Review;
import com.pullrequesttracker.domain.valueobject.ReviewSummary;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class ReviewSummaryTestBuilder {
    private List<Review> reviews = Collections.emptyList();
    private ReviewStatus reviewStatus = ReviewStatus.REVIEW_REQUIRED;
    private Set<Actor> requestedReviewers = Collections.emptySet();

    public static ReviewSummaryTestBuilder aReviewSummary() {
        return new ReviewSummaryTestBuilder();
    }

    public ReviewSummaryTestBuilder withReviews(List<Review> reviews) {
        this.reviews = reviews;
        return this;
    }

    public ReviewSummaryTestBuilder withReviewStatus(ReviewStatus reviewStatus) {
        this.reviewStatus = reviewStatus;
        return this;
    }

    public ReviewSummaryTestBuilder withRequestedReviewers(Set<Actor> requestedReviewers) {
        this.requestedReviewers = requestedReviewers;
        return this;
    }

    public ReviewSummary build() {
        return new ReviewSummary(reviews, reviewStatus, requestedReviewers);
    }

}
