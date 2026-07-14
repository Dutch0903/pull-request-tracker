package com.pullrequesttracker.presentation.cli.state;

import java.util.HashMap;
import java.util.Map;

public class StateManager {
    private final Map<SnapshotKey<?>, Object> data = new HashMap<>();

    @SuppressWarnings("unchecked")
    public <T> T get(SnapshotKey<T> key) {
        return (T) data.get(key);
    }

    @SuppressWarnings("unchecked")
    public <T> T getOrElse(SnapshotKey<T> key, T defaultValue) {
        T value = (T) data.get(key);
        return value != null ? value : defaultValue;
    }

    public <T> void set(SnapshotKey<T> key, T value) {
        data.put(key, value);
    }
}
