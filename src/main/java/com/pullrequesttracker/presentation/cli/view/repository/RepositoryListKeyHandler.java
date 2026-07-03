package com.pullrequesttracker.presentation.cli.view.repository;

import com.pullrequesttracker.presentation.cli.action.KeyHandler;
import com.pullrequesttracker.presentation.cli.action.RepositoryListViewAction;
import com.pullrequesttracker.presentation.cli.action.SharedAction;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RepositoryListKeyHandler extends KeyHandler {
    public RepositoryListKeyHandler(List<SharedAction> shared, List<RepositoryListViewAction> actions) {
        super(shared, actions);
    }
}
