package com.prtracker.presentation.cli.view.dashboard;

import com.prtracker.application.command.FetchRepositoryPullRequestsCommand;
import com.prtracker.application.query.GetRecentCodeRepositoriesQuery;
import com.prtracker.domain.entity.CodeRepository;
import com.prtracker.domain.repository.CodeRepositoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DashboardController {
    private final DashboardState dashboardState;
    private final FetchRepositoryPullRequestsCommand fetchRepositoryPullRequestsCommand;
    private final CodeRepositoryRepository codeRepositoryRepository;

    private final GetRecentCodeRepositoriesQuery getRecentCodeRepositoriesQuery;

    public void loadRecentRepositories() {
        var repos = getRecentCodeRepositoriesQuery.execute();

        dashboardState.setRecentRepositories(repos);
    }

    public void fetchRepositoryPullRequests() {
        CodeRepository codeRepository = codeRepositoryRepository.findAll().getFirst();

        fetchRepositoryPullRequestsCommand.execute(codeRepository);
    }
}
