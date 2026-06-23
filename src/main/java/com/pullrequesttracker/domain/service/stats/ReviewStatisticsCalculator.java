package com.pullrequesttracker.domain.service.stats;

import com.pullrequesttracker.domain.model.PullRequest;
import com.pullrequesttracker.domain.valueobject.ReviewStatistics;
import com.pullrequesttracker.domain.type.PullRequestStatus;
import com.pullrequesttracker.domain.type.ReviewStatus;
import java.util.List;

public class ReviewStatisticsCalculator extends StatisticsCalculator {

    public ReviewStatisticsCalculator(StatisticsConfiguration config) {
        super(config);
    }

    public ReviewStatistics calculate(List<PullRequest> prs) {
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

        return new ReviewStatistics(awaitingReview, changesRequested, approved);
    }
}
