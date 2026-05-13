package com.prtracker.pullrequest.domain.model;

import com.prtracker.pullrequest.domain.enums.CiStatus;
import com.prtracker.pullrequest.domain.enums.PullRequestStatus;
import com.prtracker.pullrequest.domain.enums.ReviewStatus;
import com.prtracker.shared.kernel.CodeRepositoryId;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
public class PullRequestFactory {

    public PullRequest create(CodeRepositoryId codeRepositoryId, PullRequestSyncData syncData) {
        return new PullRequest(PullRequestId.create(), codeRepositoryId, syncData.externalId(), syncData.title(),
                syncData.author(), syncData.isDraft(), syncData.status(), syncData.ciStatus(), syncData.commentCount(),
                syncData.labels(), syncData.requestedReviewers(), syncData.reviews(), syncData.reviewStatus(),
                syncData.createdAt(), syncData.updatedAt(), syncData.mergedBy(), syncData.mergedAt());
    }

    public PullRequest reconstitute(PullRequestId id, CodeRepositoryId codeRepositoryId, int externalId, String title,
            String author, boolean draft, PullRequestStatus status, CiStatus ciStatus, int commentCount,
            List<String> labels, List<String> requestedReviewers, List<Review> reviews, ReviewStatus reviewStatus,
            Instant createdAt, Instant updatedAt, String mergedBy, Instant mergedAt) {
        return new PullRequest(id, codeRepositoryId, externalId, title, author, draft, status, ciStatus, commentCount,
                labels, requestedReviewers, reviews, reviewStatus, createdAt, updatedAt, mergedBy, mergedAt);
    }
}
