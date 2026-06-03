package com.pullrequesttracker.presentation.cli.view.repository;

import com.pullrequesttracker.application.query.GetCodeRepositories;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RepositoryListController {
    private final RepositoryListState state;
    private final GetCodeRepositories getCodeRepositories;

    public void loadRepositories() {
        state.setRepositories(getCodeRepositories.execute());
    }
}
