package com.pullrequesttracker.testfixtures.domain.sync;

import com.pullrequesttracker.domain.model.PullRequestState;
import com.pullrequesttracker.domain.sync.PullRequestSyncData;
import com.pullrequesttracker.domain.type.CiStatus;
import com.pullrequesttracker.domain.type.ReviewStatus;
import com.pullrequesttracker.domain.valueobject.Actor;
import com.pullrequesttracker.domain.valueobject.Review;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class PullRequestSyncDataTestBuilder {
    private int externalId = 1;
    private String title = "title";
    private String author = "author";
    private boolean isDraft = false;
    private PullRequestState state = new PullRequestState.Open();
    private CiStatus ciStatus = CiStatus.PENDING;
    private List<String> labels = Collections.emptyList();
    private List<Review> reviews = Collections.emptyList();
    private ReviewStatus reviewStatus = ReviewStatus.REVIEW_REQUIRED;
    private Set<Actor> reviewRequests = Collections.emptySet();
    private int commentCount = 0;
    private Instant createdAt = Instant.now();
    private Instant updatedAt = Instant.now();

    public static PullRequestSyncDataTestBuilder aPullRequestSyncData() {
        return new PullRequestSyncDataTestBuilder();
    }

    public PullRequestSyncDataTestBuilder withExternalId(int externalId) {
        this.externalId = externalId;
        return this;
    }

    public PullRequestSyncDataTestBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public PullRequestSyncDataTestBuilder withAuthor(String author) {
        this.author = author;
        return this;
    }

    public PullRequestSyncDataTestBuilder withIsDraft(boolean isDraft) {
        this.isDraft = isDraft;
        return this;
    }

    public PullRequestSyncDataTestBuilder withState(PullRequestState state) {
        this.state = state;
        return this;
    }

    public PullRequestSyncDataTestBuilder withCiStatus(CiStatus ciStatus) {
        this.ciStatus = ciStatus;
        return this;
    }

    public PullRequestSyncDataTestBuilder withLabels(List<String> labels) {
        this.labels = labels;
        return this;
    }

    public PullRequestSyncDataTestBuilder withReviews(List<Review> reviews) {
        this.reviews = reviews;
        return this;
    }

    public PullRequestSyncDataTestBuilder withReviewStatus(ReviewStatus reviewStatus) {
        this.reviewStatus = reviewStatus;
        return this;
    }

    public PullRequestSyncDataTestBuilder withReviewRequests(Set<Actor> reviewRequests) {
        this.reviewRequests = reviewRequests;
        return this;
    }

    public PullRequestSyncDataTestBuilder withCommentCount(int commentCount) {
        this.commentCount = commentCount;
        return this;
    }

    public PullRequestSyncDataTestBuilder withCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public PullRequestSyncDataTestBuilder withUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public PullRequestSyncData build() {
        return new PullRequestSyncData(externalId, title, Actor.from(author), isDraft, state, ciStatus, labels, reviews,
                reviewStatus, reviewRequests, commentCount, createdAt, updatedAt);
    }
}
