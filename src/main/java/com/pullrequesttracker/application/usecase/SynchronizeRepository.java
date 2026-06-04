package com.pullrequesttracker.application.usecase;

import com.pullrequesttracker.application.provider.PullRequestProvider;
import com.pullrequesttracker.domain.model.CodeRepository;
import com.pullrequesttracker.domain.model.PullRequest;
import com.pullrequesttracker.domain.model.PullRequestFactory;
import com.pullrequesttracker.domain.repository.CodeRepositoryRepository;
import com.pullrequesttracker.domain.repository.PullRequestRepository;
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
public class SynchronizeRepository {
    private final PullRequestProvider pullRequestProvider;
    private final PullRequestRepository pullRequestRepository;
    private final CodeRepositoryRepository codeRepositoryRepository;

    @Async("repositoryCheckExecutor")
    public CompletableFuture<Void> execute(CodeRepository codeRepository) {
        Instant checkTime = Instant.now();

        Map<Integer, PullRequest> existing = pullRequestRepository
                .findAllByCodeRepositoryId(codeRepository.getId())
                .stream()
                .collect(Collectors.toMap(PullRequest::getExternalId, pr -> pr));

        pullRequestProvider.fetch(codeRepository).forEach(syncData -> {
            PullRequest pullRequest = Optional.ofNullable(existing.get(syncData.externalId()))
                    .orElseGet(() -> PullRequestFactory.create(codeRepository.getId(), syncData));

            pullRequest.sync(syncData);
            pullRequestRepository.save(pullRequest);
        });

        codeRepository.recordChecked(checkTime);
        codeRepositoryRepository.save(codeRepository);

        return CompletableFuture.completedFuture(null);
    }
}
