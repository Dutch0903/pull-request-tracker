package com.pullrequesttracker.application.usecase;

import com.pullrequesttracker.application.dto.PullRequestSummaryDto;
import com.pullrequesttracker.domain.model.PullRequest;
import com.pullrequesttracker.domain.repository.PullRequestRepository;
import com.pullrequesttracker.domain.type.CiStatus;
import com.pullrequesttracker.domain.type.ReviewStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
public class FetchPullRequestSummary {
    private final PullRequestRepository pullRequestRepository;
    private final int staleThresholdDays;

    public FetchPullRequestSummary(PullRequestRepository pullRequestRepository,
            @Value("${dashboard.stale-threshold-days:7}") int staleThresholdDays) {
        this.pullRequestRepository = pullRequestRepository;
        this.staleThresholdDays = staleThresholdDays;
    }

    public PullRequestSummaryDto execute() {
        Instant staleThreshold = Instant.now().minus(staleThresholdDays, ChronoUnit.DAYS);

        int open = 0, drafts = 0, readyForReview = 0, stale = 0, failingCi = 0;

        for (PullRequest pr : pullRequestRepository.findAllOpen()) {
            if (pr.isDraft()) {
                drafts++;
            } else {
                open++;
                if (pr.getReviewSummary().reviewStatus() == ReviewStatus.REVIEW_REQUIRED)
                    readyForReview++;
                if (pr.getCiStatus() == CiStatus.FAILED)
                    failingCi++;
            }
            if (pr.getUpdatedAt().isBefore(staleThreshold))
                stale++;
        }

        return new PullRequestSummaryDto(open, readyForReview, drafts, stale, failingCi);
    }
}
