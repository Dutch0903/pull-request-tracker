package com.pullrequesttracker.domain.service.stats;

public record StatsConfiguration(int staleThresholdDays, int recentActivityMaxEntries) {
}
