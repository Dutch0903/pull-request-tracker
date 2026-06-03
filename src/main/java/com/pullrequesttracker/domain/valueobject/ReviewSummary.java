package com.pullrequesttracker.domain.valueobject;

import com.pullrequesttracker.domain.type.ReviewStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ReviewSummary {
    private final List<Review> reviews;
    private ReviewStatus reviewStatus;

    public ReviewSummary(List<Review> reviews, ReviewStatus reviewStatus) {
        Objects.requireNonNull(reviews, "Reviews must not be null");
        Objects.requireNonNull(reviewStatus, "Review status must not be null");
        this.reviews = new ArrayList<>(reviews);
        this.reviewStatus = reviewStatus;
    }

    public void addReview(Review review) {
        Objects.requireNonNull(review, "Review must not be null");
        for (int i = 0; i < reviews.size(); i++) {
            Review existing = reviews.get(i);
            if (existing.reviewer().equals(review.reviewer())) {
                if (existing.submittedAt().isBefore(review.submittedAt())) {
                    reviews.set(i, review);
                }
                return;
            }
        }
        reviews.add(review);
    }

    public void updateReviewStatus(ReviewStatus reviewStatus) {
        this.reviewStatus = Objects.requireNonNull(reviewStatus, "Review status must not be null");
    }

    public int approvalCount() {
        return (int) reviews.stream().filter(r -> r.status() == ReviewStatus.APPROVED).count();
    }

    public List<Review> reviews() {
        return Collections.unmodifiableList(reviews);
    }

    public ReviewStatus reviewStatus() {
        return reviewStatus;
    }
}
