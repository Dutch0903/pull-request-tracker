package com.pullrequesttracker.application.dto;

import com.pullrequesttracker.domain.type.CiStatus;
import com.pullrequesttracker.domain.type.ReviewStatus;
import com.pullrequesttracker.domain.valueobject.PullRequestId;

import java.time.Instant;
import java.util.List;

public record PullRequestListItemDto(PullRequestId id, int externalId, String title, String author, Instant updatedAt,
        boolean draft, CiStatus ciStatus, int approvalCount, ReviewStatus reviewStatus, int commentCount,
        List<String> labels, String repositoryFullName, String url) {
}
