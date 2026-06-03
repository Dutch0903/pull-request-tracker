package com.pullrequesttracker.application.query;

import java.util.UUID;

public record CodeRepositoryProjection(UUID id, String owner, String name) {
}
