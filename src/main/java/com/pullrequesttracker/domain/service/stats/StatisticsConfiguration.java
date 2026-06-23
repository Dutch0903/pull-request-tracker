package com.pullrequesttracker.domain.service.stats;

public record StatisticsConfiguration(int staleThresholdDays, int recentActivityMaxEntries) {
}
