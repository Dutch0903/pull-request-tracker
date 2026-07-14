package com.pullrequesttracker.infrastructure.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "view-refresh")
public record ViewRefreshProperties(long intervalMs) {
    public ViewRefreshProperties {
        if (intervalMs <= 0)
            throw new IllegalArgumentException("intervalMs must be positive");
    }
}
