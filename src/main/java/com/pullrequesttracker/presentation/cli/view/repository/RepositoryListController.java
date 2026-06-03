package com.pullrequesttracker.presentation.cli.view.repository;

import com.pullrequesttracker.application.usecase.FetchAllCodeRepositories;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RepositoryListController {
    private final RepositoryListState state;
    private final FetchAllCodeRepositories fetchAllCodeRepositories;

    public void loadRepositories() {
        state.setRepositories(fetchAllCodeRepositories.execute());
    }
}
