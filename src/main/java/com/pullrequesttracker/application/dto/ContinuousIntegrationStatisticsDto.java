package com.pullrequesttracker.application.dto;

public record ContinuousIntegrationStatisticsDto(int passing, int failing, int pending) {
    public static ContinuousIntegrationStatisticsDto empty() {
        return new ContinuousIntegrationStatisticsDto(0, 0, 0);
    }
}
