package com.pullrequesttracker.domain.service.stats;

import com.pullrequesttracker.domain.model.PullRequest;
import com.pullrequesttracker.domain.model.PullRequestState;
import com.pullrequesttracker.domain.type.ReviewStatus;
import com.pullrequesttracker.domain.valueobject.Actor;
import com.pullrequesttracker.domain.valueobject.MergeInfo;
import com.pullrequesttracker.domain.valueobject.ReviewStatistics;
import com.pullrequesttracker.domain.valueobject.ReviewSummary;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static com.pullrequesttracker.testfixtures.domain.model.PullRequestTestBuilder.aPullRequest;
import static com.pullrequesttracker.testfixtures.domain.valueobject.ReviewSummaryTestBuilder.aReviewSummary;
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
        ReviewSummary approvedReviewSummary = aReviewSummary().withReviewStatus(ReviewStatus.APPROVED).build();

        List<PullRequest> list = List.of(
                aPullRequest().withState(new PullRequestState.Ignored()).withReviewSummary(approvedReviewSummary)
                        .build(),
                aPullRequest().withState(new PullRequestState.Merged(new MergeInfo(Actor.from("user"), Instant.now())))
                        .withReviewSummary(approvedReviewSummary).build(),
                aPullRequest().withState(new PullRequestState.Closed()).withReviewSummary(approvedReviewSummary)
                        .build());

        ReviewStatistics statistics = calculator.calculate(list);

        assertEquals(0, statistics.approved());
        assertEquals(0, statistics.awaitingReview());
        assertEquals(0, statistics.changesRequested());
    }

    @Test
    void calculate_withReviewRequiredPullRequests_shouldCountAsAwaitingReview() {
        ReviewStatisticsCalculator calculator = new ReviewStatisticsCalculator(new StatisticsConfiguration(0, 0));
        ReviewSummary reviewSummary = aReviewSummary().withReviewStatus(ReviewStatus.REVIEW_REQUIRED).build();
        List<PullRequest> list = List.of(aPullRequest().withReviewSummary(reviewSummary).build());

        ReviewStatistics statistics = calculator.calculate(list);

        assertEquals(1, statistics.awaitingReview());
        assertEquals(0, statistics.changesRequested());
        assertEquals(0, statistics.approved());
    }

    @Test
    void calculate_withChangesRequestedPullRequests_shouldCountAsChangesRequested() {
        ReviewStatisticsCalculator calculator = new ReviewStatisticsCalculator(new StatisticsConfiguration(0, 0));
        ReviewSummary reviewSummary = aReviewSummary().withReviewStatus(ReviewStatus.CHANGES_REQUESTED).build();
        List<PullRequest> list = List.of(aPullRequest().withReviewSummary(reviewSummary).build());

        ReviewStatistics statistics = calculator.calculate(list);

        assertEquals(0, statistics.awaitingReview());
        assertEquals(1, statistics.changesRequested());
        assertEquals(0, statistics.approved());
    }

    @Test
    void calculate_withApprovedPullRequests_shouldCountAsApproved() {
        ReviewStatisticsCalculator calculator = new ReviewStatisticsCalculator(new StatisticsConfiguration(0, 0));
        ReviewSummary reviewSummary = aReviewSummary().withReviewStatus(ReviewStatus.APPROVED).build();

        List<PullRequest> list = List.of(aPullRequest().withReviewSummary(reviewSummary).build());

        ReviewStatistics statistics = calculator.calculate(list);

        assertEquals(0, statistics.awaitingReview());
        assertEquals(0, statistics.changesRequested());
        assertEquals(1, statistics.approved());
    }
}
