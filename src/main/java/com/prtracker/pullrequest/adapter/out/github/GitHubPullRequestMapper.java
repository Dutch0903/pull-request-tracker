package com.prtracker.pullrequest.adapter.out.github;

import com.prtracker.pullrequest.domain.enums.CiStatus;
import com.prtracker.pullrequest.domain.enums.PullRequestStatus;
import com.prtracker.pullrequest.domain.enums.ReviewState;
import com.prtracker.pullrequest.domain.model.Review;
import org.kohsuke.github.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class GitHubPullRequestMapper {

    public PullRequestStatus mapStatus(GHPullRequest ghPr) throws IOException {
        if (ghPr.isMerged()) return PullRequestStatus.MERGED;

        return switch (ghPr.getState()) {
            case OPEN, ALL -> PullRequestStatus.OPEN;
            case CLOSED -> PullRequestStatus.CLOSED;
        };
    }

    public CiStatus mapCiStatus(List<GHCheckRun> ghCheckRuns) {
        if (ghCheckRuns.isEmpty()) return CiStatus.PENDING;

        boolean isAnyInProgress = ghCheckRuns.stream()
                .anyMatch(ghCheckRun -> ghCheckRun.getStatus() == GHCheckRun.Status.IN_PROGRESS);

        if (isAnyInProgress) return CiStatus.IN_PROGRESS;

        boolean anyFailed = ghCheckRuns.stream()
                .map(GHCheckRun::getConclusion)
                .anyMatch(c -> c == GHCheckRun.Conclusion.FAILURE
                        || c == GHCheckRun.Conclusion.TIMED_OUT
                        || c == GHCheckRun.Conclusion.CANCELLED
                        || c == GHCheckRun.Conclusion.ACTION_REQUIRED);

        return anyFailed ? CiStatus.FAILING : CiStatus.PASSING;
    }

    public List<String> mapLabels(GHPullRequest ghPr) throws IOException {
        return ghPr.getLabels().stream()
                .map(GHLabel::getName)
                .toList();
    }

    public List<String> mapRequestedReviewers(GHPullRequest ghPr) throws IOException {
        return ghPr.getRequestedReviewers().stream()
                .map(GHPerson::getLogin)
                .toList();
    }

    public List<Review> mapReviews(List<GHPullRequestReview> ghReviews) {
        return ghReviews.stream()
                .map(this::mapReview)
                .toList();
    }

    private Review mapReview(GHPullRequestReview ghReview) {
        try {
            return new Review(
                    ghReview.getUser().getLogin(),
                    mapReviewState(ghReview.getState()),
                    ghReview.getSubmittedAt().toInstant()
            );
        } catch (IOException e) {
            throw new GitHubApiException("Failed to map review", e);
        }
    }

    private ReviewState mapReviewState(GHPullRequestReviewState ghReviewState) {
        if (ghReviewState == null) return ReviewState.PENDING;

        return switch (ghReviewState) {
            case APPROVED -> ReviewState.APPROVED;
            case CHANGES_REQUESTED -> ReviewState.CHANGES_REQUESTED;
            case COMMENTED -> ReviewState.COMMENTED;
            default -> ReviewState.PENDING;
        };
    }
}
