package com.prtracker.shared.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "repository-check")
public record RepositoryCheckProperties(long intervalMs, int threadPoolSize) {
    public RepositoryCheckProperties {
        if (intervalMs <= 0)
            throw new IllegalArgumentException("intervalMs must be positive");
        if (threadPoolSize <= 0)
            throw new IllegalArgumentException("threadPoolSize must be positive");
    }
}
