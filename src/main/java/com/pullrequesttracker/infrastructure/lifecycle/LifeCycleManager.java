package com.pullrequesttracker.infrastructure.lifecycle;

import com.pullrequesttracker.infrastructure.persistence.InMemoryCodeRepositoryRepository;
import com.pullrequesttracker.infrastructure.persistence.InMemoryPullRequestRepository;
import com.pullrequesttracker.infrastructure.persistence.InMemoryTokenRepository;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class LifeCycleManager {
    private final InMemoryTokenRepository tokenRepository;
    private final InMemoryCodeRepositoryRepository codeRepositoryRepository;
    private final InMemoryPullRequestRepository pullRequestRepository;

    @PostConstruct
    public void initialize() {
        log.info("Initializing repositories from file");

        try {
            tokenRepository.initialize();
            codeRepositoryRepository.initialize();
            pullRequestRepository.initialize();

            log.info("Successfully initialized repositories from file");
        } catch (Throwable e) {
            log.error("Failed to initialize repositories", e);
        }
    }

    @PreDestroy
    public void persist() {
        try {
            codeRepositoryRepository.persist();
            tokenRepository.persist();
            pullRequestRepository.persist();

            log.info("Successfully persisted repositories to file");
        } catch (Throwable e) {
            log.error("Failed to persist repositories", e);
            throw new RuntimeException(e);
        }
    }
}
