package com.pullrequesttracker.presentation.cli.view.dashboard;

import com.pullrequesttracker.application.usecase.FetchCodeRepositorySummaries;
import com.pullrequesttracker.application.usecase.FetchPullRequestSummary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DashboardController {
    private final DashboardState dashboardState;
    private final FetchCodeRepositorySummaries fetchCodeRepositorySummaries;
    private final FetchPullRequestSummary fetchPullRequestSummary;

    public void loadCodeRepositorySummaries() {
        dashboardState.set(DashboardState.REPOSITORY_SUMMARIES, fetchCodeRepositorySummaries.execute());
    }

    public void loadPullRequestSummary() {
        dashboardState.set(DashboardState.PULL_REQUEST_SUMMARY, fetchPullRequestSummary.execute());
    }
}
