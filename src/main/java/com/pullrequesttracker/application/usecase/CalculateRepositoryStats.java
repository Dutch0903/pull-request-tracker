package com.pullrequesttracker.application.usecase;

import com.pullrequesttracker.application.dto.RecentActivityEntryDto;
import com.pullrequesttracker.application.dto.RecentActivityType;
import com.pullrequesttracker.application.dto.RepositoryStatsDto;
import com.pullrequesttracker.domain.model.PullRequest;
import com.pullrequesttracker.domain.repository.PullRequestRepository;
import com.pullrequesttracker.domain.service.stats.CiStatsCalculator;
import com.pullrequesttracker.domain.service.stats.OpenPrStatsCalculator;
import com.pullrequesttracker.domain.service.stats.RecentActivityCalculator;
import com.pullrequesttracker.domain.service.stats.ReviewStatsCalculator;
import com.pullrequesttracker.domain.valueobject.CodeRepositoryId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Instant;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CalculateRepositoryStats {
    private final PullRequestRepository pullRequestRepository;
    private final CiStatsCalculator ciStatsCalculator;
    private final OpenPrStatsCalculator openPrStatsCalculator;
    private final RecentActivityCalculator recentActivityCalculator;
    private final ReviewStatsCalculator reviewStatsCalculator;
    private final Clock clock;

    public RepositoryStatsDto execute(CodeRepositoryId codeRepositoryId) {
        List<PullRequest> prs = pullRequestRepository.findAllByCodeRepositoryId(codeRepositoryId);
        Instant now = Instant.now(clock);

        OpenPrStatsCalculator.OpenPrStats open = openPrStatsCalculator.calculate(prs, now);
        CiStatsCalculator.CiStats ci = ciStatsCalculator.calculate(prs);
        ReviewStatsCalculator.ReviewStats review = reviewStatsCalculator.calculate(prs);
        List<RecentActivityEntryDto> recentActivity = recentActivityCalculator.calculate(prs).stream()
                .map(e -> new RecentActivityEntryDto(
                        e.author(),
                        RecentActivityType.valueOf(e.type().name()),
                        e.prNumber(),
                        e.occurredAt()
                ))
                .toList();

        return new RepositoryStatsDto(
                codeRepositoryId.value(),
                open.open(), open.drafts(), open.stale(),
                ci.passing(), ci.failing(), ci.pending(),
                review.awaitingReview(), review.changesRequested(), review.approved(),
                recentActivity,
                now
        );
    }
}
