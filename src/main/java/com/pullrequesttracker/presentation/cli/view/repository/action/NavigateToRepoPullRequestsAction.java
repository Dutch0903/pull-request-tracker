package com.pullrequesttracker.presentation.cli.view.repository.action;

import com.pullrequesttracker.application.dto.CodeRepositoryDto;
import com.pullrequesttracker.domain.valueobject.CodeRepositoryId;
import com.pullrequesttracker.presentation.cli.action.RepositoryListViewAction;
import com.pullrequesttracker.presentation.cli.navigation.NavigationEventPublisher;
import com.pullrequesttracker.presentation.cli.navigation.ViewName;
import com.pullrequesttracker.presentation.cli.view.pullrequest.PullRequestListController;
import com.pullrequesttracker.presentation.cli.view.pullrequest.PullRequestListState;
import com.pullrequesttracker.presentation.cli.view.repository.component.RepositoryList;
import dev.tamboui.tui.event.KeyEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NavigateToRepoPullRequestsAction implements RepositoryListViewAction {
    private final RepositoryList repositoryList;
    private final PullRequestListState pullRequestListState;
    private final PullRequestListController pullRequestListController;
    private final NavigationEventPublisher navigationEventPublisher;

    @Override
    public boolean matches(KeyEvent keyEvent) {
        return keyEvent.isCharIgnoreCase('p');
    }

    @Override
    public String getKey() {
        return "p";
    }

    @Override
    public String getLabel() {
        return "Pull requests";
    }

    @Override
    public int order() {
        return 2;
    }

    @Override
    public boolean isAvailable() {
        return repositoryList.getSelectedRepository() != null;
    }

    @Override
    public void execute(KeyEvent event) {
        CodeRepositoryDto repo = repositoryList.getSelectedRepository();
        pullRequestListState.setCodeRepositoryFilter(CodeRepositoryId.from(repo.id()));
        pullRequestListController.loadPullRequests();
        navigationEventPublisher.navigateTo(ViewName.PULL_REQUESTS);
    }
}
