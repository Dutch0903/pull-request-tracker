package com.pullrequesttracker.domain.service.stats;

import com.pullrequesttracker.domain.model.PullRequest;
import com.pullrequesttracker.domain.model.RecentActivityEntry;
import com.pullrequesttracker.domain.type.PullRequestStatus;
import com.pullrequesttracker.domain.type.RecentActivityType;
import com.pullrequesttracker.domain.type.ReviewStatus;
import com.pullrequesttracker.domain.valueobject.Review;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RecentActivityCalculator extends StatisticsCalculator {

    public RecentActivityCalculator(StatisticsConfiguration config) {
        super(config);
    }

    public List<RecentActivityEntry> calculate(List<PullRequest> prs) {
        List<RecentActivityEntry> events = new ArrayList<>();

        for (PullRequest pr : prs) {
            if (pr.getStatus() == PullRequestStatus.MERGED) {
                pr.getMergeInfo().ifPresent(info -> events.add(new RecentActivityEntry(info.mergedBy().value(),
                        RecentActivityType.MERGED, pr.getExternalId(), info.mergedAt())));
            }
            if (pr.getStatus() == PullRequestStatus.OPEN) {
                events.add(new RecentActivityEntry(pr.getAuthor().value(), RecentActivityType.OPENED, pr.getExternalId(),
                        pr.getCreatedAt()));
            }
            for (Review review : pr.getReviewSummary().reviews()) {
                if (review.status() == ReviewStatus.APPROVED) {
                    events.add(new RecentActivityEntry(review.reviewer().value(), RecentActivityType.APPROVED,
                            pr.getExternalId(), review.submittedAt()));
                }

                if (review.status() == ReviewStatus.CHANGES_REQUESTED) {
                    events.add(new RecentActivityEntry(review.reviewer().value(), RecentActivityType.CHANGES_REQUESTED,
                            pr.getExternalId(), review.submittedAt()));
                }
            }
        }

        return events.stream().sorted(Comparator.comparing(RecentActivityEntry::occurredAt).reversed())
                .limit(config.recentActivityMaxEntries()).toList();
    }
}
