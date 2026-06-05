package com.pullrequesttracker.testfixtures.domain.model;

import com.pullrequesttracker.domain.model.CodeRepository;
import com.pullrequesttracker.domain.model.RepositoryAccess;
import com.pullrequesttracker.domain.type.Platform;
import com.pullrequesttracker.domain.valueobject.CodeRepositoryId;
import com.pullrequesttracker.domain.valueobject.FullName;
import com.pullrequesttracker.domain.valueobject.TokenId;

import java.time.Instant;

public class CodeRepositoryTestBuilder {
    private CodeRepositoryId id = CodeRepositoryId.create();
    private FullName fullName = new FullName("account", "repo");
    private Platform platform = Platform.GITHUB;
    private RepositoryAccess access = new RepositoryAccess.Authenticated(TokenId.create());
    private Instant lastCheckedAt = null;

    public static CodeRepositoryTestBuilder aCodeRepository() {
        return new CodeRepositoryTestBuilder();
    }

    public CodeRepositoryTestBuilder withId(CodeRepositoryId id) {
        this.id = id;
        return this;
    }

    public CodeRepositoryTestBuilder withFullName(FullName fullName) {
        this.fullName = fullName;
        return this;
    }

    public CodeRepositoryTestBuilder withPlatform(Platform platform) {
        this.platform = platform;
        return this;
    }

    public CodeRepositoryTestBuilder withAccess(RepositoryAccess access) {
        this.access = access;
        return this;
    }

    public CodeRepositoryTestBuilder withTokenId(TokenId tokenId) {
        this.access = tokenId != null
                ? new RepositoryAccess.Authenticated(tokenId)
                : new RepositoryAccess.Public();
        return this;
    }

    public CodeRepositoryTestBuilder withLastCheckedAt(Instant lastCheckedAt) {
        this.lastCheckedAt = lastCheckedAt;
        return this;
    }

    public CodeRepository build() {
        CodeRepository repo = new CodeRepository(id, fullName, platform, access);
        if (lastCheckedAt != null) {
            repo.recordChecked(lastCheckedAt);
        }
        return repo;
    }
}
