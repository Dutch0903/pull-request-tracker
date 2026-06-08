package com.pullrequesttracker.presentation.cli.state;

import java.time.Duration;
import java.time.Instant;

public record Snapshot<T>(T data, Instant capturedAt) {
    public static <T> Snapshot<T> empty() {
        return new Snapshot<>(null, Instant.EPOCH);
    }

    public boolean isOlderThan(Duration maxAge) {
        return Duration.between(Instant.now(), capturedAt).compareTo(maxAge) > 0;
    }

    public T getOrElse(T defaultValue) {
        return data != null ? data : defaultValue;
    }
}
