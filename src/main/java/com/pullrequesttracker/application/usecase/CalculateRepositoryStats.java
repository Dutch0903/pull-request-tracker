package com.pullrequesttracker.application.usecase;

import com.pullrequesttracker.domain.model.PullRequest;
import com.pullrequesttracker.domain.model.RecentActivityEntry;
import com.pullrequesttracker.domain.model.RepositoryStats;
import com.pullrequesttracker.domain.repository.PullRequestRepository;
import com.pullrequesttracker.domain.service.stats.CiStatsCalculator;
import com.pullrequesttracker.domain.service.stats.MergeStatsCalculator;
import com.pullrequesttracker.domain.service.stats.OpenPrStatsCalculator;
import com.pullrequesttracker.domain.service.stats.RecentActivityCalculator;
import com.pullrequesttracker.domain.service.stats.ReviewStatsCalculator;
import com.pullrequesttracker.domain.valueobject.CodeRepositoryId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CalculateRepositoryStats {
    private final PullRequestRepository pullRequestRepository;
    private final CiStatsCalculator ciStatsCalculator;
    private final MergeStatsCalculator mergeStatsCalculator;
    private final OpenPrStatsCalculator openPrStatsCalculator;
    private final RecentActivityCalculator recentActivityCalculator;
    private final ReviewStatsCalculator reviewStatsCalculator;

    public RepositoryStats execute(CodeRepositoryId codeRepositoryId) {
        List<PullRequest> prs = pullRequestRepository.findAllByCodeRepositoryId(codeRepositoryId);

        OpenPrStatsCalculator.OpenPrStats open = openPrStatsCalculator.calculate(prs);
        CiStatsCalculator.CiStats ci = ciStatsCalculator.calculate(prs);
        ReviewStatsCalculator.ReviewStats review = reviewStatsCalculator.calculate(prs);
        MergeStatsCalculator.MergeStats merge = mergeStatsCalculator.calculate(prs);
        List<RecentActivityEntry> recentActivity = recentActivityCalculator.calculate(prs);

        return new RepositoryStats(codeRepositoryId, open.open(), open.drafts(), open.stale(), ci.passing(),
                ci.failing(), ci.pending(), review.awaitingReview(), review.changesRequested(), review.approved(),
                merge.lastMergedAt(), merge.lastMergedBy(), recentActivity, Instant.now());
    }
}
