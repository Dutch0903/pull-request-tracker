package com.pullrequesttracker.domain.service.stats;

import com.pullrequesttracker.domain.model.PullRequest;
import com.pullrequesttracker.domain.model.PullRequestState;
import com.pullrequesttracker.domain.type.CiStatus;
import com.pullrequesttracker.domain.valueobject.ContinuousIntegrationStatistics;
import com.pullrequesttracker.domain.valueobject.Actor;
import com.pullrequesttracker.domain.valueobject.MergeInfo;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static com.pullrequesttracker.testfixtures.domain.model.PullRequestTestBuilder.aPullRequest;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ContinuousIntegrationStatisticsCalculatorTest {

    @Test
    void calculate_withEmptyList_shouldReturnAllZeros() {
        ContinuousIntegrationStatisticsCalculator calculator = new ContinuousIntegrationStatisticsCalculator(
                new StatisticsConfiguration(0, 0));

        ContinuousIntegrationStatistics statistics = calculator.calculate(List.of());

        assertEquals(0, statistics.passing());
        assertEquals(0, statistics.failing());
        assertEquals(0, statistics.pending());
    }

    @Test
    void calculate_whenNonOpenPullRequestsArePresent_shouldIgnoreThem() {
        ContinuousIntegrationStatisticsCalculator calculator = new ContinuousIntegrationStatisticsCalculator(
                new StatisticsConfiguration(0, 0));
        List<PullRequest> list = List.of(
                aPullRequest().withState(new PullRequestState.Ignored()).withCiStatus(CiStatus.PASSED).build(),
                aPullRequest().withState(new PullRequestState.Merged(new MergeInfo(Actor.from("user"), Instant.now())))
                        .withCiStatus(CiStatus.PASSED).build(),
                aPullRequest().withState(new PullRequestState.Closed()).withCiStatus(CiStatus.PASSED).build(),
                aPullRequest().withState(new PullRequestState.Open()).withCiStatus(CiStatus.FAILED).build());

        ContinuousIntegrationStatistics statistics = calculator.calculate(list);

        assertEquals(0, statistics.passing());
        assertEquals(0, statistics.pending());
        assertEquals(1, statistics.failing());
    }

    @Test
    void calculate_whenCiStatusIsPassed_shouldCountAsPassing() {
        ContinuousIntegrationStatisticsCalculator calculator = new ContinuousIntegrationStatisticsCalculator(
                new StatisticsConfiguration(0, 0));
        List<PullRequest> list = List.of(aPullRequest().withCiStatus(CiStatus.PASSED).build());

        ContinuousIntegrationStatistics statistics = calculator.calculate(list);

        assertEquals(1, statistics.passing());
        assertEquals(0, statistics.failing());
        assertEquals(0, statistics.pending());
    }

    @Test
    void calculate_whenCiStatusIsFailed_shouldCountAsFailing() {
        ContinuousIntegrationStatisticsCalculator calculator = new ContinuousIntegrationStatisticsCalculator(
                new StatisticsConfiguration(0, 0));
        List<PullRequest> list = List.of(aPullRequest().withCiStatus(CiStatus.FAILED).build());

        ContinuousIntegrationStatistics statistics = calculator.calculate(list);

        assertEquals(0, statistics.passing());
        assertEquals(1, statistics.failing());
        assertEquals(0, statistics.pending());
    }

    @Test
    void calculate_whenCiStatusIsPendingUnknownOrInProgress_shouldCountAsPending() {
        ContinuousIntegrationStatisticsCalculator calculator = new ContinuousIntegrationStatisticsCalculator(
                new StatisticsConfiguration(0, 0));
        List<PullRequest> list = List.of(aPullRequest().withCiStatus(CiStatus.PENDING).build(),
                aPullRequest().withCiStatus(CiStatus.UNKNOWN).build(),
                aPullRequest().withCiStatus(CiStatus.IN_PROGRESS).build());

        ContinuousIntegrationStatistics statistics = calculator.calculate(list);

        assertEquals(0, statistics.passing());
        assertEquals(0, statistics.failing());
        assertEquals(3, statistics.pending());
    }
}
