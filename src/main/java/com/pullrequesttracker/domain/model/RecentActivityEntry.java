package com.pullrequesttracker.domain.model;

import com.pullrequesttracker.domain.type.RecentActivityType;

import java.time.Instant;

public record RecentActivityEntry(String author, RecentActivityType type, int prNumber, Instant occurredAt) {
}
