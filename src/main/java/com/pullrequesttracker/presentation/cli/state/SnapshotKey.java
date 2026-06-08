package com.pullrequesttracker.presentation.cli.state;

import java.time.Duration;

public record SnapshotKey<T>(String name, Duration ttl) {
}
