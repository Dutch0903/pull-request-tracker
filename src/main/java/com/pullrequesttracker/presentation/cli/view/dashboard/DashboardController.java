package com.pullrequesttracker.presentation.cli.view.dashboard;

import com.pullrequesttracker.application.usecase.FetchRecentCodeRepositories;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DashboardController {
    private final DashboardState dashboardState;
    private final FetchRecentCodeRepositories fetchRecentCodeRepositories;

    public void loadRecentRepositories() {
        dashboardState.setRecentRepositories(fetchRecentCodeRepositories.execute());
    }
}
