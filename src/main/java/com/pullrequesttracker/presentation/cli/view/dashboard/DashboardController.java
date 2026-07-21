package com.pullrequesttracker.presentation.cli.view.dashboard;

import com.pullrequesttracker.application.dto.AttentionItemDto;
import com.pullrequesttracker.application.usecase.FetchCodeRepositorySummaries;
import com.pullrequesttracker.application.usecase.FetchCreatedPrs;
import com.pullrequesttracker.application.usecase.FetchPullRequestSummary;
import com.pullrequesttracker.application.usecase.FetchRequestedReviewPrs;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class DashboardController {
    private final DashboardState dashboardState;
    private final FetchCodeRepositorySummaries fetchCodeRepositorySummaries;
    private final FetchPullRequestSummary fetchPullRequestSummary;
    private final FetchCreatedPrs fetchCreatedPrs;
    private final FetchRequestedReviewPrs fetchRequestedReviewPrs;

    public void loadCodeRepositorySummaries() {
        dashboardState.set(DashboardState.REPOSITORY_SUMMARIES, fetchCodeRepositorySummaries.execute());
    }

    public void loadPullRequestSummary() {
        dashboardState.set(DashboardState.PULL_REQUEST_SUMMARY, fetchPullRequestSummary.execute());
    }

    public void loadAttentionItems() {
        List<AttentionItemDto> items = Stream
                .concat(fetchCreatedPrs.execute().stream(), fetchRequestedReviewPrs.execute().stream())
                .toList();
        dashboardState.set(DashboardState.ATTENTION_ITEMS, items);
    }
}
