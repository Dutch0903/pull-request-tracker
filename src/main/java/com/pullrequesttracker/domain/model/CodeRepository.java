package com.pullrequesttracker.domain.model;

import com.pullrequesttracker.domain.type.Platform;
import com.pullrequesttracker.domain.valueobject.CodeRepositoryId;
import com.pullrequesttracker.domain.valueobject.FullName;
import com.pullrequesttracker.domain.valueobject.TokenId;
import lombok.Getter;
import org.jspecify.annotations.Nullable;

import java.time.Instant;
import java.util.Objects;

@Getter
public class CodeRepository {
    private final CodeRepositoryId id;
    private final FullName fullName;
    private final Platform platform;
    @Nullable
    private final TokenId tokenId;
    @Nullable
    private Instant lastCheckedAt;

    public CodeRepository(CodeRepositoryId id, FullName fullName, Platform platform, @Nullable TokenId tokenId) {
        Objects.requireNonNull(id, "Id must not be null");
        Objects.requireNonNull(fullName, "Full name must not be null");
        Objects.requireNonNull(platform, "Platform must not be null");
        this.id = id;
        this.fullName = fullName;
        this.platform = platform;
        this.tokenId = tokenId;
    }

    public void recordChecked(Instant checkedAt) {
        Objects.requireNonNull(checkedAt, "Checked at must not be null");
        this.lastCheckedAt = checkedAt;
    }
}
