package com.pullrequesttracker.presentation.cli.view.pullrequest.action;

import com.pullrequesttracker.presentation.cli.action.PullRequestListViewAction;
import com.pullrequesttracker.presentation.cli.view.pullrequest.PullRequestListController;
import com.pullrequesttracker.presentation.cli.view.pullrequest.PullRequestListState;
import dev.tamboui.tui.event.KeyEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClearFiltersAction implements PullRequestListViewAction {
    private final PullRequestListState state;
    private final PullRequestListController controller;

    @Override
    public boolean matches(KeyEvent keyEvent) {
        return keyEvent.character() == 'x';
    }

    @Override
    public String getKey() {
        return "x";
    }

    @Override
    public String getLabel() {
        return "Clear filters";
    }

    @Override
    public int order() {
        return 2;
    }

    @Override
    public boolean isAvailable() {
        return state.getStatusFilter() != null || state.getSearchQuery() != null
                || state.getCodeRepositoryFilter() != null;
    }

    @Override
    public void execute(KeyEvent event) {
        state.resetFilters();
        controller.loadPullRequests();
    }
}
