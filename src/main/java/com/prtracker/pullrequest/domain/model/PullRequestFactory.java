package com.prtracker.pullrequest.domain.model;

import com.prtracker.pullrequest.domain.enums.CiStatus;
import com.prtracker.pullrequest.domain.enums.PullRequestStatus;
import com.prtracker.shared.kernel.CodeRepositoryId;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
public class PullRequestFactory {

    public PullRequest open(CodeRepositoryId codeRepositoryId, int externalId, String title,
                            String author, boolean draft, CiStatus ciStatus, int commentCount,
                            List<String> labels, List<String> requestedReviewers,
                            Instant createdAt, Instant updatedAt) {
        return new PullRequest(PullRequestId.create(), codeRepositoryId, externalId, title, author,
                draft, PullRequestStatus.OPEN, ciStatus, commentCount, labels, requestedReviewers,
                List.of(), createdAt, updatedAt, null, null);
    }

    public PullRequest reconstitute(PullRequestId id, CodeRepositoryId codeRepositoryId, int externalId,
                                    String title, String author, boolean draft, PullRequestStatus status,
                                    CiStatus ciStatus, int commentCount, List<String> labels,
                                    List<String> requestedReviewers, List<Review> reviews,
                                    Instant createdAt, Instant updatedAt, String mergedBy, Instant mergedAt) {
        return new PullRequest(id, codeRepositoryId, externalId, title, author, draft, status, ciStatus,
                commentCount, labels, requestedReviewers, reviews, createdAt, updatedAt, mergedBy, mergedAt);
    }
}
