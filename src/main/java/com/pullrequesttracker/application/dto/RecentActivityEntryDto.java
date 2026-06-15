package com.pullrequesttracker.application.dto;

import java.time.Instant;

public record RecentActivityEntryDto(String author, RecentActivityType type, int prNumber, Instant occurredAt) {
}
