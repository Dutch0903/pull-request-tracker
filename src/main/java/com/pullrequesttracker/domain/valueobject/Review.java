package com.pullrequesttracker.domain.valueobject;

import com.pullrequesttracker.domain.type.ReviewStatus;

import java.time.Instant;
import java.util.Objects;

public record Review(String reviewer, ReviewStatus status, Instant submittedAt) {
    public Review {
        Objects.requireNonNull(reviewer, "Reviewer must not be null");
        if (reviewer.isBlank()) throw new IllegalArgumentException("Reviewer must not be blank");
        Objects.requireNonNull(status, "Review status must not be null");
        Objects.requireNonNull(submittedAt, "Submitted at must not be null");
    }
}
