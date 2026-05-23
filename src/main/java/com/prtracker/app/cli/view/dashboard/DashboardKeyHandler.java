package com.prtracker.app.cli.view.dashboard;

import com.prtracker.app.cli.action.KeyHandler;
import com.prtracker.app.cli.view.dashboard.action.NavigateToRepositoriesAction;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DashboardKeyHandler extends KeyHandler {

    public DashboardKeyHandler(NavigateToRepositoriesAction navigateToRepositoriesAction) {
        super(List.of(navigateToRepositoriesAction));
    }
}
