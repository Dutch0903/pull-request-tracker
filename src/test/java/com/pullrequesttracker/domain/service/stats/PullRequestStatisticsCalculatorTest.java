package com.pullrequesttracker.domain.service.stats;

import com.pullrequesttracker.domain.model.PullRequest;
import com.pullrequesttracker.domain.model.PullRequestState;
import com.pullrequesttracker.domain.valueobject.MergeInfo;
import com.pullrequesttracker.domain.valueobject.PullRequestStatistics;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.pullrequesttracker.testfixtures.domain.model.PullRequestTestBuilder.aPullRequest;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PullRequestStatisticsCalculatorTest {

    @Test
    void calculate_withEmptyList_shouldReturnAllZeros() {
        PullRequestStatisticsCalculator calculator = new PullRequestStatisticsCalculator(
                new StatisticsConfiguration(0, 0));

        PullRequestStatistics statistics = calculator.calculate(List.of(), Instant.now());

        assertEquals(0, statistics.drafts());
        assertEquals(0, statistics.open());
        assertEquals(0, statistics.stale());
    }

    @Test
    void calculate_whenNonOpenPullRequestsArePresent_shouldIgnoreThem() {
        PullRequestStatisticsCalculator calculator = new PullRequestStatisticsCalculator(
                new StatisticsConfiguration(0, 0));
        List<PullRequest> list = List.of(aPullRequest().withState(new PullRequestState.Ignored()).build(),
                aPullRequest().withState(new PullRequestState.Merged(new MergeInfo("user", Instant.now()))).build(),
                aPullRequest().withState(new PullRequestState.Closed()).build(),
                aPullRequest().withState(new PullRequestState.Open()).withDraft(false).build());

        PullRequestStatistics statistics = calculator.calculate(list, Instant.now());

        assertEquals(0, statistics.drafts());
        assertEquals(1, statistics.open());
        assertEquals(0, statistics.stale());
    }

    @Test
    void calculate_withDraftPullRequests_shouldCountThemAsDrafts() {
        PullRequestStatisticsCalculator calculator = new PullRequestStatisticsCalculator(
                new StatisticsConfiguration(0, 0));
        List<PullRequest> list = List.of(aPullRequest().withDraft(true).build(), aPullRequest().withDraft(true).build(),
                aPullRequest().withDraft(false).build());

        PullRequestStatistics statistics = calculator.calculate(list, Instant.now());

        assertEquals(2, statistics.drafts());
        assertEquals(1, statistics.open());
        assertEquals(0, statistics.stale());
    }

    @Test
    void calculate_whenStaleThresholdIsZero_shouldNotMarkAnyPullRequestAsStale() {
        PullRequestStatisticsCalculator calculator = new PullRequestStatisticsCalculator(
                new StatisticsConfiguration(0, 0));
        Instant now = Instant.now();
        List<PullRequest> list = List.of(aPullRequest().withUpdatedAt(now.minus(10, ChronoUnit.DAYS)).build(),
                aPullRequest().withUpdatedAt(now.minus(1, ChronoUnit.DAYS)).build());

        PullRequestStatistics statistics = calculator.calculate(list, now);

        assertEquals(0, statistics.stale());
    }

    @Test
    void calculate_whenPullRequestIsOlderThanStaleThreshold_shouldMarkItAsStale() {
        PullRequestStatisticsCalculator calculator = new PullRequestStatisticsCalculator(
                new StatisticsConfiguration(3, 0));
        Instant now = Instant.now();
        List<PullRequest> list = List.of(aPullRequest().withUpdatedAt(now.minus(10, ChronoUnit.DAYS)).build(),
                aPullRequest().withUpdatedAt(now.minus(1, ChronoUnit.DAYS)).build());

        PullRequestStatistics statistics = calculator.calculate(list, now);

        assertEquals(1, statistics.stale());
    }
}
