package com.pullrequesttracker.application.dto;

public record PullRequestSummaryDto(int open, int readyForReview, int drafts, int stale, int failingCi) {
}
