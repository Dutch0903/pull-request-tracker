package com.pullrequesttracker.infrastructure.external.github.graphql.dto;

import java.time.Instant;

public record Review(String state, GitHubActor author, Instant submittedAt) {
}
