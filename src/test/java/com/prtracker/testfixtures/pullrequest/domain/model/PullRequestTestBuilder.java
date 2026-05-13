package com.prtracker.testfixtures.pullrequest.domain.model;

import com.prtracker.pullrequest.domain.enums.CiStatus;
import com.prtracker.pullrequest.domain.enums.PullRequestStatus;
import com.prtracker.pullrequest.domain.enums.ReviewStatus;
import com.prtracker.pullrequest.domain.model.PullRequest;
import com.prtracker.pullrequest.domain.model.PullRequestFactory;
import com.prtracker.pullrequest.domain.model.PullRequestId;
import com.prtracker.pullrequest.domain.model.Review;
import com.prtracker.shared.kernel.CodeRepositoryId;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class PullRequestTestBuilder {
    private PullRequestId id = PullRequestId.create();
    private CodeRepositoryId codeRepositoryId = CodeRepositoryId.create();
    private int externalId = 1;
    private String author = "author";
    private String title = "title";
    private Instant createdAt = Instant.now();
    private Instant updatedAt = Instant.now();
    private boolean draft = false;
    private PullRequestStatus status = PullRequestStatus.OPEN;
    private CiStatus ciStatus = CiStatus.PENDING;
    private int commentCount = 0;
    private List<String> labels = List.of("label1", "label2");
    private List<String> requestedReviewers = List.of("reviewer1", "reviewer2");
    private List<Review> reviews = new ArrayList<>();
    private ReviewStatus reviewStatus = ReviewStatus.REVIEW_REQUIRED;
    private String mergedBy = "mergedBy";
    private Instant mergedAt = Instant.now();

    public static PullRequestTestBuilder aPullRequest() {
        return new PullRequestTestBuilder();
    }

    public static PullRequestTestBuilder copyOf(PullRequest pullRequest) {
        return aPullRequest().withId(pullRequest.getId());
    }

    public PullRequestTestBuilder withId(PullRequestId id) {
        this.id = id;
        return this;
    }

    public PullRequestTestBuilder withCodeRepositoryId(CodeRepositoryId codeRepositoryId) {
        this.codeRepositoryId = codeRepositoryId;
        return this;
    }

    public PullRequestTestBuilder withExternalId(int externalId) {
        this.externalId = externalId;
        return this;
    }

    public PullRequestTestBuilder withAuthor(String author) {
        this.author = author;
        return this;
    }

    public PullRequestTestBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public PullRequestTestBuilder withCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public PullRequestTestBuilder withUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public PullRequestTestBuilder withDraft(boolean draft) {
        this.draft = draft;
        return this;
    }

    public PullRequestTestBuilder withStatus(PullRequestStatus status) {
        this.status = status;
        return this;
    }

    public PullRequestTestBuilder withReviewStatus(ReviewStatus reviewStatus) {
        this.reviewStatus = reviewStatus;
        return this;
    }

    public PullRequestTestBuilder withMergedAt(Instant mergedAt) {
        this.mergedAt = mergedAt;
        return this;
    }

    public PullRequestTestBuilder withLabels(List<String> labels) {
        this.labels = labels;
        return this;
    }

    public PullRequestTestBuilder withRequestedReviewers(List<String> requestedReviewers) {
        this.requestedReviewers = requestedReviewers;
        return this;
    }

    public PullRequestTestBuilder withReviews(List<Review> reviews) {
        this.reviews = reviews;
        return this;
    }

    public PullRequestTestBuilder withMergedBy(String mergedBy) {
        this.mergedBy = mergedBy;
        return this;
    }

    public PullRequest build() {
        PullRequestFactory factory = new PullRequestFactory();

        return factory.reconstitute(id, codeRepositoryId, externalId, author, title, draft, status, ciStatus,
                commentCount, labels, requestedReviewers, reviews, reviewStatus, createdAt, updatedAt, mergedBy,
                mergedAt);
    }
}
