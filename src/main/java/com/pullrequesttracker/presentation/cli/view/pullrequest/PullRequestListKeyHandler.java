package com.pullrequesttracker.presentation.cli.view.pullrequest;

import com.pullrequesttracker.presentation.cli.action.KeyHandler;
import com.pullrequesttracker.presentation.cli.action.NavigateBackAction;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PullRequestListKeyHandler extends KeyHandler {
    public PullRequestListKeyHandler(NavigateBackAction navigateBackAction) {
        super(List.of(navigateBackAction));
    }
}
