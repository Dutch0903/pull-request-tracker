package com.prtracker.shared.lifecycle;

import com.prtracker.coderepository.domain.port.CodeRepositoryRepository;
import com.prtracker.pullrequest.domain.port.PullRequestRepository;
import com.prtracker.token.domain.port.TokenRepository;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class LifeCycleManager {
    private final TokenRepository tokenRepository;
    private final CodeRepositoryRepository codeRepositoryRepository;
    private final PullRequestRepository pullRequestRepository;

    @PostConstruct
    public void initialize() {
        log.info("Initializing repositories from file");

        try {
            tokenRepository.initialize();
            codeRepositoryRepository.initialize();
            pullRequestRepository.initialize();

            log.info("Successfully initialized {} code repositories", codeRepositoryRepository.count());
            log.info("Successfully initialized {} tokens", tokenRepository.count());
            log.info("Successfully initialized {} pull requests", pullRequestRepository.count());
        } catch (Throwable e) {
            log.error("Failed to initialize repositories", e);
        }
    }

    @PreDestroy
    public void persist() {
        try {
            log.info("Persisting {} tokens, {} repositories and {} pull requests", tokenRepository.count(), codeRepositoryRepository.count(), pullRequestRepository.count());
            codeRepositoryRepository.persist();
            tokenRepository.persist();
            pullRequestRepository.persist();
        } catch (Throwable e) {
            log.error("Failed to persist repositories", e);
            throw new RuntimeException(e);
        }
    }
}
