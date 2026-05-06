package com.prtracker.pullrequest.domain.model;

import com.prtracker.pullrequest.domain.enums.CiStatus;
import com.prtracker.pullrequest.domain.enums.PullRequestStatus;
import com.prtracker.pullrequest.domain.enums.ReviewState;
import com.prtracker.shared.kernel.CodeRepositoryId;
import lombok.Getter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
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
    private int commentCount;
    private List<String> labels;
    private List<String> requestedReviewers;
    private final List<Review> reviews;
    private Instant updatedAt;
    private String mergedBy;
    private Instant mergedAt;

    PullRequest(PullRequestId id, CodeRepositoryId codeRepositoryId, int externalId,
                String title, String author, boolean draft, PullRequestStatus status,
                CiStatus ciStatus, int commentCount, List<String> labels,
                List<String> requestedReviewers, List<Review> reviews,
                Instant createdAt, Instant updatedAt, String mergedBy, Instant mergedAt) {
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
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.mergedBy = mergedBy;
        this.mergedAt = mergedAt;
    }

    public Optional<Review> addReview(Review review) {
        boolean alreadyExists = reviews.stream()
                .anyMatch(r -> r.reviewer().equals(review.reviewer()) && r.submittedAt().equals(review.submittedAt()));
        if (alreadyExists) return Optional.empty();
        reviews.add(review);
        return Optional.of(review);
    }

    public boolean updateCiStatus(CiStatus newCiStatus, Instant updatedAt) {
        if (this.ciStatus == newCiStatus) return false;
        this.ciStatus = newCiStatus;
        this.updatedAt = updatedAt;
        return true;
    }

    public boolean merge(String mergedBy, Instant mergedAt) {
        if (this.status == PullRequestStatus.MERGED) return false;
        this.status = PullRequestStatus.MERGED;
        this.mergedBy = mergedBy;
        this.mergedAt = mergedAt;
        this.updatedAt = mergedAt;
        return true;
    }

    public boolean close(Instant updatedAt) {
        if (this.status == PullRequestStatus.CLOSED || this.status == PullRequestStatus.IGNORED) return false;
        this.status = PullRequestStatus.CLOSED;
        this.updatedAt = updatedAt;
        return true;
    }

    public boolean undraft(Instant updatedAt) {
        if (!this.draft) return false;
        this.draft = false;
        this.updatedAt = updatedAt;
        return true;
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
        return (int) reviews.stream()
                .filter(r -> r.state() == ReviewState.APPROVED)
                .count();
    }

    public ReviewState effectiveReviewState() {
        if (reviews.isEmpty()) return ReviewState.PENDING;

        if (reviews.stream().anyMatch(r -> r.state() == ReviewState.CHANGES_REQUESTED)) {
            return ReviewState.CHANGES_REQUESTED;
        }

        if (reviews.stream().allMatch(r -> r.state() == ReviewState.APPROVED)) {
            return ReviewState.APPROVED;
        }

        return ReviewState.PENDING;
    }
}
