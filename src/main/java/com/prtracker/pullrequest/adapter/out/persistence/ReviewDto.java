package com.prtracker.pullrequest.adapter.out.persistence;

import java.time.Instant;

public record ReviewDto(
        String reviewer,
        String state,
        Instant submittedAt
) {
}
