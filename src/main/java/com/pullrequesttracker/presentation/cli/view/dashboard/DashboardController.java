package com.pullrequesttracker.presentation.cli.view.dashboard;

import com.pullrequesttracker.application.query.GetRecentCodeRepositories;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DashboardController {
    private final DashboardState dashboardState;
    private final GetRecentCodeRepositories getRecentCodeRepositories;

    public void loadRecentRepositories() {
        dashboardState.setRecentRepositories(getRecentCodeRepositories.execute());
    }
}
