package com.pullrequesttracker.domain.model;

import com.pullrequesttracker.domain.valueobject.Review;
import com.pullrequesttracker.domain.valueobject.ReviewSummary;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

import static com.pullrequesttracker.testfixtures.domain.model.PullRequestTestBuilder.aPullRequest;
import static com.pullrequesttracker.testfixtures.domain.model.ReviewTestBuilder.aReview;
import static com.pullrequesttracker.testfixtures.domain.valueobject.ReviewSummaryTestBuilder.aReviewSummary;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PullRequestTest {
    @Test
    void addReview_whenReviewHasNotBeenSubmitted_shouldAddReview() {
        Review review = aReview().build();
        ReviewSummary reviewSummary = aReviewSummary().withReviews(Collections.emptyList()).build();
        PullRequest pullRequest = aPullRequest().withReviewSummary(reviewSummary).build();

        assertEquals(0, pullRequest.getReviewSummary().reviews().size());

        pullRequest.addReview(review);

        assertEquals(1, pullRequest.getReviewSummary().reviews().size());
    }

    @Test
    void addReview_whenReviewIsAlreadySubmitted_shouldNotAddReview() {
        Review review = aReview().build();
        ReviewSummary reviewSummary = aReviewSummary().withReviews(List.of(review)).build();
        PullRequest pullRequest = aPullRequest().withReviewSummary(reviewSummary).build();

        assertEquals(1, pullRequest.getReviewSummary().reviews().size());

        pullRequest.addReview(review);

        assertEquals(1, pullRequest.getReviewSummary().reviews().size());
    }

    @Test
    void addReview_whenOlderReviewIsSubmitted_shouldReplaceWithNewerReview() {
        String reviewer = "reviewer";
        Review olderReview = aReview().withReviewer(reviewer).withSubmittedAt(Instant.now().minusSeconds(10)).build();
        Review newerReview = aReview().withReviewer(reviewer).withSubmittedAt(Instant.now()).build();

        ReviewSummary reviewSummary = aReviewSummary().withReviews(List.of(olderReview)).build();

        PullRequest pullRequest = aPullRequest().withReviewSummary(reviewSummary).build();

        pullRequest.addReview(newerReview);

        assertEquals(1, pullRequest.getReviewSummary().reviews().size());
        assertEquals(newerReview, pullRequest.getReviewSummary().reviews().getFirst());
    }
}
