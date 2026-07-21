package com.pullrequesttracker.application.dto;

import com.pullrequesttracker.domain.type.CiStatus;
import com.pullrequesttracker.domain.type.ReviewStatus;
import com.pullrequesttracker.domain.valueobject.PullRequestId;

import java.time.Instant;

public record AttentionItemDto(
        PullRequestId id,
        int externalId,
        String title,
        String repositoryFullName,
        ReviewStatus reviewStatus,
        CiStatus ciStatus,
        Instant createdAt,
        String url
) {}
