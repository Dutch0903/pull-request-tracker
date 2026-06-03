package com.pullrequesttracker.application.usecase;

import com.pullrequesttracker.domain.repository.CodeRepositoryRepository;
import com.pullrequesttracker.domain.repository.PullRequestRepository;
import com.pullrequesttracker.application.synchronizer.RepositorySynchronizer;
import com.pullrequesttracker.domain.model.CodeRepository;
import com.pullrequesttracker.domain.model.PullRequest;
import com.pullrequesttracker.domain.model.PullRequestFactory;
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
public class FetchRepositoryPullRequests {
    private final RepositorySynchronizer repositorySynchronizer;
    private final PullRequestRepository pullRequestRepository;
    private final PullRequestFactory pullRequestFactory;
    private final CodeRepositoryRepository codeRepositoryRepository;

    @Async("repositoryCheckExecutor")
    public CompletableFuture<Void> execute(CodeRepository codeRepository) {
        Instant checkTime = Instant.now();

        Map<Integer, PullRequest> existing = pullRequestRepository
                .findAllByCodeRepositoryId(codeRepository.getId())
                .stream()
                .collect(Collectors.toMap(PullRequest::getExternalId, pr -> pr));

        repositorySynchronizer.synchronize(codeRepository).forEach(syncData -> {
            PullRequest pullRequest = Optional.ofNullable(existing.get(syncData.externalId()))
                    .orElseGet(() -> pullRequestFactory.create(codeRepository.getId(), syncData));

            pullRequest.sync(syncData);
            pullRequestRepository.save(pullRequest);
        });

        codeRepository.recordChecked(checkTime);
        codeRepositoryRepository.save(codeRepository);

        return CompletableFuture.completedFuture(null);
    }
}
