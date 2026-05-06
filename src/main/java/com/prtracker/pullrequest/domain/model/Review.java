package com.prtracker.pullrequest.domain.model;

import com.prtracker.pullrequest.domain.enums.ReviewState;

import java.time.Instant;

public record Review(
        String reviewer,
        ReviewState state,
        Instant submittedAt
) {
}
