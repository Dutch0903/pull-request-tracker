package com.pullrequesttracker.application.usecase;

import com.pullrequesttracker.domain.model.PullRequest;
import com.pullrequesttracker.domain.model.RepositoryStats;
import com.pullrequesttracker.domain.repository.PullRequestRepository;
import com.pullrequesttracker.domain.service.stats.CiStatsCalculator;
import com.pullrequesttracker.domain.service.stats.CiStatsCalculator.CiStats;
import com.pullrequesttracker.domain.service.stats.MergeStatsCalculator;
import com.pullrequesttracker.domain.service.stats.MergeStatsCalculator.MergeStats;
import com.pullrequesttracker.domain.service.stats.OpenPrStatsCalculator;
import com.pullrequesttracker.domain.service.stats.OpenPrStatsCalculator.OpenPrStats;
import com.pullrequesttracker.domain.service.stats.RecentActivityCalculator;
import com.pullrequesttracker.domain.service.stats.ReviewStatsCalculator;
import com.pullrequesttracker.domain.service.stats.ReviewStatsCalculator.ReviewStats;
import com.pullrequesttracker.domain.valueobject.CodeRepositoryId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CalculateRepositoryStats {
    private final PullRequestRepository pullRequestRepository;
    private final OpenPrStatsCalculator openPrStatsCalculator;
    private final CiStatsCalculator ciStatsCalculator;
    private final ReviewStatsCalculator reviewStatsCalculator;
    private final MergeStatsCalculator mergeStatsCalculator;
    private final RecentActivityCalculator recentActivityCalculator;

    public RepositoryStats execute(CodeRepositoryId codeRepositoryId) {
        List<PullRequest> prs = pullRequestRepository.findAllByCodeRepositoryId(codeRepositoryId);

        OpenPrStats open = openPrStatsCalculator.calculate(prs);
        CiStats ci = ciStatsCalculator.calculate(prs);
        ReviewStats review = reviewStatsCalculator.calculate(prs);
        MergeStats merge = mergeStatsCalculator.calculate(prs);

        return new RepositoryStats(
                codeRepositoryId,
                open.open(),
                open.drafts(),
                open.stale(),
                ci.passing(),
                ci.failing(),
                ci.pending(),
                review.awaitingReview(),
                review.changesRequested(),
                review.approved(),
                merge.lastMergedAt(),
                merge.lastMergedBy(),
                recentActivityCalculator.calculate(prs),
                Instant.now());
    }
}
