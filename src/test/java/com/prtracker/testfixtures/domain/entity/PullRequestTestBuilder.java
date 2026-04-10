package com.prtracker.testfixtures.domain.entity;

import com.prtracker.domain.entity.CodeRepository;
import com.prtracker.domain.entity.PullRequest;
import com.prtracker.domain.enums.PullRequestStatus;
import com.prtracker.domain.valueobject.CodeRepositoryId;
import com.prtracker.domain.valueobject.PullRequestId;

import java.time.Instant;

public class PullRequestTestBuilder {
    private PullRequestId id = PullRequestId.create();
    private CodeRepositoryId codeRepositoryId = CodeRepositoryId.create();
    private int externalId = 1;
    private String title = "title";
    private String author = "author";
    private PullRequestStatus status = PullRequestStatus.OPEN;
    private Instant updatedAt = Instant.now();

    public static PullRequestTestBuilder aPullRequest() {
        return new PullRequestTestBuilder();
    }

    public PullRequestTestBuilder withId(PullRequestId id) {
        this.id = id;
        return this;
    }

    public PullRequestTestBuilder withCodeRepositoryId(CodeRepositoryId codeRepositoryId) {
        this.codeRepositoryId = codeRepositoryId;
        return this;
    }

    public PullRequestTestBuilder withExternalId(int externalId) {
        this.externalId = externalId;
        return this;
    }

    public PullRequestTestBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public PullRequestTestBuilder withAuthor(String author) {
        this.author = author;
        return this;
    }

    public PullRequestTestBuilder withStatus(PullRequestStatus status) {
        this.status = status;
        return this;
    }

    public PullRequestTestBuilder withUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public PullRequest build() {
        return new  PullRequest(id, codeRepositoryId, externalId, title, author, status, updatedAt);
    }


}
