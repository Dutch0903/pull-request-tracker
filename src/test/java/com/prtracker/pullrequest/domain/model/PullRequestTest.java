package com.prtracker.pullrequest.domain.model;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static com.prtracker.testfixtures.pullrequest.domain.model.PullRequestTestBuilder.aPullRequest;
import static com.prtracker.testfixtures.pullrequest.domain.model.ReviewTestBuilder.aReview;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PullRequestTest {
    @Test
    public void addReview_whenReviewHasNotBeenSubmitted_shouldAddReview() {
        Review review = aReview().build();
        PullRequest pullRequest = aPullRequest().withReviews(List.of()).build();

        assertEquals(0, pullRequest.getReviews().size());

        pullRequest.addReview(review);

        assertEquals(1, pullRequest.getReviews().size());
    }

    @Test
    public void addReview_whenReviewIsAlreadySubmitted_shouldNotAddReview() {
        Review review = aReview().build();
        PullRequest pullRequest = aPullRequest().withReviews(List.of(review)).build();

        assertEquals(1, pullRequest.getReviews().size());

        pullRequest.addReview(review);
        assertEquals(1, pullRequest.getReviews().size());
    }

    @Test
    public void addReview_whenOlderReviewIsSubmitted_shouldReplaceWithNewerReview() {
        String reviewer = "reviewer";
        Review olderReview = aReview().withReviewer(reviewer).withSubmittedAt(Instant.now().minusSeconds(10)).build();
        Review newerReview = aReview().withReviewer(reviewer).withSubmittedAt(Instant.now()).build();

        PullRequest pullRequest = aPullRequest().withReviews(List.of(olderReview)).build();

        pullRequest.addReview(newerReview);

        assertEquals(1, pullRequest.getReviews().size());
        assertEquals(newerReview, pullRequest.getReviews().getFirst());
    }
}
