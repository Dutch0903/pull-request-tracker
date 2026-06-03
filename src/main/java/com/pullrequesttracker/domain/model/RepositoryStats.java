package com.pullrequesttracker.domain.model;

import com.pullrequesttracker.domain.valueobject.CodeRepositoryId;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;
import java.util.List;

@Getter
@AllArgsConstructor
public class RepositoryStats {
    private CodeRepositoryId codeRepositoryId;
    // Open PRs
    private int openPrCount;
    private int draftCount;
    private int stalePrCount;
    // CI status
    private int passingCiCount;
    private int failingCiCount;
    private int pendingCiCount;
    // Review status
    private int awaitingReviewCount;
    private int changesRequestedCount;
    private int approvedCount;
    // Last merge
    private Instant lastMergedAt;
    private String lastMergedBy;
    // Recent activity
    private List<RecentActivityEntry> recentActivity;
    private Instant calculatedAt;
}
