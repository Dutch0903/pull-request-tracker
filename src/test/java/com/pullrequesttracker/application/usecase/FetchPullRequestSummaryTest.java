package com.pullrequesttracker.application.usecase;

import com.pullrequesttracker.application.dto.PullRequestSummaryDto;
import com.pullrequesttracker.domain.model.PullRequest;
import com.pullrequesttracker.domain.repository.PullRequestRepository;
import com.pullrequesttracker.domain.type.CiStatus;
import com.pullrequesttracker.domain.type.ReviewStatus;
import com.pullrequesttracker.domain.valueobject.ReviewSummary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;

import static com.pullrequesttracker.testfixtures.domain.model.PullRequestTestBuilder.aPullRequest;
import static com.pullrequesttracker.testfixtures.domain.valueobject.ReviewSummaryTestBuilder.aReviewSummary;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FetchPullRequestSummaryTest {
    @Mock
    private PullRequestRepository pullRequestRepository;

    private final Instant fixedNow = Instant.parse("2026-06-17T10:00:00Z");
    private final Clock clock = Clock.fixed(fixedNow, ZoneId.of("UTC"));
    private final int staleThresholdDays = 3;

    private FetchPullRequestSummary fetchPullRequestSummary;

    @BeforeEach
    public void setUp() {
        fetchPullRequestSummary = new FetchPullRequestSummary(pullRequestRepository, staleThresholdDays, clock);
    }

    @Test
    void execute_whenNoPullRequestsExists_shouldReturnAllZeros() {
        PullRequestSummaryDto result = fetchPullRequestSummary.execute();

        assertEquals(0, result.open());
        assertEquals(0, result.readyForReview());
        assertEquals(0, result.drafts());
        assertEquals(0, result.stale());
        assertEquals(0, result.failingContinuousIntegration());
    }

    @Test
    void execute_shouldReturnAnAccurateSummary() {
        Instant staleInstant = Instant.parse("2026-06-10T00:00:00Z");

        ReviewSummary approvedReviewSummary = aReviewSummary().withReviewStatus(ReviewStatus.APPROVED).build();
        ReviewSummary reviewRequiredReviewSummary = aReviewSummary().withReviewStatus(ReviewStatus.REVIEW_REQUIRED).build();

        PullRequest pr1 = aPullRequest().withDraft(true).withUpdatedAt(fixedNow).build();
        PullRequest pr2 = aPullRequest().withDraft(true).withUpdatedAt(staleInstant).build();
        PullRequest pr3 = aPullRequest().withDraft(false).withReviewSummary(reviewRequiredReviewSummary)
                .withCiStatus(CiStatus.PENDING).withUpdatedAt(fixedNow).build();
        PullRequest pr4 = aPullRequest().withDraft(false).withReviewSummary(approvedReviewSummary)
                .withCiStatus(CiStatus.FAILED).withUpdatedAt(fixedNow).build();
        PullRequest pr5 = aPullRequest().withDraft(false).withReviewSummary(approvedReviewSummary)
                .withCiStatus(CiStatus.PENDING).withUpdatedAt(staleInstant).build();

        when(pullRequestRepository.findAllOpen()).thenReturn(List.of(pr1, pr2, pr3, pr4, pr5));

        PullRequestSummaryDto result = fetchPullRequestSummary.execute();

        assertEquals(3, result.open());
        assertEquals(1, result.readyForReview());
        assertEquals(2, result.drafts());
        assertEquals(2, result.stale());
        assertEquals(1, result.failingContinuousIntegration());
    }
}
