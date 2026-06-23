package com.pullrequesttracker.domain.service.stats;

import com.pullrequesttracker.domain.model.PullRequest;
import com.pullrequesttracker.domain.valueobject.PullRequestStatistics;
import com.pullrequesttracker.domain.type.PullRequestStatus;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class PullRequestStatisticsCalculator extends StatisticsCalculator {

    public PullRequestStatisticsCalculator(StatisticsConfiguration config) {
        super(config);
    }

    public PullRequestStatistics calculate(List<PullRequest> prs, Instant now) {
        Instant staleThreshold = now.minus(config.staleThresholdDays(), ChronoUnit.DAYS);

        int open = 0;
        int drafts = 0;
        int stale = 0;

        for (PullRequest pr : prs) {
            if (pr.isDraft()) {
                drafts++;
            }
            if (pr.getStatus() == PullRequestStatus.OPEN) {
                if (!pr.isDraft()) {
                    open++;
                }
                if (pr.getUpdatedAt().isBefore(staleThreshold)) {
                    stale++;
                }
            }
        }

        return new PullRequestStatistics(open, drafts, stale);
    }
}
