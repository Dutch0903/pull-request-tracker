package com.prtracker.testfixtures.coderepository.adapter.out.persistence;

import com.prtracker.coderepository.adapter.out.persistence.CodeRepositoryDto;
import com.prtracker.coderepository.domain.model.CodeRepositoryStatus;

import java.util.UUID;

public class CodeRepositoryDtoTestBuilder {
    private UUID id = UUID.randomUUID();
    private String owner = "owner";
    private String name = "name";
    private String status = CodeRepositoryStatus.ACTIVE.toString();
    private UUID tokenId = UUID.randomUUID();

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

    public CodeRepositoryDtoTestBuilder withStatus(CodeRepositoryStatus status) {
        this.status = status.toString();
        return this;
    }

    public CodeRepositoryDtoTestBuilder withTokenId(UUID tokenId) {
        this.tokenId = tokenId;
        return this;
    }

    public CodeRepositoryDto build() {
        return new CodeRepositoryDto(id, owner, name, status, tokenId);
    }
}
