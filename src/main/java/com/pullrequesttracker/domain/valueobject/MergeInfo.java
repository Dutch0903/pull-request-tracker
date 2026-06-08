package com.pullrequesttracker.domain.valueobject;

import java.time.Instant;
import java.util.Objects;

public record MergeInfo(String mergedBy, Instant mergedAt) {
    public MergeInfo {
        Objects.requireNonNull(mergedBy, "Merged by must not be null");
        if (mergedBy.isBlank())
            throw new IllegalArgumentException("Merged by must not be blank");
        Objects.requireNonNull(mergedAt, "Merged at must not be null");
    }
}
