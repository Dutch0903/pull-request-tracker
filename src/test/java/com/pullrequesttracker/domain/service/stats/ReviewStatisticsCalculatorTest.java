package com.pullrequesttracker.domain.service.stats;

import com.pullrequesttracker.domain.model.PullRequest;
import com.pullrequesttracker.domain.model.PullRequestState;
import com.pullrequesttracker.domain.type.ReviewStatus;
import com.pullrequesttracker.domain.valueobject.MergeInfo;
import com.pullrequesttracker.domain.valueobject.ReviewStatistics;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static com.pullrequesttracker.testfixtures.domain.model.PullRequestTestBuilder.aPullRequest;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReviewStatisticsCalculatorTest {

    @Test
    void calculate_withEmptyList_shouldReturnAllZeros() {
        ReviewStatisticsCalculator calculator = new ReviewStatisticsCalculator(new StatisticsConfiguration(0, 0));

        ReviewStatistics statistics = calculator.calculate(List.of());

        assertEquals(0, statistics.approved());
        assertEquals(0, statistics.awaitingReview());
        assertEquals(0, statistics.changesRequested());
    }

    @Test
    void calculate_whenNonOpenPullRequestsArePresent_shouldIgnoreThem() {
        ReviewStatisticsCalculator calculator = new ReviewStatisticsCalculator(new StatisticsConfiguration(0, 0));
        List<PullRequest> list = List.of(
                aPullRequest().withState(new PullRequestState.Ignored()).withReviewStatus(ReviewStatus.APPROVED)
                        .build(),
                aPullRequest().withState(new PullRequestState.Merged(new MergeInfo("user", Instant.now())))
                        .withReviewStatus(ReviewStatus.APPROVED).build(),
                aPullRequest().withState(new PullRequestState.Closed()).withReviewStatus(ReviewStatus.APPROVED)
                        .build());

        ReviewStatistics statistics = calculator.calculate(list);

        assertEquals(0, statistics.approved());
        assertEquals(0, statistics.awaitingReview());
        assertEquals(0, statistics.changesRequested());
    }

    @Test
    void calculate_withReviewRequiredPullRequests_shouldCountAsAwaitingReview() {
        ReviewStatisticsCalculator calculator = new ReviewStatisticsCalculator(new StatisticsConfiguration(0, 0));
        List<PullRequest> list = List.of(aPullRequest().withReviewStatus(ReviewStatus.REVIEW_REQUIRED).build());

        ReviewStatistics statistics = calculator.calculate(list);

        assertEquals(1, statistics.awaitingReview());
        assertEquals(0, statistics.changesRequested());
        assertEquals(0, statistics.approved());
    }

    @Test
    void calculate_withChangesRequestedPullRequests_shouldCountAsChangesRequested() {
        ReviewStatisticsCalculator calculator = new ReviewStatisticsCalculator(new StatisticsConfiguration(0, 0));
        List<PullRequest> list = List.of(aPullRequest().withReviewStatus(ReviewStatus.CHANGES_REQUESTED).build());

        ReviewStatistics statistics = calculator.calculate(list);

        assertEquals(0, statistics.awaitingReview());
        assertEquals(1, statistics.changesRequested());
        assertEquals(0, statistics.approved());
    }

    @Test
    void calculate_withApprovedPullRequests_shouldCountAsApproved() {
        ReviewStatisticsCalculator calculator = new ReviewStatisticsCalculator(new StatisticsConfiguration(0, 0));
        List<PullRequest> list = List.of(aPullRequest().withReviewStatus(ReviewStatus.APPROVED).build());

        ReviewStatistics statistics = calculator.calculate(list);

        assertEquals(0, statistics.awaitingReview());
        assertEquals(0, statistics.changesRequested());
        assertEquals(1, statistics.approved());
    }
}
