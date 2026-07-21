package com.pullrequesttracker.domain.service.stats;

import com.pullrequesttracker.domain.model.PullRequest;
import com.pullrequesttracker.domain.model.PullRequestState;
import com.pullrequesttracker.domain.model.RecentActivityEntry;
import com.pullrequesttracker.domain.type.RecentActivityType;
import com.pullrequesttracker.domain.type.ReviewStatus;
import com.pullrequesttracker.domain.valueobject.Actor;
import com.pullrequesttracker.domain.valueobject.MergeInfo;
import com.pullrequesttracker.domain.valueobject.ReviewSummary;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static com.pullrequesttracker.testfixtures.domain.model.PullRequestTestBuilder.aPullRequest;
import static com.pullrequesttracker.testfixtures.domain.valueobject.ReviewSummaryTestBuilder.aReviewSummary;
import static com.pullrequesttracker.testfixtures.domain.valueobject.ReviewTestBuilder.aReview;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RecentActivityCalculatorTest {

    private static final StatisticsConfiguration UNLIMITED = new StatisticsConfiguration(0, Integer.MAX_VALUE);

    @Test
    void calculate_withEmptyList_shouldReturnEmptyList() {
        RecentActivityCalculator calculator = new RecentActivityCalculator(UNLIMITED);

        List<RecentActivityEntry> result = calculator.calculate(List.of());

        assertTrue(result.isEmpty());
    }

    @Test
    void calculate_withMergedPullRequest_shouldIncludeMergedEvent() {
        Instant mergedAt = Instant.parse("2026-01-01T00:00:00Z");
        PullRequest pr = aPullRequest().withMergeInfo(new MergeInfo(Actor.from("merger"), mergedAt)).withExternalId(42)
                .build();
        RecentActivityCalculator calculator = new RecentActivityCalculator(UNLIMITED);

        List<RecentActivityEntry> result = calculator.calculate(List.of(pr));

        assertEquals(1, result.size());
        assertEquals("merger", result.get(0).author());
        assertEquals(RecentActivityType.MERGED, result.get(0).type());
        assertEquals(42, result.get(0).pullRequestNumber());
        assertEquals(mergedAt, result.get(0).occurredAt());
    }

    @Test
    void calculate_withOpenPullRequest_shouldIncludeOpenedEvent() {
        PullRequest pr = aPullRequest().withAuthor("author").withState(new PullRequestState.Open()).withExternalId(42)
                .build();
        RecentActivityCalculator calculator = new RecentActivityCalculator(UNLIMITED);

        List<RecentActivityEntry> result = calculator.calculate(List.of(pr));

        assertEquals(1, result.size());
        assertEquals("author", result.get(0).author());
        assertEquals(RecentActivityType.OPENED, result.get(0).type());
        assertEquals(42, result.get(0).pullRequestNumber());
    }

    @Test
    void calculate_withApprovedReview_shouldIncludeApprovedEvent() {
        Instant submittedAt = Instant.parse("2026-01-01T00:00:00Z");
        ReviewSummary reviewSummary = aReviewSummary().withReviews(List.of(aReview().withReviewer("reviewer")
                .withStatus(ReviewStatus.APPROVED).withSubmittedAt(submittedAt).build())).build();

        PullRequest pr = aPullRequest().withExternalId(42).withReviewSummary(reviewSummary).build();
        RecentActivityCalculator calculator = new RecentActivityCalculator(UNLIMITED);

        List<RecentActivityEntry> approvedEvents = calculator.calculate(List.of(pr)).stream()
                .filter(e -> e.type() == RecentActivityType.APPROVED).toList();

        assertEquals(1, approvedEvents.size());
        assertEquals("reviewer", approvedEvents.get(0).author());
        assertEquals(42, approvedEvents.get(0).pullRequestNumber());
        assertEquals(submittedAt, approvedEvents.get(0).occurredAt());
    }

    @Test
    void calculate_withChangesRequestedReview_shouldNotIncludeApprovedEvent() {
        ReviewSummary reviewSummary = aReviewSummary().withReviews(List.of(aReview().withStatus(ReviewStatus.CHANGES_REQUESTED).build())).build();
        PullRequest pr = aPullRequest()
                .withReviewSummary(reviewSummary).build();
        RecentActivityCalculator calculator = new RecentActivityCalculator(UNLIMITED);

        List<RecentActivityEntry> result = calculator.calculate(List.of(pr));

        assertTrue(result.stream().noneMatch(e -> e.type() == RecentActivityType.APPROVED));
    }

    @Test
    void calculate_whenNonMergedAndNonOpenPullRequestsArePresent_shouldIgnoreThem() {
        List<PullRequest> list = List.of(aPullRequest().withState(new PullRequestState.Ignored()).build(),
                aPullRequest().withState(new PullRequestState.Closed()).build());
        RecentActivityCalculator calculator = new RecentActivityCalculator(UNLIMITED);

        List<RecentActivityEntry> result = calculator.calculate(list);

        assertTrue(result.isEmpty());
    }

    @Test
    void calculate_shouldSortEntriesByOccurredAtDescending() {
        Instant first = Instant.parse("2026-01-03T00:00:00Z");
        Instant second = Instant.parse("2026-01-02T00:00:00Z");
        Instant third = Instant.parse("2026-01-01T00:00:00Z");
        List<PullRequest> list = List.of(aPullRequest().withMergeInfo(new MergeInfo(Actor.from("user"), third)).build(),
                aPullRequest().withMergeInfo(new MergeInfo(Actor.from("user"), first)).build(),
                aPullRequest().withMergeInfo(new MergeInfo(Actor.from("user"), second)).build());
        RecentActivityCalculator calculator = new RecentActivityCalculator(UNLIMITED);

        List<RecentActivityEntry> result = calculator.calculate(list);

        assertEquals(first, result.get(0).occurredAt());
        assertEquals(second, result.get(1).occurredAt());
        assertEquals(third, result.get(2).occurredAt());
    }

    @Test
    void calculate_shouldLimitResultsByMaxEntries() {
        Instant base = Instant.parse("2026-01-01T00:00:00Z");
        List<PullRequest> list = List.of(aPullRequest().withMergeInfo(new MergeInfo(Actor.from("user"), base)).build(),
                aPullRequest().withMergeInfo(new MergeInfo(Actor.from("user"), base)).build(),
                aPullRequest().withMergeInfo(new MergeInfo(Actor.from("user"), base)).build(),
                aPullRequest().withMergeInfo(new MergeInfo(Actor.from("user"), base)).build(),
                aPullRequest().withMergeInfo(new MergeInfo(Actor.from("user"), base)).build());
        RecentActivityCalculator calculator = new RecentActivityCalculator(new StatisticsConfiguration(0, 3));

        List<RecentActivityEntry> result = calculator.calculate(list);

        assertEquals(3, result.size());
    }
}
