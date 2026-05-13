package com.prtracker.pullrequest.application.command;

import com.prtracker.coderepository.domain.model.CodeRepository;
import com.prtracker.coderepository.domain.port.CodeRepositoryRepository;
import com.prtracker.coderepository.domain.port.RepositoryCheckerPort;
import com.prtracker.pullrequest.domain.model.PullRequest;
import com.prtracker.pullrequest.domain.model.PullRequestFactory;
import com.prtracker.pullrequest.domain.port.PullRequestRepository;
import com.prtracker.pullrequest.domain.port.RepositorySynchronizerPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class FetchRepositoryPullRequests implements RepositoryCheckerPort {
    private final RepositorySynchronizerPort repositorySynchronizerPort;
    private final PullRequestRepository pullRequestRepository;
    private final PullRequestFactory pullRequestFactory;
    private final CodeRepositoryRepository codeRepositoryRepository;

    @Override
    @Async("repositoryCheckExecutor")
    public CompletableFuture<Void> check(CodeRepository codeRepository) {
        Instant checkTime = Instant.now();

        Map<Integer, PullRequest> existingPullRequests = getExistingPullRequests(codeRepository);

        repositorySynchronizerPort.synchronize(codeRepository).forEach(pullRequestSyncData -> {
            Optional<PullRequest> existing = Optional
                    .ofNullable(existingPullRequests.get(pullRequestSyncData.externalId()));

            if (existing.isPresent()) {
                PullRequest pullRequest = existing.get();
                pullRequest.sync(pullRequestSyncData);

                pullRequestRepository.save(pullRequest);
            } else {
                PullRequest pullRequest = pullRequestFactory.create(codeRepository.getId(), pullRequestSyncData);

                pullRequestRepository.save(pullRequest);
            }
        });

        codeRepository.recordChecked(checkTime);
        codeRepositoryRepository.save(codeRepository);

        return CompletableFuture.completedFuture(null);
    }

    private Map<Integer, PullRequest> getExistingPullRequests(CodeRepository codeRepository) {
        return pullRequestRepository.findAllByCodeRepositoryId(codeRepository.getId()).stream()
                .collect(Collectors.toMap(PullRequest::getExternalId, pullRequest -> pullRequest));
    }
}
