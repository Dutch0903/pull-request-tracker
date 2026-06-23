package com.pullrequesttracker.application.usecase;

import com.pullrequesttracker.application.dto.ContinuousIntegrationStatisticsDto;
import com.pullrequesttracker.application.dto.PullRequestStatisticsDto;
import com.pullrequesttracker.application.dto.RecentActivityEntryDto;
import com.pullrequesttracker.application.dto.RecentActivityType;
import com.pullrequesttracker.application.dto.CodeRepositoryStatisticsDto;
import com.pullrequesttracker.application.dto.ReviewStatisticsDto;
import com.pullrequesttracker.domain.model.PullRequest;
import com.pullrequesttracker.domain.repository.PullRequestRepository;
import com.pullrequesttracker.domain.service.stats.ContinuousIntegrationStatisticsCalculator;
import com.pullrequesttracker.domain.service.stats.PullRequestStatisticsCalculator;
import com.pullrequesttracker.domain.service.stats.RecentActivityCalculator;
import com.pullrequesttracker.domain.service.stats.ReviewStatisticsCalculator;
import com.pullrequesttracker.domain.valueobject.ContinuousIntegrationStatistics;
import com.pullrequesttracker.domain.valueobject.PullRequestStatistics;
import com.pullrequesttracker.domain.valueobject.ReviewStatistics;
import com.pullrequesttracker.domain.valueobject.CodeRepositoryId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Instant;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CalculateCodeRepositoryStatistics {
    private final PullRequestRepository pullRequestRepository;
    private final ContinuousIntegrationStatisticsCalculator continuousIntegrationStatisticsCalculator;
    private final PullRequestStatisticsCalculator pullRequestStatisticsCalculator;
    private final RecentActivityCalculator recentActivityCalculator;
    private final ReviewStatisticsCalculator reviewStatisticsCalculator;
    private final Clock clock;

    public CodeRepositoryStatisticsDto execute(CodeRepositoryId codeRepositoryId) {
        List<PullRequest> prs = pullRequestRepository.findAllByCodeRepositoryId(codeRepositoryId);
        Instant now = Instant.now(clock);

        if (prs.isEmpty()) {
            return CodeRepositoryStatisticsDto.empty(codeRepositoryId.value(), now);
        }

        PullRequestStatistics pullRequestStatistics = pullRequestStatisticsCalculator.calculate(prs, now);
        ContinuousIntegrationStatistics ciStatistics = continuousIntegrationStatisticsCalculator.calculate(prs);
        ReviewStatistics reviewStatistics = reviewStatisticsCalculator.calculate(prs);
        List<RecentActivityEntryDto> recentActivity = recentActivityCalculator.calculate(prs).stream()
                .map(e -> new RecentActivityEntryDto(e.author(), RecentActivityType.valueOf(e.type().name()),
                        e.pullRequestNumber(), e.occurredAt()))
                .toList();

        return new CodeRepositoryStatisticsDto(codeRepositoryId.value(),
                new PullRequestStatisticsDto(pullRequestStatistics.open(), pullRequestStatistics.drafts(), pullRequestStatistics.stale()),
                new ContinuousIntegrationStatisticsDto(ciStatistics.passing(), ciStatistics.failing(), ciStatistics.pending()),
                new ReviewStatisticsDto(reviewStatistics.awaitingReview(), reviewStatistics.changesRequested(), reviewStatistics.approved()),
                recentActivity, now);
    }
}
