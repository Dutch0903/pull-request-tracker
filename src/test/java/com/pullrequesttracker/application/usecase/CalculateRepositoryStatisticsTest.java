package com.pullrequesttracker.application.usecase;

import com.pullrequesttracker.application.dto.CodeRepositoryStatisticsDto;
import com.pullrequesttracker.application.dto.RecentActivityEntryDto;
import com.pullrequesttracker.domain.model.PullRequest;
import com.pullrequesttracker.domain.model.RecentActivityEntry;
import com.pullrequesttracker.domain.repository.PullRequestRepository;
import com.pullrequesttracker.domain.service.stats.ContinuousIntegrationStatisticsCalculator;
import com.pullrequesttracker.domain.service.stats.PullRequestStatisticsCalculator;
import com.pullrequesttracker.domain.service.stats.RecentActivityCalculator;
import com.pullrequesttracker.domain.service.stats.ReviewStatisticsCalculator;
import com.pullrequesttracker.domain.valueobject.ContinuousIntegrationStatistics;
import com.pullrequesttracker.domain.valueobject.PullRequestStatistics;
import com.pullrequesttracker.domain.valueobject.ReviewStatistics;
import com.pullrequesttracker.domain.valueobject.CodeRepositoryId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;

import static com.pullrequesttracker.testfixtures.domain.model.RecentActivityEntryTestBuilder.aRecentActivityEntry;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CalculateRepositoryStatisticsTest {
    @Mock
    private PullRequestRepository pullRequestRepository;

    @Mock
    private ContinuousIntegrationStatisticsCalculator continuousIntegrationStatisticsCalculator;

    @Mock
    private PullRequestStatisticsCalculator pullRequestStatisticsCalculator;

    @Mock
    private RecentActivityCalculator recentActivityCalculator;

    @Mock
    private ReviewStatisticsCalculator reviewStatisticsCalculator;

    @Mock
    private PullRequest pullRequest;

    private final Instant fixedNow = Instant.parse("2026-06-17T10:00:00Z");
    private final Clock clock = Clock.fixed(fixedNow, ZoneId.of("UTC"));

    private CalculateCodeRepositoryStatistics calculateRepositoryStatistics;

    @BeforeEach
    public void setup() {
        calculateRepositoryStatistics = new CalculateCodeRepositoryStatistics(pullRequestRepository,
                continuousIntegrationStatisticsCalculator, pullRequestStatisticsCalculator, recentActivityCalculator,
                reviewStatisticsCalculator, clock);
    }

    @Test
    void execute_whenNoPullRequestExists_shouldReturnEmptyStatistics() {
        CodeRepositoryId codeRepositoryId = CodeRepositoryId.create();
        when(pullRequestRepository.findAllByCodeRepositoryId(codeRepositoryId)).thenReturn(List.of());

        CodeRepositoryStatisticsDto result = calculateRepositoryStatistics.execute(codeRepositoryId);

        assertEquals(codeRepositoryId.value(), result.codeRepositoryId());

        assertEquals(0, result.pullRequestStatistics().open());
        assertEquals(0, result.pullRequestStatistics().drafts());
        assertEquals(0, result.pullRequestStatistics().stale());
        assertEquals(0, result.continuousIntegrationStatistics().passing());
        assertEquals(0, result.continuousIntegrationStatistics().failing());
        assertEquals(0, result.continuousIntegrationStatistics().pending());
        assertEquals(0, result.reviewStatistics().awaitingReview());
        assertEquals(0, result.reviewStatistics().changesRequested());
        assertEquals(0, result.reviewStatistics().approved());
        assertTrue(result.recentActivity().isEmpty());
        assertEquals(fixedNow, result.calculatedAt());
    }

    @Test
    void execute_whenPullRequestsExist_shouldMapCalculatorResultsToDtos() {
        CodeRepositoryId codeRepositoryId = CodeRepositoryId.create();
        RecentActivityEntry recentActivityEntry = aRecentActivityEntry().build();
        var stubPrs = List.of(pullRequest);
        when(pullRequestRepository.findAllByCodeRepositoryId(codeRepositoryId)).thenReturn(stubPrs);
        when(pullRequestStatisticsCalculator.calculate(stubPrs, fixedNow))
                .thenReturn(new PullRequestStatistics(3, 1, 2));
        when(continuousIntegrationStatisticsCalculator.calculate(stubPrs))
                .thenReturn(new ContinuousIntegrationStatistics(4, 1, 0));
        when(reviewStatisticsCalculator.calculate(stubPrs)).thenReturn(new ReviewStatistics(2, 1, 3));
        when(recentActivityCalculator.calculate(stubPrs)).thenReturn(List.of(recentActivityEntry));

        CodeRepositoryStatisticsDto result = calculateRepositoryStatistics.execute(codeRepositoryId);

        assertEquals(3, result.pullRequestStatistics().open());
        assertEquals(1, result.pullRequestStatistics().drafts());
        assertEquals(2, result.pullRequestStatistics().stale());
        assertEquals(4, result.continuousIntegrationStatistics().passing());
        assertEquals(1, result.continuousIntegrationStatistics().failing());
        assertEquals(0, result.continuousIntegrationStatistics().pending());
        assertEquals(2, result.reviewStatistics().awaitingReview());
        assertEquals(1, result.reviewStatistics().changesRequested());
        assertEquals(3, result.reviewStatistics().approved());
        assertEquals(fixedNow, result.calculatedAt());

        assertEquals(1, result.recentActivity().size());

        RecentActivityEntryDto recentActivityEntryDto = result.recentActivity().getFirst();

        assertEquals(recentActivityEntry.author(), recentActivityEntryDto.author());
        assertEquals(recentActivityEntry.pullRequestNumber(), recentActivityEntryDto.pullRequestNumber());
        assertEquals(recentActivityEntry.occurredAt(), recentActivityEntryDto.occurredAt());
        assertEquals(recentActivityEntry.type().name(), recentActivityEntryDto.type().name());
    }
}
