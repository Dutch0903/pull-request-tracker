package com.prtracker.pullrequest.adapter.out.github;

import com.prtracker.pullrequest.adapter.out.github.graphql.dto.Commit;
import com.prtracker.pullrequest.adapter.out.github.graphql.dto.Label;
import com.prtracker.pullrequest.adapter.out.github.graphql.dto.PullRequest;
import com.prtracker.pullrequest.domain.enums.CiStatus;
import com.prtracker.pullrequest.domain.enums.PullRequestStatus;
import com.prtracker.pullrequest.domain.enums.ReviewStatus;
import com.prtracker.pullrequest.domain.model.PullRequestSyncData;
import com.prtracker.pullrequest.domain.model.Review;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class GitHubPullRequestMapper {
    public PullRequestSyncData toSyncData(PullRequest pullRequest) {
        return new PullRequestSyncData(pullRequest.number(), pullRequest.title(), pullRequest.author().login(),
                pullRequest.isDraft(), determineStatus(pullRequest),
                pullRequest.mergedBy() != null ? pullRequest.mergedBy().login() : null, pullRequest.mergedAt(),
                determineCiStatus(pullRequest), mapLabels(pullRequest), mapRequestedReviewers(pullRequest),
                mapReviews(pullRequest), mapReviewDecision(pullRequest), pullRequest.totalCommentsCount(),
                pullRequest.createdAt(), pullRequest.updatedAt());
    }

    private PullRequestStatus determineStatus(PullRequest pullRequest) {
        if (pullRequest.closed()) {
            return PullRequestStatus.CLOSED;
        }

        if (pullRequest.merged()) {
            return PullRequestStatus.MERGED;
        }

        return PullRequestStatus.OPEN;
    }

    private CiStatus determineCiStatus(PullRequest pullRequest) {
        List<Commit> commits = pullRequest.commits().nodes();

        if (commits.isEmpty()) {
            return CiStatus.UNKNOWN;
        }

        Commit latestCommit = commits.getFirst();

        if (latestCommit.statusCheckRollup() == null) {
            return CiStatus.UNKNOWN;
        }

        return switch (latestCommit.statusCheckRollup().state()) {
            case "ERROR", "FAILED" -> CiStatus.FAILED;
            case "PENDING" -> CiStatus.PENDING;
            case "EXPECTED" -> CiStatus.IN_PROGRESS;
            case "SUCCESS" -> CiStatus.PASSED;
            default -> CiStatus.UNKNOWN;
        };
    }

    private List<String> mapLabels(PullRequest pullRequest) {
        return pullRequest.labels().nodes().stream().map(Label::name).toList();
    }

    private List<String> mapRequestedReviewers(PullRequest pullRequest) {
        return List.of();
    }

    private List<Review> mapReviews(PullRequest pullRequest) {
        return pullRequest.latestReviews().nodes().stream().map(ghReview -> new Review(ghReview.author().login(),
                mapReviewStatus(ghReview.state()), ghReview.submittedAt())).toList();
    }

    private ReviewStatus mapReviewDecision(PullRequest pullRequest) {
        String reviewDecision = pullRequest.reviewDecision();

        if (reviewDecision == null) {
            return ReviewStatus.REVIEW_REQUIRED;
        }

        return mapReviewStatus(reviewDecision);
    }

    private ReviewStatus mapReviewStatus(String ghStatus) {
        return switch (ghStatus) {
            case "APPROVED" -> ReviewStatus.APPROVED;
            case "CHANGED_REQUESTED" -> ReviewStatus.CHANGES_REQUESTED;
            case "COMMENTED" -> ReviewStatus.COMMENTED;
            case "DISMISSED" -> ReviewStatus.DISMISSED;
            default -> ReviewStatus.REVIEW_REQUIRED;
        };
    }
}
