package com.pullrequesttracker.domain.model;

import com.pullrequesttracker.domain.sync.PullRequestSyncData;
import com.pullrequesttracker.domain.type.CiStatus;
import com.pullrequesttracker.domain.type.PullRequestStatus;
import com.pullrequesttracker.domain.valueobject.CodeRepositoryId;
import com.pullrequesttracker.domain.valueobject.MergeInfo;
import com.pullrequesttracker.domain.valueobject.PullRequestId;
import com.pullrequesttracker.domain.valueobject.ReviewSummary;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Component
public class PullRequestFactory {

    public PullRequest create(CodeRepositoryId codeRepositoryId, PullRequestSyncData syncData) {
        ReviewSummary reviewSummary = new ReviewSummary(syncData.reviews(), syncData.reviewStatus());
        MergeInfo mergeInfo = syncData.mergedBy() != null
                ? new MergeInfo(syncData.mergedBy(), syncData.mergedAt())
                : null;
        return new PullRequest(
                new PullRequestId(UUID.randomUUID()), codeRepositoryId, syncData.externalId(),
                syncData.author(), syncData.createdAt(), syncData.title(), syncData.isDraft(),
                syncData.status(), syncData.ciStatus(), syncData.labels(), reviewSummary,
                syncData.commentCount(), mergeInfo, syncData.updatedAt()
        );
    }

    public PullRequest reconstitute(PullRequestId id, CodeRepositoryId codeRepositoryId, int externalId,
                                    String author, Instant createdAt, String title, boolean draft,
                                    PullRequestStatus status, CiStatus ciStatus, List<String> labels,
                                    ReviewSummary reviewSummary, int commentCount,
                                    @Nullable MergeInfo mergeInfo, Instant updatedAt) {
        return new PullRequest(id, codeRepositoryId, externalId, author, createdAt, title, draft,
                status, ciStatus, labels, reviewSummary, commentCount, mergeInfo, updatedAt);
    }
}
