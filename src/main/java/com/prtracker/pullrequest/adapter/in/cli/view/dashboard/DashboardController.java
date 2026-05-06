package com.prtracker.pullrequest.adapter.in.cli.view.dashboard;

import com.prtracker.coderepository.application.query.GetRecentCodeRepositories;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DashboardController {
    private final DashboardState dashboardState;
    private final GetRecentCodeRepositories getRecentCodeRepositories;

    public void loadRecentRepositories() {
        var repos = getRecentCodeRepositories.execute();

        dashboardState.setRecentRepositories(repos);
    }
}
