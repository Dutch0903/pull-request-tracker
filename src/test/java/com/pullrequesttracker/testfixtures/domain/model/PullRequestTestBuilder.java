package com.pullrequesttracker.testfixtures.domain.model;

import com.pullrequesttracker.domain.model.PullRequest;
import com.pullrequesttracker.domain.model.PullRequestFactory;
import com.pullrequesttracker.domain.type.CiStatus;
import com.pullrequesttracker.domain.type.PullRequestStatus;
import com.pullrequesttracker.domain.type.ReviewStatus;
import com.pullrequesttracker.domain.valueobject.CodeRepositoryId;
import com.pullrequesttracker.domain.valueobject.MergeInfo;
import com.pullrequesttracker.domain.valueobject.PullRequestId;
import com.pullrequesttracker.domain.valueobject.Review;
import com.pullrequesttracker.domain.valueobject.ReviewSummary;

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
    private List<Review> reviews = new ArrayList<>();
    private ReviewStatus reviewStatus = ReviewStatus.REVIEW_REQUIRED;
    private String mergedBy = null;
    private Instant mergedAt = null;

    public static PullRequestTestBuilder aPullRequest() {
        return new PullRequestTestBuilder();
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

    public PullRequestTestBuilder withReviews(List<Review> reviews) {
        this.reviews = new ArrayList<>(reviews);
        return this;
    }

    public PullRequestTestBuilder withReviewStatus(ReviewStatus reviewStatus) {
        this.reviewStatus = reviewStatus;
        return this;
    }

    public PullRequestTestBuilder withStatus(PullRequestStatus status) {
        this.status = status;
        return this;
    }

    public PullRequestTestBuilder withMergedBy(String mergedBy) {
        this.mergedBy = mergedBy;
        return this;
    }

    public PullRequestTestBuilder withMergedAt(Instant mergedAt) {
        this.mergedAt = mergedAt;
        return this;
    }

    public PullRequest build() {
        ReviewSummary reviewSummary = new ReviewSummary(reviews, reviewStatus);
        MergeInfo mergeInfo = (status == PullRequestStatus.MERGED && mergedBy != null)
                ? new MergeInfo(mergedBy, mergedAt)
                : null;

        return new PullRequestFactory().reconstitute(id, codeRepositoryId, externalId, author, createdAt,
                title, draft, status, ciStatus, labels, reviewSummary, commentCount, mergeInfo, updatedAt);
    }
}
