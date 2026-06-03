package com.pullrequesttracker.presentation.cli.view.dashboard;

import com.pullrequesttracker.presentation.cli.action.KeyHandler;
import com.pullrequesttracker.presentation.cli.view.dashboard.action.NavigateToRepositoriesAction;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DashboardKeyHandler extends KeyHandler {
    public DashboardKeyHandler(NavigateToRepositoriesAction navigateToRepositoriesAction) {
        super(List.of(navigateToRepositoriesAction));
    }
}
