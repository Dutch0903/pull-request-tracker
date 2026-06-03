package com.pullrequesttracker.domain.model;

import com.pullrequesttracker.domain.type.CiStatus;
import com.pullrequesttracker.domain.type.PullRequestStatus;
import com.pullrequesttracker.domain.valueobject.CodeRepositoryId;
import com.pullrequesttracker.domain.valueobject.MergeInfo;
import com.pullrequesttracker.domain.valueobject.PullRequestId;
import com.pullrequesttracker.domain.valueobject.Review;
import com.pullrequesttracker.domain.valueobject.ReviewSummary;
import com.pullrequesttracker.domain.sync.PullRequestSyncData;
import lombok.Getter;
import org.springframework.lang.Nullable;

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
    private final String author;
    private final Instant createdAt;
    private String title;
    private boolean draft;
    private PullRequestStatus status;
    private CiStatus ciStatus;
    private List<String> labels;
    private ReviewSummary reviewSummary;
    private int commentCount;
    @Nullable
    private MergeInfo mergeInfo;
    private Instant updatedAt;

    PullRequest(PullRequestId id, CodeRepositoryId codeRepositoryId, int externalId, String author,
                Instant createdAt, String title, boolean draft, PullRequestStatus status, CiStatus ciStatus,
                List<String> labels, ReviewSummary reviewSummary, int commentCount, @Nullable MergeInfo mergeInfo,
                Instant updatedAt) {
        Objects.requireNonNull(id, "Pull request id must not be null");
        Objects.requireNonNull(codeRepositoryId, "Code repository id must not be null");
        if (externalId <= 0) throw new IllegalArgumentException("External id must be positive");
        Objects.requireNonNull(author, "Author must not be null");
        if (author.isBlank()) throw new IllegalArgumentException("Author must not be blank");
        Objects.requireNonNull(createdAt, "Created at must not be null");
        Objects.requireNonNull(status, "Status must not be null");
        Objects.requireNonNull(reviewSummary, "Review summary must not be null");
        Objects.requireNonNull(updatedAt, "Updated at must not be null");
        if (status == PullRequestStatus.MERGED && mergeInfo == null) {
            throw new IllegalArgumentException("Merge info must be present when status is MERGED");
        }

        this.id = id;
        this.codeRepositoryId = codeRepositoryId;
        this.externalId = externalId;
        this.author = author;
        this.createdAt = createdAt;
        this.draft = draft;
        this.status = status;
        this.reviewSummary = reviewSummary;
        this.mergeInfo = mergeInfo;
        this.updatedAt = updatedAt;
        setTitle(title);
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

        if (syncData.status() == PullRequestStatus.MERGED) {
            merge(syncData.mergedBy(), syncData.mergedAt());
        } else if (syncData.status() == PullRequestStatus.CLOSED) {
            close(syncData.updatedAt());
        } else if (!syncData.isDraft()) {
            undraft(syncData.updatedAt());
        }
    }

    public Optional<MergeInfo> getMergeInfo() {
        return Optional.ofNullable(mergeInfo);
    }

    public void addReview(Review review) {
        reviewSummary.addReview(review);
    }

    public void updateCommentCount(int commentCount) {
        setCommentCount(commentCount);
    }

    public void updateCiStatus(CiStatus newCiStatus, Instant updatedAt) {
        Objects.requireNonNull(updatedAt, "Updated at must not be null");
        if (this.ciStatus == newCiStatus) return;
        setCiStatus(newCiStatus);
        this.updatedAt = updatedAt;
    }

    public void merge(String mergedBy, Instant mergedAt) {
        if (this.status == PullRequestStatus.MERGED) return;
        this.mergeInfo = new MergeInfo(mergedBy, mergedAt);
        this.status = PullRequestStatus.MERGED;
        this.updatedAt = mergedAt;
    }

    public void close(Instant updatedAt) {
        Objects.requireNonNull(updatedAt, "Updated at must not be null");
        if (this.status == PullRequestStatus.CLOSED || this.status == PullRequestStatus.IGNORED) return;
        this.status = PullRequestStatus.CLOSED;
        this.updatedAt = updatedAt;
    }

    public void undraft(Instant updatedAt) {
        Objects.requireNonNull(updatedAt, "Updated at must not be null");
        if (!this.draft) return;
        this.draft = false;
        this.updatedAt = updatedAt;
    }

    public void updateTitle(String title, Instant updatedAt) {
        Objects.requireNonNull(updatedAt, "Updated at must not be null");
        setTitle(title);
        this.updatedAt = updatedAt;
    }

    public void updateLabels(List<String> labels, Instant updatedAt) {
        Objects.requireNonNull(updatedAt, "Updated at must not be null");
        setLabels(labels);
        this.updatedAt = updatedAt;
    }

    private void setTitle(String title) {
        Objects.requireNonNull(title, "Title must not be null");
        if (title.isBlank()) throw new IllegalArgumentException("Title must not be blank");
        this.title = title;
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
        if (commentCount < 0) throw new IllegalArgumentException("Comment count must not be negative");
        this.commentCount = commentCount;
    }
}
