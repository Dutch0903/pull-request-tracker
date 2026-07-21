package com.pullrequesttracker.presentation.cli.navigation;

public record ViewRefreshConfiguration(long intervalMs) {
    public ViewRefreshConfiguration {
        if (intervalMs <= 0)
            throw new IllegalArgumentException("intervalMs must be positive");
    }
}
