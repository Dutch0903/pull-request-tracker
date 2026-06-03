package com.pullrequesttracker.domain.service.stats;

import com.pullrequesttracker.domain.model.PullRequest;
import com.pullrequesttracker.domain.type.PullRequestStatus;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class OpenPrStatsCalculator extends StatsCalculator {

    public OpenPrStatsCalculator(StatsConfiguration config) {
        super(config);
    }

    public OpenPrStats calculate(List<PullRequest> prs) {
        Instant staleThreshold = Instant.now().minus(config.staleThresholdDays(), ChronoUnit.DAYS);

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

        return new OpenPrStats(open, drafts, stale);
    }

    public record OpenPrStats(int open, int drafts, int stale) {
    }
}
