package com.prtracker.pullrequest.domain.model;

import com.prtracker.pullrequest.domain.enums.ReviewStatus;

import java.time.Instant;

public record Review(String reviewer, ReviewStatus state, Instant submittedAt) {
}
