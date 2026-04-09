package com.prtracker.domain.valueobject;

import com.prtracker.domain.exceptions.InvalidPullRequestIdException;

import java.util.UUID;

public record PullRequestId(UUID value) {
    public PullRequestId {
        if (value == null) {
            throw new InvalidPullRequestIdException("Pull request ID cannot be null");
        }
    }

    public static PullRequestId from(UUID id) {
        return new PullRequestId(id);
    }

    public static PullRequestId create() {
        return new PullRequestId(UUID.randomUUID());
    }
}
