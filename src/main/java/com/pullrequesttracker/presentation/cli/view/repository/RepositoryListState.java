package com.pullrequesttracker.presentation.cli.view.repository;

import com.pullrequesttracker.application.query.CodeRepositoryProjection;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RepositoryListState {
    private List<CodeRepositoryProjection> repositories = List.of();

    public List<CodeRepositoryProjection> getRepositories() {
        return repositories;
    }

    public void setRepositories(List<CodeRepositoryProjection> repositories) {
        this.repositories = repositories;
    }
}
