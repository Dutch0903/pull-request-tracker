package com.pullrequesttracker.infrastructure.external.github.graphql.dto;

import java.time.Instant;

public record Review(String state, Actor author, Instant submittedAt) {
}
