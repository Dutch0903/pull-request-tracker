package com.pullrequesttracker.presentation.cli.view.dashboard;

import com.pullrequesttracker.application.dto.CodeRepositorySummaryDto;
import com.pullrequesttracker.application.dto.PullRequestSummaryDto;
import com.pullrequesttracker.presentation.cli.state.SnapshotKey;
import com.pullrequesttracker.presentation.cli.state.StateManager;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DashboardState extends StateManager {
    public static final SnapshotKey<List<CodeRepositorySummaryDto>> REPOSITORY_SUMMARIES = new SnapshotKey<>(
            "codeRepositorySummaries");

    public static final SnapshotKey<PullRequestSummaryDto> PULL_REQUEST_SUMMARY = new SnapshotKey<>(
            "pullRequestSummary");
}
