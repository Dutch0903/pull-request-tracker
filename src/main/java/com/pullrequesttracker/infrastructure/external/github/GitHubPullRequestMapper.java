package com.pullrequesttracker.infrastructure.external.github;

import com.pullrequesttracker.domain.model.PullRequestState;
import com.pullrequesttracker.domain.sync.PullRequestSyncData;
import com.pullrequesttracker.domain.type.CiStatus;
import com.pullrequesttracker.domain.type.ReviewStatus;
import com.pullrequesttracker.domain.valueobject.Actor;
import com.pullrequesttracker.domain.valueobject.MergeInfo;
import com.pullrequesttracker.domain.valueobject.Review;
import com.pullrequesttracker.infrastructure.external.github.graphql.dto.GithubPullRequest;
import com.pullrequesttracker.infrastructure.external.github.graphql.dto.Label;
import com.pullrequesttracker.infrastructure.external.github.graphql.dto.StatusCheckRollup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class GitHubPullRequestMapper {

    public PullRequestSyncData toSyncData(GithubPullRequest pr) {
        return new PullRequestSyncData(pr.number(), pr.title(), Actor.from(pr.author().login()), pr.isDraft(),
                determineState(pr), determineCiStatus(pr), mapLabels(pr), mapReviews(pr), mapReviewDecision(pr),
                pr.totalCommentsCount(), pr.createdAt(), pr.updatedAt());
    }

    private PullRequestState determineState(GithubPullRequest pr) {
        if (pr.merged())
            return new PullRequestState.Merged(new MergeInfo(Actor.from(pr.mergedBy().login()), pr.mergedAt()));
        if (pr.closed())
            return new PullRequestState.Closed();
        return new PullRequestState.Open();
    }

    private CiStatus determineCiStatus(GithubPullRequest pr) {
        if (pr.headRef() == null || pr.headRef().target() == null)
            return CiStatus.UNKNOWN;

        StatusCheckRollup statusCheckRollup = pr.headRef().target().statusCheckRollup();

        log.info("Status check rollup: {}", statusCheckRollup);

        if (statusCheckRollup == null)
            return CiStatus.UNKNOWN;

        log.info("Status check rollup state: {}", statusCheckRollup.state());

        return switch (statusCheckRollup.state()) {
            case "ERROR", "FAILED" -> CiStatus.FAILED;
            case "PENDING" -> CiStatus.PENDING;
            case "EXPECTED" -> CiStatus.IN_PROGRESS;
            case "SUCCESS" -> CiStatus.PASSED;
            default -> CiStatus.UNKNOWN;
        };
    }

    private List<String> mapLabels(GithubPullRequest pr) {
        return pr.labels().nodes().stream().map(Label::name).toList();
    }

    private List<Review> mapReviews(GithubPullRequest pr) {
        return pr.latestReviews().nodes().stream()
                .map(r -> new Review(Actor.from(r.author().login()), mapReviewStatus(r.state()), r.submittedAt()))
                .toList();
    }

    private ReviewStatus mapReviewDecision(GithubPullRequest pr) {
        String reviewDecision = pr.reviewDecision();
        if (reviewDecision == null)
            return ReviewStatus.REVIEW_REQUIRED;
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
