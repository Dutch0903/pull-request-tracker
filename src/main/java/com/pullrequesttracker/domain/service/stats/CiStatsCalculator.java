package com.pullrequesttracker.domain.service.stats;

import com.pullrequesttracker.domain.model.PullRequest;
import com.pullrequesttracker.domain.type.CiStatus;
import com.pullrequesttracker.domain.type.PullRequestStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CiStatsCalculator extends StatsCalculator {

    public CiStatsCalculator(StatsConfiguration config) {
        super(config);
    }

    public CiStats calculate(List<PullRequest> prs) {
        int passing = 0;
        int failing = 0;
        int pending = 0;

        for (PullRequest pr : prs) {
            if (pr.getStatus() != PullRequestStatus.OPEN) {
                continue;
            }
            if (pr.getCiStatus() == CiStatus.PASSED) {
                passing++;
            } else if (pr.getCiStatus() == CiStatus.FAILED) {
                failing++;
            } else {
                pending++;
            }
        }

        return new CiStats(passing, failing, pending);
    }

    public record CiStats(int passing, int failing, int pending) {
    }
}
