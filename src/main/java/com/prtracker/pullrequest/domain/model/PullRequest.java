package com.prtracker.pullrequest.domain.model;

import com.prtracker.pullrequest.domain.enums.CiStatus;
import com.prtracker.pullrequest.domain.enums.PullRequestStatus;
import com.prtracker.pullrequest.domain.enums.ReviewStatus;
import com.prtracker.shared.kernel.CodeRepositoryId;
import lombok.Getter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

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
    private int commentCount;
    private List<String> labels;
    private List<String> requestedReviewers;
    private List<Review> reviews;
    private ReviewStatus reviewStatus;
    private Instant updatedAt;
    private String mergedBy;
    private Instant mergedAt;

    PullRequest(PullRequestId id, CodeRepositoryId codeRepositoryId, int externalId, String title, String author,
            boolean draft, PullRequestStatus status, CiStatus ciStatus, int commentCount, List<String> labels,
            List<String> requestedReviewers, List<Review> reviews, ReviewStatus reviewStatus, Instant createdAt,
            Instant updatedAt, String mergedBy, Instant mergedAt) {
        this.id = id;
        this.codeRepositoryId = codeRepositoryId;
        this.externalId = externalId;
        this.title = title;
        this.author = author;
        this.draft = draft;
        this.status = status;
        this.ciStatus = ciStatus;
        this.commentCount = commentCount;
        this.labels = new ArrayList<>(labels);
        this.requestedReviewers = new ArrayList<>(requestedReviewers);
        this.reviews = new ArrayList<>(reviews);
        this.reviewStatus = reviewStatus;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.mergedBy = mergedBy;
        this.mergedAt = mergedAt;
    }

    public void sync(PullRequestSyncData syncData) {
        updateTitle(syncData.title(), syncData.updatedAt());
        updateCiStatus(syncData.ciStatus(), syncData.updatedAt());
        updateLabels(syncData.labels(), syncData.updatedAt());
        updateCommentCount(syncData.commentCount());
        syncData.reviews().forEach(this::addReview);

        if (syncData.status() == PullRequestStatus.MERGED) {
            merge(syncData.mergedBy(), syncData.mergedAt());
        } else if (syncData.status() == PullRequestStatus.CLOSED) {
            close(syncData.updatedAt());
        } else if (!syncData.isDraft()) {
            undraft(syncData.updatedAt());
        }
    }

    public void addReview(Review review) {
        boolean alreadyExists = reviews.stream()
                .anyMatch(r -> r.reviewer().equals(review.reviewer()) && r.submittedAt().equals(review.submittedAt()));
        if (alreadyExists)
            return;
        reviews.add(review);
    }

    public void updateCiStatus(CiStatus newCiStatus, Instant updatedAt) {
        if (this.ciStatus == newCiStatus)
            return;
        this.ciStatus = newCiStatus;
        this.updatedAt = updatedAt;
    }

    public void merge(String mergedBy, Instant mergedAt) {
        if (this.status == PullRequestStatus.MERGED)
            return;
        this.status = PullRequestStatus.MERGED;
        this.mergedBy = mergedBy;
        this.mergedAt = mergedAt;
        this.updatedAt = mergedAt;
    }

    public void close(Instant updatedAt) {
        if (this.status == PullRequestStatus.CLOSED || this.status == PullRequestStatus.IGNORED)
            return;
        this.status = PullRequestStatus.CLOSED;
        this.updatedAt = updatedAt;
    }

    public void undraft(Instant updatedAt) {
        if (!this.draft)
            return;
        this.draft = false;
        this.updatedAt = updatedAt;
    }

    public void updateTitle(String title, Instant updatedAt) {
        this.title = title;
        this.updatedAt = updatedAt;
    }

    public void updateLabels(List<String> labels, Instant updatedAt) {
        this.labels = new ArrayList<>(labels);
        this.updatedAt = updatedAt;
    }

    public void updateRequestedReviewers(List<String> requestedReviewers, Instant updatedAt) {
        this.requestedReviewers = new ArrayList<>(requestedReviewers);
        this.updatedAt = updatedAt;
    }

    public void updateCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int approvalCount() {
        return (int) reviews.stream().filter(r -> r.state() == ReviewStatus.APPROVED).count();
    }
}
