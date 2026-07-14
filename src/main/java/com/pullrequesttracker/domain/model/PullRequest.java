package com.pullrequesttracker.domain.model;

import com.pullrequesttracker.domain.sync.PullRequestSyncData;
import com.pullrequesttracker.domain.type.CiStatus;
import com.pullrequesttracker.domain.type.PullRequestStatus;
import com.pullrequesttracker.domain.valueobject.CodeRepositoryId;
import com.pullrequesttracker.domain.valueobject.MergeInfo;
import com.pullrequesttracker.domain.valueobject.Actor;
import com.pullrequesttracker.domain.valueobject.PullRequestId;
import com.pullrequesttracker.domain.valueobject.Title;
import com.pullrequesttracker.domain.valueobject.Review;
import com.pullrequesttracker.domain.valueobject.ReviewSummary;
import lombok.Getter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Getter
public class PullRequest {
    private final PullRequestId id;
    private final CodeRepositoryId codeRepositoryId;
    private final int externalId;
    private final Actor author;
    private final Instant createdAt;
    private Title title;
    private boolean draft;
    private PullRequestState state;
    private CiStatus ciStatus;
    private List<String> labels;
    private ReviewSummary reviewSummary;
    private int commentCount;
    private Instant updatedAt;

    PullRequest(PullRequestId id, CodeRepositoryId codeRepositoryId, int externalId, Actor author, Instant createdAt,
            Title title, boolean draft, PullRequestState state, CiStatus ciStatus, List<String> labels,
            ReviewSummary reviewSummary, int commentCount, Instant updatedAt) {
        Objects.requireNonNull(id, "Pull request id must not be null");
        Objects.requireNonNull(codeRepositoryId, "Code repository id must not be null");
        if (externalId <= 0)
            throw new IllegalArgumentException("External id must be positive");
        Objects.requireNonNull(author, "Actor must not be null");
        Objects.requireNonNull(createdAt, "Created at must not be null");
        Objects.requireNonNull(state, "State must not be null");
        Objects.requireNonNull(reviewSummary, "Review summary must not be null");
        Objects.requireNonNull(updatedAt, "Updated at must not be null");

        this.id = id;
        this.codeRepositoryId = codeRepositoryId;
        this.externalId = externalId;
        this.author = author;
        this.createdAt = createdAt;
        this.title = title;
        this.draft = draft;
        this.state = state;
        this.reviewSummary = reviewSummary;
        this.updatedAt = updatedAt;
        setCiStatus(ciStatus);
        setLabels(labels);
        setCommentCount(commentCount);
    }

    public void sync(PullRequestSyncData syncData) {
        updateTitle(syncData.title(), syncData.updatedAt());
        updateCiStatus(syncData.ciStatus(), syncData.updatedAt());
        updateLabels(syncData.labels(), syncData.updatedAt());
        updateCommentCount(syncData.commentCount());
        syncData.reviews().forEach(this::addReview);
        reviewSummary.updateReviewStatus(syncData.reviewStatus());

        if (syncData.state() instanceof PullRequestState.Merged(MergeInfo mergeInfo)) {
            merge(mergeInfo);
        } else if (syncData.state() instanceof PullRequestState.Closed) {
            close(syncData.updatedAt());
        } else if (!syncData.isDraft()) {
            undraft(syncData.updatedAt());
        }
    }

    public PullRequestStatus getStatus() {
        return switch (state) {
            case PullRequestState.Open _ -> PullRequestStatus.OPEN;
            case PullRequestState.Merged _ -> PullRequestStatus.MERGED;
            case PullRequestState.Closed _ -> PullRequestStatus.CLOSED;
            case PullRequestState.Ignored _ -> PullRequestStatus.IGNORED;
        };
    }

    public Optional<MergeInfo> getMergeInfo() {
        return switch (state) {
            case PullRequestState.Merged m -> Optional.of(m.mergeInfo());
            default -> Optional.empty();
        };
    }

    public void addReview(Review review) {
        reviewSummary.addReview(review);
    }

    public void updateCommentCount(int commentCount) {
        setCommentCount(commentCount);
    }

    public void updateCiStatus(CiStatus newCiStatus, Instant updatedAt) {
        Objects.requireNonNull(updatedAt, "Updated at must not be null");

        if (this.ciStatus == newCiStatus)
            return;

        setCiStatus(newCiStatus);
        this.updatedAt = updatedAt;
    }

    public void merge(MergeInfo mergeInfo) {
        if (state instanceof PullRequestState.Merged)
            return;

        state = new PullRequestState.Merged(mergeInfo);
        this.updatedAt = mergeInfo.mergedAt();
    }

    public void close(Instant updatedAt) {
        Objects.requireNonNull(updatedAt, "Updated at must not be null");

        if (state instanceof PullRequestState.Closed || state instanceof PullRequestState.Ignored)
            return;

        state = new PullRequestState.Closed();
        this.updatedAt = updatedAt;
    }

    public void undraft(Instant updatedAt) {
        Objects.requireNonNull(updatedAt, "Updated at must not be null");

        if (!this.draft)
            return;

        this.draft = false;
        this.updatedAt = updatedAt;
    }

    public void updateTitle(String title, Instant updatedAt) {
        Objects.requireNonNull(updatedAt, "Updated at must not be null");

        this.title = new Title(title);
        this.updatedAt = updatedAt;
    }

    public void updateLabels(List<String> labels, Instant updatedAt) {
        Objects.requireNonNull(updatedAt, "Updated at must not be null");

        setLabels(labels);
        this.updatedAt = updatedAt;
    }

    private void setCiStatus(CiStatus ciStatus) {
        Objects.requireNonNull(ciStatus, "CI status must not be null");

        this.ciStatus = ciStatus;
    }

    private void setLabels(List<String> labels) {
        Objects.requireNonNull(labels, "Labels must not be null");

        this.labels = new ArrayList<>(labels);
    }

    private void setCommentCount(int commentCount) {
        if (commentCount < 0)
            throw new IllegalArgumentException("Comment count must not be negative");

        this.commentCount = commentCount;
    }
}
