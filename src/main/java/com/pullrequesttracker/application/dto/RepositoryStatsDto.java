package com.pullrequesttracker.application.dto;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record RepositoryStatsDto(
        UUID codeRepositoryId,
        int openPrCount,
        int draftCount,
        int stalePrCount,
        int passingCiCount,
        int failingCiCount,
        int pendingCiCount,
        int awaitingReviewCount,
        int changesRequestedCount,
        int approvedCount,
        List<RecentActivityEntryDto> recentActivity,
        Instant calculatedAt
) {
}
