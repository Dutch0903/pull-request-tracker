package com.pullrequesttracker.presentation.cli.component;

import dev.tamboui.toolkit.element.Element;

import java.time.Duration;
import java.time.Instant;

import static dev.tamboui.toolkit.Toolkit.text;

public class CountdownTimer {
    private final Instant lastRefreshedAt;
    private final Duration refreshInterval;

    public CountdownTimer(Instant lastRefreshedAt, Duration refreshInterval) {
        this.lastRefreshedAt = lastRefreshedAt;
        this.refreshInterval = refreshInterval;
    }

    public Element render() {
        Duration remaining = refreshInterval.minus(Duration.between(lastRefreshedAt, Instant.now()));
        if (remaining.isNegative())
            remaining = Duration.ZERO;
        return text(String.format("Next refresh: %d:%02d", remaining.toMinutes(), remaining.toSecondsPart()));
    }
}
