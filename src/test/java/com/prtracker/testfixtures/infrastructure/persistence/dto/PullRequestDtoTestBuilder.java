package com.prtracker.testfixtures.infrastructure.persistence.dto;

import com.prtracker.domain.enums.PullRequestStatus;
import com.prtracker.infrastructure.persistence.dto.PullRequestDto;

import java.time.Instant;
import java.util.UUID;

public class PullRequestDtoTestBuilder {
    private UUID id = UUID.randomUUID();
    private UUID codeRepositoryId = UUID.randomUUID();
    private int externalId = 1;
    private String title = "title";
    private String author = "author";
    private String status = PullRequestStatus.OPEN.name();
    private Instant updatedAt = Instant.now();

    public static PullRequestDtoTestBuilder aPullRequestDto() {
        return new PullRequestDtoTestBuilder();
    }

    public PullRequestDtoTestBuilder withId(UUID id) {
        this.id = id;
        return this;
    }

    public PullRequestDtoTestBuilder withCodeRepositoryId(UUID codeRepositoryId) {
        this.codeRepositoryId = codeRepositoryId;
        return this;
    }

    public PullRequestDtoTestBuilder withExternalId(int externalId) {
        this.externalId = externalId;
        return this;
    }

    public PullRequestDtoTestBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public PullRequestDtoTestBuilder withAuthor(String author) {
        this.author = author;
        return this;
    }

    public PullRequestDtoTestBuilder withStatus(PullRequestStatus status) {
        this.status = status.name();
        return this;
    }

    public PullRequestDtoTestBuilder withUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public PullRequestDto build() {
        return new PullRequestDto(id, codeRepositoryId, externalId, title, author, status, updatedAt);
    }
}
