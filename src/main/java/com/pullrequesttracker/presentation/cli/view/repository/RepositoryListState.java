package com.pullrequesttracker.presentation.cli.view.repository;

import com.pullrequesttracker.application.dto.CodeRepositoryDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RepositoryListState {
    private List<CodeRepositoryDto> repositories = List.of();

    public List<CodeRepositoryDto> getRepositories() {
        return repositories;
    }

    public void setRepositories(List<CodeRepositoryDto> repositories) {
        this.repositories = repositories;
    }
}
