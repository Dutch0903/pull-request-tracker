package com.pullrequesttracker.domain.valueobject;

import com.pullrequesttracker.domain.type.ReviewStatus;

import java.util.*;

public class ReviewSummary {
    private final List<Review> reviews;
    private ReviewStatus reviewStatus;
    private final Set<Actor> requestedReviewers;

    public ReviewSummary(List<Review> reviews, ReviewStatus reviewStatus, Set<Actor> reviewRequests) {
        Objects.requireNonNull(reviews, "Reviews must not be null");
        Objects.requireNonNull(reviewStatus, "Review status must not be null");
        this.reviews = new ArrayList<>(reviews);
        this.reviewStatus = reviewStatus;
        this.requestedReviewers = new HashSet<>(reviewRequests);
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

    public void addRequestedReviewer(Actor requestedReviewer) {
        Objects.requireNonNull(requestedReviewer, "Review request must not be null");

        requestedReviewers.add(requestedReviewer);
    }

    public void updateRequestedReviewers(Set<Actor> requestedReviewers) {
        this.requestedReviewers.clear();
        this.requestedReviewers.addAll(requestedReviewers);
    }

    public boolean hasRequestedReviewer(Actor actor) {
        return requestedReviewers.contains(actor);
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

    public Set<Actor> requestedReviewers() {
        return requestedReviewers;
    }
}
