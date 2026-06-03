package com.pullrequesttracker.domain.valueobject;

import java.util.Objects;
import java.util.UUID;

public record PullRequestId(UUID value) {
    public PullRequestId {
        Objects.requireNonNull(value, "Pull request id cannot be null");
    }

    public static PullRequestId from(UUID id) {
        return new PullRequestId(id);
    }

    public static PullRequestId create() {
        return new PullRequestId(UUID.randomUUID());
    }
}
