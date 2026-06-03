package com.pullrequesttracker.testfixtures.domain.model;

import com.pullrequesttracker.domain.model.CodeRepository;
import com.pullrequesttracker.domain.type.Platform;
import com.pullrequesttracker.domain.valueobject.CodeRepositoryId;
import com.pullrequesttracker.domain.valueobject.FullName;
import com.pullrequesttracker.domain.valueobject.TokenId;

import java.time.Instant;

public class CodeRepositoryTestBuilder {
    private CodeRepositoryId id = CodeRepositoryId.create();
    private FullName fullName = new FullName("account", "repo");
    private Platform platform = Platform.GITHUB;
    private TokenId tokenId = TokenId.create();
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

    public CodeRepositoryTestBuilder withTokenId(TokenId tokenId) {
        this.tokenId = tokenId;
        return this;
    }

    public CodeRepositoryTestBuilder withLastCheckedAt(Instant lastCheckedAt) {
        this.lastCheckedAt = lastCheckedAt;
        return this;
    }

    public CodeRepository build() {
        CodeRepository repo = new CodeRepository(id, fullName, platform, tokenId);
        if (lastCheckedAt != null) {
            repo.recordChecked(lastCheckedAt);
        }
        return repo;
    }
}
