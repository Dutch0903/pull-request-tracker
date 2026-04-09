package com.prtracker.application.command;

import com.prtracker.domain.repository.CodeRepositoryRepository;
import com.prtracker.domain.repository.PullRequestRepository;
import com.prtracker.domain.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PersistCodeRepositoriesCommand extends VoidCommand {
    private final TokenRepository tokenRepository;
    private final CodeRepositoryRepository codeRepositoryRepository;
    private final PullRequestRepository pullRequestRepository;

    @Override
    protected void executeInternal() {
        try {
            codeRepositoryRepository.persist();
            tokenRepository.persist();
            pullRequestRepository.persist();
        } catch (Throwable e) {
            log.error("Failed to persist code repositories", e);
            throw new RuntimeException(e);
        }
    }
}
