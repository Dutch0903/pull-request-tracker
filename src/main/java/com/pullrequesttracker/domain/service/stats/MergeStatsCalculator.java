package com.pullrequesttracker.domain.service.stats;

import com.pullrequesttracker.domain.model.PullRequest;
import com.pullrequesttracker.domain.type.PullRequestStatus;
import com.pullrequesttracker.domain.valueobject.MergeInfo;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Component
public class MergeStatsCalculator extends StatsCalculator {

    public MergeStatsCalculator(StatsConfiguration config) {
        super(config);
    }

    public MergeStats calculate(List<PullRequest> prs) {
        Optional<PullRequest> lastMerged = prs.stream()
                .filter(pr -> pr.getStatus() == PullRequestStatus.MERGED && pr.getMergeInfo().isPresent())
                .max(Comparator.comparing(pr -> pr.getMergeInfo().get().mergedAt()));

        return lastMerged
                .map(pr -> {
                    MergeInfo info = pr.getMergeInfo().get();
                    return new MergeStats(info.mergedAt(), info.mergedBy());
                })
                .orElse(new MergeStats(null, null));
    }

    public record MergeStats(Instant lastMergedAt, String lastMergedBy) {
    }
}
