package com.pullrequesttracker.presentation.cli.view.repository;

import com.pullrequesttracker.presentation.cli.action.KeyHandler;
import com.pullrequesttracker.presentation.cli.view.repository.action.NavigateToDashboardAction;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RepositoryListKeyHandler extends KeyHandler {
    public RepositoryListKeyHandler(NavigateToDashboardAction navigateToDashboardAction) {
        super(List.of(navigateToDashboardAction));
    }
}
