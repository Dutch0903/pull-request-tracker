package com.pullrequesttracker.domain.valueobject;

import java.time.Instant;
import java.util.Objects;

public record MergeInfo(Actor mergedBy, Instant mergedAt) {
    public MergeInfo {
        Objects.requireNonNull(mergedBy, "Merged by must not be null");
        Objects.requireNonNull(mergedAt, "Merged at must not be null");
    }
}
