package com.prtracker.pullrequest.adapter.out.github.graphql.dto;

import java.time.Instant;

public record Review(String state, Actor author, Instant submittedAt) {
}
