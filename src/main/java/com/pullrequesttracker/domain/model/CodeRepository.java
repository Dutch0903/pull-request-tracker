package com.pullrequesttracker.domain.model;

import com.pullrequesttracker.domain.type.Platform;
import com.pullrequesttracker.domain.valueobject.CodeRepositoryId;
import com.pullrequesttracker.domain.valueobject.FullName;
import com.pullrequesttracker.domain.valueobject.TokenId;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

public class CodeRepository {
    private final CodeRepositoryId id;
    private final FullName fullName;
    private final Platform platform;
    private final RepositoryAccess access;
    private SyncStatus syncStatus;

    public CodeRepository(CodeRepositoryId id, FullName fullName, Platform platform, RepositoryAccess access) {
        Objects.requireNonNull(id, "Id must not be null");
        Objects.requireNonNull(fullName, "Full name must not be null");
        Objects.requireNonNull(platform, "Platform must not be null");
        Objects.requireNonNull(access, "Access must not be null");
        this.id = id;
        this.fullName = fullName;
        this.platform = platform;
        this.access = access;
        this.syncStatus = new SyncStatus.NeverSynced();
    }

    public void recordChecked(Instant checkedAt) {
        Objects.requireNonNull(checkedAt, "Checked at must not be null");
        this.syncStatus = new SyncStatus.SyncedAt(checkedAt);
    }

    public CodeRepositoryId getId() { return id; }

    public FullName getFullName() { return fullName; }

    public Platform getPlatform() { return platform; }

    public RepositoryAccess getAccess() { return access; }

    public SyncStatus getSyncStatus() { return syncStatus; }

    public Optional<TokenId> getTokenId() {
        return switch (access) {
            case RepositoryAccess.Authenticated a -> Optional.of(a.tokenId());
            case RepositoryAccess.Public p -> Optional.empty();
        };
    }

    public Optional<Instant> getLastCheckedAt() {
        return switch (syncStatus) {
            case SyncStatus.SyncedAt s -> Optional.of(s.at());
            case SyncStatus.NeverSynced n -> Optional.empty();
        };
    }
}
