package com.pullrequesttracker.domain.service.stats;

import com.pullrequesttracker.domain.model.PullRequest;
import com.pullrequesttracker.domain.type.PullRequestStatus;
import com.pullrequesttracker.domain.type.ReviewStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReviewStatsCalculator extends StatsCalculator {

    public ReviewStatsCalculator(StatsConfiguration config) {
        super(config);
    }

    public ReviewStats calculate(List<PullRequest> prs) {
        int awaitingReview = 0;
        int changesRequested = 0;
        int approved = 0;

        for (PullRequest pr : prs) {
            if (pr.getStatus() != PullRequestStatus.OPEN) {
                continue;
            }
            ReviewStatus reviewStatus = pr.getReviewSummary().reviewStatus();
            if (reviewStatus == ReviewStatus.REVIEW_REQUIRED) {
                awaitingReview++;
            } else if (reviewStatus == ReviewStatus.CHANGES_REQUESTED) {
                changesRequested++;
            } else if (reviewStatus == ReviewStatus.APPROVED) {
                approved++;
            }
        }

        return new ReviewStats(awaitingReview, changesRequested, approved);
    }

    public record ReviewStats(int awaitingReview, int changesRequested, int approved) {
    }
}
