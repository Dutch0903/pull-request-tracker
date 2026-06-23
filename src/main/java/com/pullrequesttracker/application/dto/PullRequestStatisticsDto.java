package com.pullrequesttracker.application.dto;

public record PullRequestStatisticsDto(int open, int drafts, int stale) {
    public static PullRequestStatisticsDto empty() {
        return new PullRequestStatisticsDto(0, 0, 0);
    }
}
