package com.pullrequesttracker.application.dto;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record CodeRepositoryStatisticsDto(UUID codeRepositoryId, PullRequestStatisticsDto pullRequestStatistics,
        ContinuousIntegrationStatisticsDto continuousIntegrationStatistics, ReviewStatisticsDto reviewStatistics,
        List<RecentActivityEntryDto> recentActivity, Instant calculatedAt) {
    public static CodeRepositoryStatisticsDto empty(UUID codeRepositoryId, Instant calculatedAt) {
        return new CodeRepositoryStatisticsDto(codeRepositoryId, PullRequestStatisticsDto.empty(),
                ContinuousIntegrationStatisticsDto.empty(), ReviewStatisticsDto.empty(), List.of(), calculatedAt);
    }
}
