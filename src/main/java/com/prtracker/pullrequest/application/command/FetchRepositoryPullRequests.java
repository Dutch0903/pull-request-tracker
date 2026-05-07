package com.prtracker.pullrequest.application.command;

import com.prtracker.coderepository.domain.model.CodeRepository;
import com.prtracker.coderepository.domain.port.RepositoryCheckerPort;
import com.prtracker.pullrequest.domain.model.PullRequest;
import com.prtracker.pullrequest.domain.port.PullRequestRepository;
import com.prtracker.pullrequest.domain.port.RepositorySynchronizerPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
@Slf4j
public class FetchRepositoryPullRequests implements RepositoryCheckerPort {
    private final RepositorySynchronizerPort repositorySynchronizerPort;
    private final PullRequestRepository pullRequestRepository;

    @Override
    @Async("repositoryCheckExecutor")
    public CompletableFuture<Void> check(CodeRepository codeRepository) {
        log.info("Repository check requested: {}", codeRepository);
        execute(codeRepository);
        return CompletableFuture.completedFuture(null);
    }

    public void execute(CodeRepository codeRepository) {
        List<PullRequest> existing = pullRequestRepository.findAllByCodeRepositoryId(codeRepository.getId());

        repositorySynchronizerPort.synchronize(codeRepository, existing)
                .forEach(pullRequestRepository::save);
    }
}
