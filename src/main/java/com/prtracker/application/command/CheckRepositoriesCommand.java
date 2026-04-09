package com.prtracker.application.command;

import com.prtracker.domain.entity.CodeRepository;
import com.prtracker.domain.repository.CodeRepositoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CheckRepositoriesCommand extends VoidCommand {
    private final CodeRepositoryRepository codeRepositoryRepository;
    private final FetchRepositoryPullRequestsCommand fetchRepositoryPullRequestsCommand;

    @Override
    protected void executeInternal() {
        List<CodeRepository> codeRepositories = codeRepositoryRepository.findAll();

        codeRepositories.forEach(fetchRepositoryPullRequestsCommand::execute);
    }
}
