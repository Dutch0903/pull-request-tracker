package com.prtracker.pullrequest.adapter.out.github;

import org.kohsuke.github.GHCheckRun;
import org.kohsuke.github.GHPullRequest;
import org.kohsuke.github.GHPullRequestReview;

import java.util.List;

public record GitHubPullRequestData(
        GHPullRequest pullRequest,
        List<GHPullRequestReview> reviews,
        List<GHCheckRun> checkRuns) {
}
