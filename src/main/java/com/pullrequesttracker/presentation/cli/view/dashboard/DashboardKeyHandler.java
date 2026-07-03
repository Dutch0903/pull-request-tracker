package com.pullrequesttracker.presentation.cli.view.dashboard;

import com.pullrequesttracker.presentation.cli.action.DashboardViewAction;
import com.pullrequesttracker.presentation.cli.action.KeyHandler;
import com.pullrequesttracker.presentation.cli.action.SharedAction;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DashboardKeyHandler extends KeyHandler {
    public DashboardKeyHandler(List<SharedAction> shared, List<DashboardViewAction> actions) {
        super(shared, actions);
    }
}
