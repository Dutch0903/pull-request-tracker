package com.pullrequesttracker.presentation.cli.view.pullrequest;

import com.pullrequesttracker.presentation.cli.action.KeyHandler;
import com.pullrequesttracker.presentation.cli.action.PullRequestListViewAction;
import com.pullrequesttracker.presentation.cli.action.SharedAction;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PullRequestListKeyHandler extends KeyHandler {
    public PullRequestListKeyHandler(List<SharedAction> shared, List<PullRequestListViewAction> actions) {
        super(shared, actions);
    }
}
