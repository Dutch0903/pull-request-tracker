package com.prtracker.application.usecase;

import com.prtracker.domain.repository.CodeRepositoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class InitializeCodeRepositoriesUseCase extends AbstractVoidUseCase {
    private final CodeRepositoryRepository codeRepositoryRepository;

    protected void executeInternal() {
        log.info("Initializing code repositories from file");

        try {
            codeRepositoryRepository.initialize();

            log.info("Successfully initialized {} code repositories", codeRepositoryRepository.count());
        } catch (Throwable e) {
            log.error("Failed to initialize code repositories", e);
        }
    }
}
