package com.pullrequesttracker.application.dto;

public record ReviewStatisticsDto(int awaitingReview, int changesRequested, int approved) {
    public static ReviewStatisticsDto empty() {
        return new ReviewStatisticsDto(0, 0, 0);
    }
}
