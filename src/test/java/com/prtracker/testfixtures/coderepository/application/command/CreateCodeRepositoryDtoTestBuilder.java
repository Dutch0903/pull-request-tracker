package com.prtracker.testfixtures.coderepository.application.command;

import com.prtracker.coderepository.application.command.CreateCodeRepositoryDto;

import java.util.UUID;

public class CreateCodeRepositoryDtoTestBuilder {
    private UUID tokenId = UUID.randomUUID();
    private String repositoryReference = "owner/repo";

    public static CreateCodeRepositoryDtoTestBuilder aCreateCodeRepositoryDto() {
        return new CreateCodeRepositoryDtoTestBuilder();
    }

    public CreateCodeRepositoryDtoTestBuilder withTokenId(UUID tokenId) {
        this.tokenId = tokenId;
        return this;
    }

    public CreateCodeRepositoryDtoTestBuilder withRepositoryReference(String repositoryReference) {
        this.repositoryReference = repositoryReference;
        return this;
    }

    public CreateCodeRepositoryDto build() {
        return new CreateCodeRepositoryDto(repositoryReference, tokenId);
    }
}
