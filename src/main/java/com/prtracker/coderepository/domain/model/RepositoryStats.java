package com.prtracker.coderepository.domain.model;

import com.prtracker.shared.kernel.CodeRepositoryId;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@Getter
@AllArgsConstructor
public class RepositoryStats {
    private CodeRepositoryId codeRepositoryId;
    private int openPrCount;
    private int draftCount;
    private int awaitingReviewCount;
    private double avgAgeDays;
    private int stalePrCount;
    private int failingCiCount;
    private Instant lastMergedAt;
    private String lastMergedBy;
    private int contributorsThisWeek;
    private Instant calculatedAt;
}
