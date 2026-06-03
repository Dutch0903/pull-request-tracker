package com.pullrequesttracker.infrastructure.persistence.dto;

import java.time.Instant;

public record ReviewDto(String reviewer, String state, Instant submittedAt) {
}
