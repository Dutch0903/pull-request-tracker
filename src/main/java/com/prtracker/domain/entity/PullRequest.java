package com.prtracker.domain.entity;

import com.prtracker.domain.enums.PullRequestStatus;
import com.prtracker.domain.valueobject.CodeRepositoryId;
import com.prtracker.domain.valueobject.PullRequestId;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@Getter
@AllArgsConstructor
public class PullRequest {
    private PullRequestId id;
    private CodeRepositoryId codeRepositoryId;
    private int externalId;
    private String title;
    private String author;
    private PullRequestStatus status;
    private Instant updatedAt;
}
