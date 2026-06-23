package com.pullrequesttracker.domain.service.stats;

import com.pullrequesttracker.domain.model.PullRequest;
import com.pullrequesttracker.domain.valueobject.ContinuousIntegrationStatistics;
import com.pullrequesttracker.domain.type.CiStatus;
import com.pullrequesttracker.domain.type.PullRequestStatus;
import java.util.List;

public class ContinuousIntegrationStatisticsCalculator extends StatisticsCalculator {

    public ContinuousIntegrationStatisticsCalculator(StatisticsConfiguration config) {
        super(config);
    }

    public ContinuousIntegrationStatistics calculate(List<PullRequest> prs) {
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

        return new ContinuousIntegrationStatistics(passing, failing, pending);
    }
}
