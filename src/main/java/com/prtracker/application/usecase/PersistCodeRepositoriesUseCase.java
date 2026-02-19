package com.prtracker.application.usecase;

import com.prtracker.domain.repository.CodeRepositoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class PersistCodeRepositoriesUseCase extends AbstractVoidUseCase {
    private final CodeRepositoryRepository codeRepositoryRepository;

    protected void executeInternal() {
        try {
            codeRepositoryRepository.persist();
        } catch (Throwable e) {
            log.error("Failed to persist code repositories", e);
            throw new RuntimeException(e);
        }
    }
}
