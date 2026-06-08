package com.pullrequesttracker.presentation.cli.state;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class StateManager {
    private final Map<SnapshotKey<?>, Snapshot<?>> snapshots = new HashMap<>();

    @SuppressWarnings("unchecked")
    public <T> Snapshot<T> get(SnapshotKey<T> snapshotKey) {
        return (Snapshot<T>) snapshots.getOrDefault(snapshotKey, Snapshot.empty());
    }

    public <T> void set(SnapshotKey<T> snapshotKey, T data) {
        snapshots.put(snapshotKey, new Snapshot<>(data, Instant.now()));
    }

    public <T> boolean isStale(SnapshotKey<T> snapshotKey) {
        return get(snapshotKey).isOlderThan(snapshotKey.ttl());
    }
}
