package com.prtracker.pullrequest.application.command;

import com.prtracker.coderepository.application.event.RepositoryCheckRequestedEvent;
import com.prtracker.coderepository.domain.model.CodeRepository;
import com.prtracker.pullrequest.domain.model.PullRequest;
import com.prtracker.pullrequest.domain.port.PullRequestRepository;
import com.prtracker.pullrequest.domain.port.RepositorySynchronizerPort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FetchRepositoryPullRequests {
    private final RepositorySynchronizerPort repositorySynchronizerPort;
    private final PullRequestRepository pullRequestRepository;

    @EventListener
    public void onRepositoryCheckRequested(RepositoryCheckRequestedEvent event) {
        execute(event.codeRepository());
    }

    public void execute(CodeRepository codeRepository) {
        List<PullRequest> existing = pullRequestRepository.findAllByCodeRepositoryId(codeRepository.getId());

        repositorySynchronizerPort.synchronize(codeRepository, existing)
                .forEach(pullRequestRepository::save);
    }
}
