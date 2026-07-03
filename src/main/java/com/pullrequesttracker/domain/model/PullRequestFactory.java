package com.pullrequesttracker.domain.model;

import com.pullrequesttracker.domain.sync.PullRequestSyncData;
import com.pullrequesttracker.domain.type.CiStatus;
import com.pullrequesttracker.domain.valueobject.Actor;
import com.pullrequesttracker.domain.valueobject.CodeRepositoryId;
import com.pullrequesttracker.domain.valueobject.PullRequestId;
import com.pullrequesttracker.domain.valueobject.Title;
import com.pullrequesttracker.domain.valueobject.ReviewSummary;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class PullRequestFactory {

    public static PullRequest create(CodeRepositoryId codeRepositoryId, PullRequestSyncData syncData) {
        ReviewSummary reviewSummary = new ReviewSummary(syncData.reviews(), syncData.reviewStatus());
        return new PullRequest(new PullRequestId(UUID.randomUUID()), codeRepositoryId, syncData.externalId(),
                syncData.author(), syncData.createdAt(), new Title(syncData.title()), syncData.isDraft(),
                syncData.state(), syncData.ciStatus(), syncData.labels(), reviewSummary, syncData.commentCount(),
                syncData.updatedAt());
    }

    public static PullRequest reconstitute(PullRequestId id, CodeRepositoryId codeRepositoryId, int externalId,
            Actor author, Instant createdAt, Title title, boolean draft, PullRequestState state, CiStatus ciStatus,
            List<String> labels, ReviewSummary reviewSummary, int commentCount, Instant updatedAt) {
        return new PullRequest(id, codeRepositoryId, externalId, author, createdAt, title, draft, state, ciStatus,
                labels, reviewSummary, commentCount, updatedAt);
    }
}
