package com.pullrequesttracker.presentation.cli.view.repository;

import com.pullrequesttracker.application.usecase.CalculateCodeRepositoryStatistics;
import com.pullrequesttracker.application.usecase.FetchAllCodeRepositories;
import com.pullrequesttracker.domain.valueobject.CodeRepositoryId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RepositoryListController {
    private final RepositoryListState state;
    private final FetchAllCodeRepositories fetchAllCodeRepositories;
    private final CalculateCodeRepositoryStatistics calculateRepositoryStats;

    public void loadRepositories() {
        state.set(RepositoryListState.REPOSITORIES, fetchAllCodeRepositories.execute());
    }

    public void loadRepositoryStats(CodeRepositoryId id) {
        state.set(RepositoryListState.REPOSITORY_STATS, calculateRepositoryStats.execute(id));
    }
}
