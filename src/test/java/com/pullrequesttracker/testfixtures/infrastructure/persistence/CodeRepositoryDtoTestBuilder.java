package com.pullrequesttracker.testfixtures.infrastructure.persistence;

import com.pullrequesttracker.infrastructure.persistence.dto.CodeRepositoryDto;

import java.util.UUID;

public class CodeRepositoryDtoTestBuilder {
    private UUID id = UUID.randomUUID();
    private String owner = "owner";
    private String name = "name";
    private String platform = "GITHUB";
    private UUID tokenId = UUID.randomUUID();
    private String lastCheckedAt = null;

    public static CodeRepositoryDtoTestBuilder aCodeRepositoryDto() {
        return new CodeRepositoryDtoTestBuilder();
    }

    public CodeRepositoryDtoTestBuilder withId(UUID id) {
        this.id = id;
        return this;
    }

    public CodeRepositoryDtoTestBuilder withOwner(String owner) {
        this.owner = owner;
        return this;
    }

    public CodeRepositoryDtoTestBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public CodeRepositoryDtoTestBuilder withPlatform(String platform) {
        this.platform = platform;
        return this;
    }

    public CodeRepositoryDtoTestBuilder withTokenId(UUID tokenId) {
        this.tokenId = tokenId;
        return this;
    }

    public CodeRepositoryDtoTestBuilder withLastCheckedAt(String lastCheckedAt) {
        this.lastCheckedAt = lastCheckedAt;
        return this;
    }

    public CodeRepositoryDto build() {
        return new CodeRepositoryDto(id, owner, name, platform, tokenId, lastCheckedAt);
    }
}
