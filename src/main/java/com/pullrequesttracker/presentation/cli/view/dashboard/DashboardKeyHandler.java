package com.pullrequesttracker.presentation.cli.view.dashboard;

import com.pullrequesttracker.presentation.cli.action.KeyHandler;
import com.pullrequesttracker.presentation.cli.view.dashboard.action.NavigateToPullRequestsAction;
import com.pullrequesttracker.presentation.cli.view.dashboard.action.NavigateToRepositoriesAction;
import com.pullrequesttracker.presentation.cli.view.dashboard.action.NavigateToTokensAction;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DashboardKeyHandler extends KeyHandler {
    public DashboardKeyHandler(NavigateToRepositoriesAction navigateToRepositoriesAction,
            NavigateToTokensAction navigateToTokensAction, NavigateToPullRequestsAction navigateToPullRequestsAction) {
        super(List.of(navigateToRepositoriesAction, navigateToTokensAction, navigateToPullRequestsAction));
    }
}
