package com.pullrequesttracker.domain.model;

import java.time.Instant;

public sealed interface SyncStatus permits SyncStatus.NeverSynced, SyncStatus.SyncedAt {

    record NeverSynced() implements SyncStatus {}

    record SyncedAt(Instant at) implements SyncStatus {}
}
